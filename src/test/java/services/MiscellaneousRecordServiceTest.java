
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
import domain.MiscellaneousRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class MiscellaneousRecordServiceTest extends AbstractTest {

	//Service under test

	@Autowired
	private MiscellaneousRecordService	miscellaneousRecordService;


	//Test template

	protected void Template(final String username, final String title, final String title2, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);

			//Creation

			final MiscellaneousRecord miscellaneousRecord = this.miscellaneousRecordService.create(this.getEntityId("curriculum1"));
			miscellaneousRecord.setComments("");
			miscellaneousRecord.setLink("");
			miscellaneousRecord.setTitle(title);
			final MiscellaneousRecord saved = this.miscellaneousRecordService.save(miscellaneousRecord);

			//Listing
			final Collection<MiscellaneousRecord> cl = this.miscellaneousRecordService.findAll();
			Assert.isTrue(cl.contains(saved));
			Assert.notNull(this.miscellaneousRecordService.findOne(saved.getId()));

			//Edition
			saved.setTitle(title2);
			final MiscellaneousRecord saved2 = this.miscellaneousRecordService.save(saved);

			//Deletion
			this.miscellaneousRecordService.delete(saved2);
			Assert.isNull(this.miscellaneousRecordService.findOne(saved2.getId()));

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
				"instructor1", "testTitle", "editTitle", null
			},

			//Test #02: Attempt to save a miscellaneous record without proper credentials. Expected false.
			{
				"user2", "testTitle", "editTitle", IllegalArgumentException.class
			},

			//Test #03: Attempt to create a miscellaneous record with blank fields. Expected false.
			{
				"instructor1", "", "", ConstraintViolationException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.Template((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}
}
