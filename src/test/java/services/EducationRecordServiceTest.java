
package services;

import java.util.Collection;
import java.util.Date;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.EducationRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class EducationRecordServiceTest extends AbstractTest {

	//Service under test

	@Autowired
	private EducationRecordService	educationRecordService;


	//Test template

	protected void Template(final String username, final String diploma, final String institution, final String diploma2, final Date periodStart, final Date periodEnd, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);

			//Creation

			final EducationRecord educationRecord = this.educationRecordService.create(this.getEntityId("curriculum1"));
			educationRecord.setAttachment("");
			educationRecord.setComments("");
			educationRecord.setDiploma(diploma);
			educationRecord.setInstitution(institution);
			educationRecord.setPeriodStart(periodStart);
			educationRecord.setPeriodEnd(periodEnd);
			final EducationRecord saved = this.educationRecordService.save(educationRecord);

			//Listing
			final Collection<EducationRecord> cl = this.educationRecordService.findAll();
			Assert.isTrue(cl.contains(saved));
			Assert.notNull(this.educationRecordService.findOne(saved.getId()));

			//Edition
			educationRecord.setDiploma(diploma2);
			final EducationRecord saved2 = this.educationRecordService.save(saved);

			//Deletion
			this.educationRecordService.delete(saved2);
			Assert.isNull(this.educationRecordService.findOne(saved2.getId()));

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
				"instructor1", "testDiploma", "testInstitution", "editDiploma", new Date(1476086400000L), new Date(1507622400000L), null
			},

			//Test #02: Attempt to save an education record without proper credentials. Expected false.
			{
				"auditor1", "testDiploma", "testInstitution", "editDiploma", new Date(1476086400000L), new Date(1507622400000L), IllegalArgumentException.class
			},

			//Test #03: Attempt to create an education record with a future start date. Expected false.
			{
				"instructor1", "testDiploma", "testInstitution", "editDiploma", new Date(1539158400000L), new Date(1549158400000L), ConstraintViolationException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.Template((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Date) testingData[i][4], (Date) testingData[i][5], (Class<?>) testingData[i][6]);
	}
}
