
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
import domain.Participation;
import domain.Status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ParticipationServiceTest extends AbstractTest {

	//Service under test

	@Autowired
	private ParticipationService	participationService;


	//Test template

	protected void Template(final String username, final String comments, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);

			//Creation
			final Participation participation = this.participationService.create();
			participation.setComments(comments);
			final Participation saved = this.participationService.save(participation);

			//Listing
			final Collection<Participation> cl = this.participationService.findAll();
			Assert.isTrue(cl.contains(saved));
			Assert.notNull(this.participationService.findOne(saved.getId()));

			//Edition
			saved.setStatus(Status.CANCELLED);
			final Participation saved2 = this.participationService.save(saved);

			//Deletion
			this.participationService.delete(saved2);
			Assert.isNull(this.participationService.findOne(saved2.getId()));

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
				"user1", "testComments", null
			},

			//Test #02: Attempt to save a participation without proper credentials. Expected false.
			{
				"null", "testAddress", IllegalArgumentException.class
			},

			//Test #03: Attempt to create a participation injecting HTML code. Expected false.
			{
				"instructor1", "<h1>hack</h1>", ConstraintViolationException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.Template((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}
}
