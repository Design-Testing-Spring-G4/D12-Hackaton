
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
import domain.Activity;
import domain.ActivityCategory;
import domain.Resort;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ActivityServiceTest extends AbstractTest {

	//Service under test

	@Autowired
	private ActivityService	activityService;

	//Auxiliary Services

	@Autowired
	private ResortService	resortService;


	//Test template

	protected void Template(final String username, final ActivityCategory category, final String description, final Double price, final String title, final String title2, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);

			//Creation

			final Activity activity = this.activityService.create();
			activity.setCategory(category);
			activity.setDescription(description);
			activity.setPrice(price);
			final Resort resort = this.resortService.findOne(this.getEntityId("resort1"));
			activity.setResort(resort);
			activity.setTitle(title);
			final Activity saved = this.activityService.save(activity);

			//Listing
			Collection<Activity> cl = this.activityService.findAll();
			Assert.isTrue(cl.contains(saved));
			Assert.notNull(this.activityService.findOne(saved.getId()));

			//Edition
			saved.setTitle(title2);
			final Activity saved2 = this.activityService.save(saved);

			//Deletion
			this.activityService.delete(saved2);
			cl = this.activityService.findAll();
			Assert.isTrue(!cl.contains(saved));

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
				"manager1", ActivityCategory.SPORT, "testDescription", 10.0, "testTitle", "testTitle2", null

			},

			//Test #02: Attempt to create an activity with incorrect price. Expected false.
			{
				"manager1", ActivityCategory.SPORT, "testDescription", -10.0, "testTitle", "testTitle2", ConstraintViolationException.class
			},

			//Test #03: Attempt to edit an activity without title. Expected false.
			{
				"manager1", ActivityCategory.SPORT, "testDescription", 10.0, "testTitle", null, ConstraintViolationException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.Template((String) testingData[i][0], (ActivityCategory) testingData[i][1], (String) testingData[i][2], (Double) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (Class<?>) testingData[i][6]);
	}
}
