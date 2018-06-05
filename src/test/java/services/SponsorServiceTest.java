
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
import domain.Sponsor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class SponsorServiceTest extends AbstractTest {

	//Service under test

	@Autowired
	private SponsorService	sponsorService;


	//Test template

	protected void Template(final String username, final String address, final String email, final String name, final String surname, final String phone, final String address2, final String email2, final String name2, final String surname2,
		final String phone2, final Class<?> expected) {
		Class<?> caught = null;

		try {

			//Creation
			final Sponsor sponsor = this.sponsorService.create();
			sponsor.setAddress(address);
			sponsor.setEmail(email);
			sponsor.setName(name);
			sponsor.setSurname(surname);
			sponsor.setPhone(phone);
			sponsor.getUserAccount().setUsername(username);
			sponsor.getUserAccount().setPassword(username);
			final Sponsor saved = this.sponsorService.save(sponsor);

			this.authenticate(username);

			//Listing
			final Collection<Sponsor> cl = this.sponsorService.findAll();
			Assert.isTrue(cl.contains(saved));
			Assert.notNull(this.sponsorService.findOne(saved.getId()));

			//Edition
			saved.setAddress(address2);
			saved.setEmail(email2);
			saved.setName(name2);
			saved.setSurname(surname2);
			saved.setPhone(phone2);
			final Sponsor saved2 = this.sponsorService.save(saved);

			//Deletion
			this.sponsorService.delete(saved2);
			Assert.isNull(this.sponsorService.findOne(saved2.getId()));

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
				"sponsor9", "testAddress", "testemail@alum.com", "testSponsor", "testSurname", "+648456571", "editAddress", "editemail@alum.com", "editSponsor", "editSurname", "+648456521", null
			},

			//Test #02: Attempt to save an sponsor without proper credentials. Expected false.
			{
				"auditor1", "testAddress", "testemail@alum.com", "testSponsor", "testSurname", "+648456571", "editAddress", "editemail@alum.com", "editSponsor", "editSurname", null, DataIntegrityViolationException.class
			},

			//Test #03: Attempt to create a sponsor without email. Expected false.
			{
				"sponsor9", "testAddress", "", "testSponsor", "testSurname", "+648456571", "editAddress", "editemail@alum.com", "editSponsor", "editSurname", "+648456521", DataIntegrityViolationException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.Template((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6], (String) testingData[i][7],
				(String) testingData[i][8], (String) testingData[i][9], (String) testingData[i][10], (Class<?>) testingData[i][11]);
	}
}
