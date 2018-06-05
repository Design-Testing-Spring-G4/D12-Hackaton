
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
import domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class UserServiceTest extends AbstractTest {

	//Service under test

	@Autowired
	private UserService	userService;


	//Test template

	protected void Template(final String username, final String address, final String email, final String name, final String surname, final String phone, final String address2, final String email2, final String name2, final String surname2,
		final String phone2, final Class<?> expected) {
		Class<?> caught = null;

		try {

			//Creation
			final User user = this.userService.create();
			user.setAddress(address);
			user.setEmail(email);
			user.setName(name);
			user.setSurname(surname);
			user.setPhone(phone);
			user.getUserAccount().setUsername(username);
			user.getUserAccount().setPassword(username);
			final User saved = this.userService.save(user);

			this.authenticate(username);

			//Listing
			final Collection<User> cl = this.userService.findAll();
			Assert.isTrue(cl.contains(saved));
			Assert.notNull(this.userService.findOne(saved.getId()));

			//Edition
			saved.setAddress(address2);
			saved.setEmail(email2);
			saved.setName(name2);
			saved.setSurname(surname2);
			saved.setPhone(phone2);
			final User saved2 = this.userService.save(saved);

			//Deletion
			this.userService.delete(saved2);
			Assert.isNull(this.userService.findOne(saved2.getId()));

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
				"user9", "testAddress", "testemail@alum.com", "testUser", "testSurname", "+648456571", "editAddress", "editemail@alum.com", "editUser", "editSurname", "+648456521", null
			},

			//Test #02: Attempt to save an user without proper credentials. Expected false.
			{
				"null", "testAddress", "testemail@alum.com", "testUser", "testSurname", "+648456571", "editAddress", "editemail@alum.com", "editUser", "editSurname", "+648456521", ConstraintViolationException.class
			},

			//Test #03: Attempt to edit an user with blank fields. Expected false.
			{
				"user9", "testAddress", "testemail@alum.com", "testUser", "testSurname", "+648456571", "editAddress", "", "", "", "+648456521", ConstraintViolationException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.Template((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6], (String) testingData[i][7],
				(String) testingData[i][8], (String) testingData[i][9], (String) testingData[i][10], (Class<?>) testingData[i][11]);
	}
}
