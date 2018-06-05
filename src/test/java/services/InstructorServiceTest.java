
package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Instructor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class InstructorServiceTest extends AbstractTest {

	//Service under test

	@Autowired
	private InstructorService	instructorService;


	//Test template

	protected void Template(final String username, final String username2, final String address, final String email, final String name, final String surname, final String phone, final String surname2, final Class<?> expected) {
		Class<?> caught = null;

		try {

			//Creation
			final Instructor instructor = this.instructorService.create();
			instructor.setAddress(address);
			instructor.setEmail(email);
			instructor.setName(name);
			instructor.setSurname(surname);
			instructor.setPhone(phone);
			instructor.getUserAccount().setUsername(username);
			instructor.getUserAccount().setPassword(username);
			final Instructor saved = this.instructorService.save(instructor);

			this.authenticate(username2);

			//Listing
			final Collection<Instructor> cl = this.instructorService.findAll();
			Assert.isTrue(cl.contains(saved));
			Assert.notNull(this.instructorService.findOne(saved.getId()));

			//Edition
			saved.setSurname(surname2);
			final Instructor saved2 = this.instructorService.save(saved);

			//Deletion
			this.instructorService.delete(saved2);
			Assert.isNull(this.instructorService.findOne(saved2.getId()));

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
				"instructor9", "instructor9", "testAddress", "testemail@alum.com", "testInstructor", "testSurname", "+648456571", "editSurname", null
			},

			//Test #02: Attempt to save an instructor without proper credentials. Expected false.
			{
				"instructor9", "admin", "testAddress", "testemail@alum.com", "testInstructor", "testSurname", "+648456571", "editSurname", DataIntegrityViolationException.class
			},

			//Test #03: Attempt to edit an instructor without surname. Expected false.
			{
				"instructor9", "instructor9", "testAddress", "testemail@alum.com", "testInstructor", "testSurname", "+648456571", "", DataIntegrityViolationException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.Template((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6], (String) testingData[i][7],
				(Class<?>) testingData[i][8]);
	}
}
