
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
import domain.Administrator;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AdministratorServiceTest extends AbstractTest {

	//Service under test

	@Autowired
	private AdministratorService	administratorService;


	//Test template

	protected void Template(final String username, final String username2, final String address, final String email, final String name, final String surname, final String phone, final String email2, final Class<?> expected) {
		Class<?> caught = null;

		try {

			//Creation
			final Administrator administrator = this.administratorService.create();
			administrator.setAddress(address);
			administrator.setEmail(email);
			administrator.setName(name);
			administrator.setSurname(surname);
			administrator.setPhone(phone);
			administrator.getUserAccount().setUsername(username);
			administrator.getUserAccount().setPassword(username);
			final Administrator saved = this.administratorService.save(administrator);

			this.authenticate(username2);

			//Listing
			final Collection<Administrator> cl = this.administratorService.findAll();
			Assert.isTrue(cl.contains(saved));
			Assert.notNull(this.administratorService.findOne(saved.getId()));

			//Edition
			saved.setEmail(email2);
			final Administrator saved2 = this.administratorService.save(saved);

			//Deletion
			this.administratorService.delete(saved2);
			Assert.isNull(this.administratorService.findOne(saved2.getId()));

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
				"admin9", "admin9", "testAddress", "testemail@alum.com", "testAdministrator", "testSurname", "+64 8456571", "editemail@alum.com", null
			},

			//Test #02: Attempt to save an administrator without proper credentials. Expected false.
			{
				"admin9", "admin", "testAddress", "testemail@alum.com", "testAdministrator", "testSurname", "+64 8456571", "editemail@alum.com", DataIntegrityViolationException.class
			},

			//Test #03: Attempt to edit an administrator with invalid email. Expected false.
			{
				"admin9", "admin9", "testAddress", "testemail@alum.com", "testAdministrator", "testSurname", "+64 8456571", "invalidEmail", DataIntegrityViolationException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.Template((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6], (String) testingData[i][7],
				(Class<?>) testingData[i][8]);
	}
}
