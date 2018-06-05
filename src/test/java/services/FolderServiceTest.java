
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
import domain.Folder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class FolderServiceTest extends AbstractTest {

	//Service under test

	@Autowired
	private FolderService	folderService;

	@Autowired
	private ActorService	actorService;


	//Test template

	protected void Template(final String username, final String name, final String name2, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);

			//Creation

			final Folder folder = this.folderService.create(this.actorService.findByPrincipal());
			folder.setName(name);
			folder.setParent(null);
			final Folder saved = this.folderService.save(folder);

			//Listing
			final Collection<Folder> cl = this.folderService.findAll();
			Assert.isTrue(cl.contains(saved));
			Assert.notNull(this.folderService.findOne(saved.getId()));

			//Edition
			saved.setName(name2);
			final Folder saved2 = this.folderService.save(saved);

			//Deletion
			this.folderService.delete(saved2);
			Assert.isNull(this.folderService.findOne(saved2.getId()));

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
				"user1", "testName", "editName", null
			},

			//Test #02: Attempt to create a folder without name. Expected false.
			{
				"user1", "", "editName", ConstraintViolationException.class
			},

			//Test #03: Attempt to edit an folder with injected HTML code. Expected false.
			{
				"user1", "testName", "<h1>hack</h1>", ConstraintViolationException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.Template((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}
}
