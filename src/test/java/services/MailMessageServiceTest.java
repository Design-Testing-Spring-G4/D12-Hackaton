
package services;

import java.util.Collection;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Actor;
import domain.Folder;
import domain.MailMessage;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class MailMessageServiceTest extends AbstractTest {

	//Service under test

	@Autowired
	private MailMessageService	mailMessageService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private FolderService		folderService;


	//Test template

	protected void Template(final String username, final String body, final String subject, final String subject2, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);

			//Creation
			final MailMessage mailMessage = this.mailMessageService.create();
			mailMessage.setBody(body);
			final Actor receiver = this.actorService.findOne(this.getEntityId("user2"));
			mailMessage.setReceiver(receiver);
			mailMessage.setSubject(subject);
			final Folder folder = this.folderService.getSystemFolderByName(this.getEntityId("user1"), "Out box");
			mailMessage.setFolder(folder);
			final MailMessage saved = this.mailMessageService.save(mailMessage);

			//Sending
			final MailMessage sent = this.mailMessageService.send(saved);
			this.mailMessageService.save(sent);

			//Listing
			final Collection<MailMessage> cl = this.mailMessageService.findAll();
			Assert.isTrue(cl.contains(saved));
			Assert.notNull(this.mailMessageService.findOne(saved.getId()));

			//Edition
			saved.setSubject(subject2);
			final MailMessage saved2 = this.mailMessageService.save(saved);

			//Moving a message to another folder
			final Folder newOne = this.folderService.getSystemFolderByName(this.getEntityId("user1"), "Spam box");
			this.mailMessageService.move(saved2, newOne);

			//Deletion
			this.mailMessageService.delete(saved2);
			Assert.isNull(this.mailMessageService.findOne(saved2.getId()));

			this.unauthenticate();

			//As administrator, broadcast a notification to all users
			this.authenticate("admin");

			final MailMessage broadcastMessage = this.mailMessageService.create();
			broadcastMessage.setBody("testBroadcast");
			broadcastMessage.setSubject("testSubject");
			final Folder broadcastFolder = this.folderService.getSystemFolderByName(this.getEntityId("admin1"), "Out box");
			broadcastMessage.setFolder(broadcastFolder);
			this.mailMessageService.broadcastNotification(broadcastMessage);

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();

		}

		this.checkExceptions(expected, caught);
	}

	//Driver for multiple tests under the same template.

	@Test
	public void Driver() {

		final Object testingData[][] = {

			//Test #01: Correct execution of test. Expected true.
			{
				"user1", "testBody", "testSubject", "editSubject", null
			},

			//Test #02: Attempt to send a message without proper credentials. Expected false.
			{
				"null", "testBody", "testSubject", "editSubject", IllegalArgumentException.class
			},

			//Test #03: Attempt to create a message with blank fields. Expected false.
			{
				"user1", "", "", "editSubject", ConstraintViolationException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.Template((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
	}
}
