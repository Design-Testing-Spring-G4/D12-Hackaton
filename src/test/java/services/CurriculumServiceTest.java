
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Curriculum;
import domain.Instructor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class CurriculumServiceTest extends AbstractTest {

	//Service under test

	@Autowired
	private CurriculumService	curriculumService;

	@Autowired
	private InstructorService	instructorService;


	//Test template

	protected void Template(final String username, final Class<?> expected) {
		Class<?> caught = null;

		try {
			final Instructor instructor = this.instructorService.create();
			instructor.setAddress("");
			instructor.setEmail("mail@mail.com");
			instructor.setName("testName");
			instructor.setSurname("testSurname");
			instructor.setPhone("456456");
			instructor.getUserAccount().setUsername(username);
			instructor.getUserAccount().setPassword(username);
			this.instructorService.save(instructor);

			this.authenticate(username);

			//Creation
			final Curriculum curriculum = this.curriculumService.create();

			//Assertion that unique ticker generates automatically
			Assert.notNull(curriculum.getTicker());
			final Curriculum saved = this.curriculumService.save(curriculum);

			//Listing
			Assert.notNull(this.curriculumService.findOne(saved.getId()));

			//Deletion
			this.curriculumService.delete(saved);
			Assert.isNull(this.curriculumService.findOne(saved.getId()));

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
				"instructor9", null
			},

			//Test #02: Attempt to save a curriculum without proper credentials. Expected false.
			{
				"user1", DataIntegrityViolationException.class
			},

			//Test #03: Attempt to save a curriculum as anonymous user. Expected false.
			{
				"null", DataIntegrityViolationException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.Template((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}
}
