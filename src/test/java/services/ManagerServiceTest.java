
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
import domain.Manager;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ManagerServiceTest extends AbstractTest {

	//Service under test

	@Autowired
	private ManagerService	managerService;


	//Test template

	protected void Template(final String username, final String address, final String email, final String name, final String surname, final String phone, final String name2, final Class<?> expected) {
		Class<?> caught = null;

		try {

			//Creation

			final Manager manager = this.managerService.create();
			manager.setAddress(address);
			manager.setEmail(email);
			manager.setName(name);
			manager.setSurname(surname);
			manager.setPhone(phone);
			manager.getUserAccount().setUsername(username);
			manager.getUserAccount().setPassword(username);
			final Manager saved = this.managerService.save(manager);

			this.authenticate(username);

			//Listing
			final Collection<Manager> cl = this.managerService.findAll();
			Assert.isTrue(cl.contains(saved));
			Assert.notNull(this.managerService.findOne(saved.getId()));

			//Edition
			saved.setName(name2);
			final Manager saved2 = this.managerService.save(saved);

			//Deletion
			this.managerService.delete(saved2);
			Assert.isNull(this.managerService.findOne(saved2.getId()));

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
				"manager9", "testAddress", "testemail@alum.com", "testManager", "testSurname", "+648456571", "editManager", null
			},

			//Test #02: Attempt to create a manager with null fields. Expected false.
			{
				"manager9", "testAddress", "testemail@alum.com", null, null, "+648456571", "editManager", NullPointerException.class
			},

			//Test #03: Attempt to edit a manager with blank name. Expected false.
			{
				"manager9", "testAddress", "testemail@alum.com", "testManager", "testSurname", "+648456571", "", DataIntegrityViolationException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.Template((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6], (Class<?>) testingData[i][7]);
	}
}
