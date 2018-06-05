
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
import domain.Audit;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AuditServiceTest extends AbstractTest {

	//Service under test

	@Autowired
	private AuditService	auditService;


	//Test template

	protected void Template(final String username, final String attachments, final String description, final String title, final boolean finalMode, final boolean finalMode2, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);

			//Creation
			final Audit audit = this.auditService.create(this.getEntityId("resort1"));
			audit.setAttachments(attachments);
			audit.setDescription(description);
			audit.setFinalMode(finalMode);
			audit.setTitle(title);
			final Audit saved = this.auditService.save(audit);

			//Listing
			final Collection<Audit> cl = this.auditService.findAll();
			Assert.isTrue(cl.contains(saved));
			Assert.notNull(this.auditService.findOne(saved.getId()));

			//Edition
			saved.setFinalMode(finalMode2);
			final Audit saved2 = this.auditService.save(saved);

			//Deletion
			this.auditService.delete(saved2);
			Assert.isNull(this.auditService.findOne(saved2.getId()));

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
				"auditor1", "", "testDescription", "testTitle", false, true, null
			},

			//Test #02: Attempt to create an audit with incorrect credentials. Expected false.
			{
				"user1", "", "testDescription", "testTitle", false, true, ClassCastException.class
			},

			//Test #03: Attempt to save an audit with blank fields. Expected false.
			{
				"auditor1", "", "", "", false, true, ConstraintViolationException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.Template((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (boolean) testingData[i][4], (boolean) testingData[i][5], (Class<?>) testingData[i][6]);
	}
}
