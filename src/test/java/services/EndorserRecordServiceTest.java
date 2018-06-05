
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
import domain.EndorserRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class EndorserRecordServiceTest extends AbstractTest {

	//Service under test

	@Autowired
	private EndorserRecordService	endorserRecordService;


	//Test template

	protected void Template(final String username, final String email, final String name, final String phone, final String profile, final String email2, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);

			//Creation
			final EndorserRecord endorserRecord = this.endorserRecordService.create(this.getEntityId("curriculum1"));
			endorserRecord.setEmail(email);
			endorserRecord.setName(name);
			endorserRecord.setPhone(phone);
			endorserRecord.setComments("");
			endorserRecord.setProfile(profile);
			final EndorserRecord saved = this.endorserRecordService.save(endorserRecord);

			//Listing
			final Collection<EndorserRecord> cl = this.endorserRecordService.findAll();
			Assert.isTrue(cl.contains(saved));
			Assert.notNull(this.endorserRecordService.findOne(saved.getId()));

			//Edition
			saved.setEmail(email2);
			final EndorserRecord saved2 = this.endorserRecordService.save(saved);

			//Deletion
			this.endorserRecordService.delete(saved2);
			Assert.isNull(this.endorserRecordService.findOne(saved2.getId()));

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
				"instructor1", "test@mail.com", "testName", "345456", "http://www.profile.org", "edit@mail.com", null
			},

			//Test #02: Attempt to save an endorser record without proper credentials. Expected false.
			{
				"user2", "test@mail.com", "testName", "345456", "http://www.profile.org", "edit@mail.com", IllegalArgumentException.class
			},

			//Test #03: Attempt to edit an endorser record with blank fields. Expected false.
			{
				"instructor1", "test@mail.com", "", "345456", "", "", ConstraintViolationException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.Template((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (Class<?>) testingData[i][6]);
	}
}
