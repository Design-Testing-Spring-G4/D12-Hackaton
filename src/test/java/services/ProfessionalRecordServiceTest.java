
package services;

import java.util.Collection;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.ProfessionalRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ProfessionalRecordServiceTest extends AbstractTest {

	//Service under test

	@Autowired
	private ProfessionalRecordService	professionalRecordService;


	//Test template

	protected void Template(final String username, final String company, final String role, final String company2, final Date periodStart, final Date periodEnd, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);

			//Creation
			final ProfessionalRecord professionalRecord = this.professionalRecordService.create(this.getEntityId("curriculum1"));
			professionalRecord.setAttachment("");
			professionalRecord.setComments("");
			professionalRecord.setCompany(company);
			professionalRecord.setRole(role);
			professionalRecord.setPeriodStart(periodStart);
			professionalRecord.setPeriodEnd(periodEnd);
			final ProfessionalRecord saved = this.professionalRecordService.save(professionalRecord);

			//Listing
			final Collection<ProfessionalRecord> cl = this.professionalRecordService.findAll();
			Assert.isTrue(cl.contains(saved));
			Assert.notNull(this.professionalRecordService.findOne(saved.getId()));

			//Edition
			saved.setCompany(company2);
			final ProfessionalRecord saved2 = this.professionalRecordService.save(saved);

			//Deletion
			this.professionalRecordService.delete(saved2);
			Assert.isNull(this.professionalRecordService.findOne(saved2.getId()));

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
				"instructor1", "testCompany", "testRole", "editCompany", new Date(1039158400000L), new Date(1049158400000L), null
			},

			//Test #02: Attempt to save a professional record without proper credentials. Expected false.
			{
				"manager1", "testCompany", "testRole", "editCompany", new Date(1039158400000L), new Date(1049158400000L), IllegalArgumentException.class
			},

			//Test #03: Attempt to create a professional record with start date after end date. Expected false.
			{
				"instructor1", "testCompany", "testRole", "editCompany", new Date(1049158400000L), new Date(1039158400000L), IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.Template((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Date) testingData[i][4], (Date) testingData[i][5], (Class<?>) testingData[i][6]);
	}
}
