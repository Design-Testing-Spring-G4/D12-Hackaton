
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
import domain.Competition;
import domain.Suggestion;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class SuggestionServiceTest extends AbstractTest {

	//Service under test

	@Autowired
	private SuggestionService	suggestionService;

	@Autowired
	private CompetitionService	competitionService;


	//Test template

	protected void Template(final String username, final String comments, final String title, final String comments2, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);

			//Creation
			final Competition competition = this.competitionService.findOne(this.getEntityId("competition1"));
			final Suggestion suggestion = this.suggestionService.create(competition);
			suggestion.setAttachments("");
			suggestion.setComments(comments);
			suggestion.setTitle(title);
			final Suggestion saved = this.suggestionService.save(suggestion);

			//Listing
			final Collection<Suggestion> cl = this.suggestionService.findAll();
			Assert.isTrue(cl.contains(saved));
			Assert.notNull(this.suggestionService.findOne(saved.getId()));

			//Edition
			saved.setComments(comments2);
			final Suggestion saved2 = this.suggestionService.save(saved);

			//Deletion
			this.suggestionService.delete(saved2);
			Assert.isNull(this.suggestionService.findOne(saved2.getId()));

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
				"user1", "testComments", "testTitle", "editComments", null
			},

			//Test #02: Attempt to save a suggestion without proper credentials. Expected false.
			{
				"null", "testComments", "testTitle", "editComments", IllegalArgumentException.class
			},

			//Test #03: Attempt to inject HTML code when creating a suggestion. Expected false.
			{
				"user1", "<h1>hack</h1>", "testTitle", "editComments", ConstraintViolationException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.Template((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
	}
}
