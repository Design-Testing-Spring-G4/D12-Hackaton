
package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Lesson;
import domain.Schedule;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class LessonServiceTest extends AbstractTest {

	//Service under test

	@Autowired
	private LessonService	lessonService;


	//Test template

	protected void Template(final String username, final String name, final String description, final String name2, final Double price, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);

			//Creation
			final Lesson lesson = this.lessonService.create();
			lesson.setName(name);
			lesson.setDescription(description);
			lesson.setSchedule(Schedule.DAILY);
			lesson.setPrice(price);
			final Lesson saved = this.lessonService.save(lesson);

			//Listing
			final Collection<Lesson> cl = this.lessonService.findAll();
			Assert.isTrue(cl.contains(saved));
			Assert.notNull(this.lessonService.findOne(saved.getId()));

			//Edition
			saved.setName(name2);
			final Lesson saved2 = this.lessonService.save(saved);

			//Deletion
			this.lessonService.delete(saved2);
			Assert.isNull(this.lessonService.findOne(saved2.getId()));

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
				"instructor1", "testName", "testDescription", "editName", 10.0, null
			},

			//Test #02: Attempt to save a lesson without proper credentials. Expected false.
			{
				"manager1", "testName", "testDescription", "editName", 10.0, ClassCastException.class
			},

			//Test #03: Attempt to edit a lesson without name. Expected false.
			{
				"instructor1", "testName", "testDescription", null, 10.0, NullPointerException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.Template((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Double) testingData[i][4], (Class<?>) testingData[i][5]);
	}
}
