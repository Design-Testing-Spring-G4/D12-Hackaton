
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.PersonalRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class PersonalRecordServiceTest extends AbstractTest {

	//Service under test

	@Autowired
	private PersonalRecordService	personalRecordService;


	//Test template

	protected void Template(final String username, final String email, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);

			//Listing
			final PersonalRecord personalRecord = this.personalRecordService.findOne(this.getEntityId("personalRecord1"));
			Assert.notNull(personalRecord);

			//Edition
			personalRecord.setEmail(email);
			final PersonalRecord saved2 = this.personalRecordService.save(personalRecord);

			//Deletion
			this.personalRecordService.delete(saved2);
			Assert.isNull(this.personalRecordService.findOne(saved2.getId()));

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
				"instructor1", "edit@mail.com", null
			},

			//Test #02: Attempt to save a personal record without proper credentials. Expected false.
			{
				"admin", "edit@mail.com", IllegalArgumentException.class
			},

			//Test #03: Attempt to edit a personal record without email. Expected false.
			{
				"instructor1", "", IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.Template((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}
}
