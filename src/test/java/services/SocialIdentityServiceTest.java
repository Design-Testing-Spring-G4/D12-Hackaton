
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
import domain.Administrator;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class SocialIdentityServiceTest extends AbstractTest {

	//Service under test

	@Autowired
	private AdministratorService	administratorService;


	//Test template

	protected void Template(final String username, final String address, final String email, final String name, final String surname, final String phone, final String address2, final String email2, final String name2, final String surname2,
		final String phone2, final String username2, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);

			//Creation

			final Administrator administrator = this.administratorService.create();
			administrator.setAddress(address);
			administrator.setEmail(email);
			administrator.setName(name);
			administrator.setSurname(surname);
			administrator.setPhone(phone);
			administrator.getUserAccount().setUsername(username2);
			administrator.getUserAccount().setPassword(username2);
			final Administrator saved = this.administratorService.save(administrator);

			this.unauthenticate();
			this.authenticate(username2);

			//Listing
			Collection<Administrator> cl = this.administratorService.findAll();
			Assert.isTrue(cl.contains(saved));
			Assert.notNull(this.administratorService.findOne(saved.getId()));

			//Edition
			saved.setAddress(address2);
			saved.setEmail(email2);
			saved.setName(name2);
			saved.setSurname(surname2);
			saved.setPhone(phone2);
			final Administrator saved2 = this.administratorService.save(saved);

			//Deletion
			this.administratorService.delete(saved2);
			cl = this.administratorService.findAll();
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
				"admin", "testAddress", "testemail@alum.com", "testAdministrator", "testSurname", "+648456571", "editAddress", "editemail@alum.com", "editAdministrator", "editSurname", "+648456521", "admin9", null

			},

			//Test #02: Attempt to save an administrator without proper credentials. Expected false.
			{
				"admin", "testAddress", "testemail@alum.com", "testAdministrator", "testSurname", "+648456571", "editAddress", "editemail@alum.com", "editAdministrator", "editSurname", "+648456521", null, IllegalArgumentException.class

			},

			//Test #03: Attempt to create an administrator without email. Expected false.
			{
				"admin", "testAddress", "", "testAdministrator", "testSurname", "+648456571", "editAddress", "editemail@alum.com", "editAdministrator", "editSurname", "+648456521", null, IllegalArgumentException.class

			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.Template((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6], (String) testingData[i][7],
				(String) testingData[i][8], (String) testingData[i][9], (String) testingData[i][10], (String) testingData[i][11], (Class<?>) testingData[i][12]);
	}
}
