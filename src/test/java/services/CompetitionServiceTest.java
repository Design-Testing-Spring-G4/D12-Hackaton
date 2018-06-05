
package services;

import java.util.Collection;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Competition;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class CompetitionServiceTest extends AbstractTest {

	//Service under test

	@Autowired
	private CompetitionService	competitionService;


	//Test template

	protected void Template(final String username, final String title, final String description, final String rules, final String sports, final String title2, final Date startDate, final Date endDate, final Double entry, final Double prizePool,
		final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);

			//Creation
			final Competition competition = this.competitionService.create();
			competition.setBanner("");
			competition.setLink("");
			competition.setTitle(title);
			competition.setDescription(description);
			competition.setStartDate(startDate);
			competition.setEndDate(endDate);
			competition.setEntry(entry);
			competition.setPrizePool(prizePool);
			competition.setMaxParticipants(32);
			competition.setRules(rules);
			competition.setSports(sports);
			final Competition saved = this.competitionService.save(competition);

			//Listing
			final Collection<Competition> cl = this.competitionService.findAll();
			Assert.isTrue(cl.contains(saved));
			Assert.notNull(this.competitionService.findOne(saved.getId()));

			//Edition
			saved.setTitle(title2);
			final Competition saved2 = this.competitionService.save(saved);

			//Deletion
			this.competitionService.delete(saved2);
			Assert.isNull(this.competitionService.findOne(saved2.getId()));

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
				"sponsor1", "testTitle", "testDescription", "testRules", "testSports", "editTitle", new Date(1539158400000L), new Date(1549158400000L), 10.0, 1000.0, null
			},

			//Test #02: Attempt to create a competition without proper credentials. Expected false.
			{
				"admin", "testTitle", "testDescription", "testRules", "testSports", "editTitle", new Date(1539158400000L), new Date(1549158400000L), 10.0, 1000.0, ClassCastException.class
			},

			//Test #03: Attempt to create a competition with a start date after the end date. Expected false.
			{
				"sponsor1", "testTitle", "testDescription", "testRules", "testSports", "editTitle", new Date(1549158400000L), new Date(1539158400000L), 10.0, 1000.0, IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.Template((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (Date) testingData[i][6], (Date) testingData[i][7],
				(Double) testingData[i][8], (Double) testingData[i][9], (Class<?>) testingData[i][10]);
	}
}
