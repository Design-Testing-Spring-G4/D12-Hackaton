
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
import domain.Auditor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AuditorServiceTest extends AbstractTest {

	//Service under test

	@Autowired
	private AuditorService	auditorService;


	//Test template

	protected void Template(final String username, final String username2, final String address, final String email, final String name, final String surname, final String phone, final String name2, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);

			//Creation
			final Auditor auditor = this.auditorService.create();
			auditor.setAddress(address);
			auditor.setEmail(email);
			auditor.setName(name);
			auditor.setSurname(surname);
			auditor.setPhone(phone);
			auditor.getUserAccount().setUsername(username2);
			auditor.getUserAccount().setPassword(username2);
			final Auditor saved = this.auditorService.save(auditor);

			this.unauthenticate();
			this.authenticate(username2);

			//Listing
			final Collection<Auditor> cl = this.auditorService.findAll();
			Assert.isTrue(cl.contains(saved));
			Assert.notNull(this.auditorService.findOne(saved.getId()));

			//Edition
			saved.setName(name2);
			final Auditor saved2 = this.auditorService.save(saved);

			//Deletion
			this.auditorService.delete(saved2);
			Assert.isNull(this.auditorService.findOne(saved2.getId()));

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
				"admin", "auditor9", "testAddress", "testemail@alum.com", "testAuditor", "testSurname", "+648456571", "editAuditor", null
			},

			//Test #02: Attempt to save an auditor without proper credentials. Expected false.
			{
				"admin", null, "testAddress", "testemail@alum.com", "testAuditor", "testSurname", "+648456571", "editAuditor", IllegalArgumentException.class
			},

			//Test #03: Attempt to edit an auditor without name. Expected false.
			{
				"admin", "auditor9", "testAddress", "testemail@alum.com", "testAuditor", "testSurname", "+648456571", "", ConstraintViolationException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.Template((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6], (String) testingData[i][7],
				(Class<?>) testingData[i][8]);
	}
}
