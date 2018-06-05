
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
import domain.LegalText;
import domain.Location;
import domain.Resort;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ResortServiceTest extends AbstractTest {

	//Service under test

	@Autowired
	private ResortService		resortService;

	@Autowired
	private LegalTextService	legalTextService;


	//Test template

	protected void Template(final String username, final String locationName, final String name, final String description, final String features, final String name2, final String description2, final String features2, final Date startDate,
		final Date endDate, final Date startDate2, final Date endDate2, final Double priceAdult, final Double priceChild, final Double priceAdult2, final Double priceChild2, final Integer spots, final Integer spots2, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);

			//Creation
			final Resort resort = this.resortService.create();
			resort.setName(name);
			resort.setDescription(description);
			resort.setStartDate(startDate);
			resort.setEndDate(endDate);
			resort.setFeatures(features);
			final LegalText legalText = this.legalTextService.findOne(this.getEntityId("legalText1"));
			resort.setLegalText(legalText);
			final Location location = new Location();
			location.setLocation(locationName);
			location.setGpsCoordinates("+90.0, +90.0");
			resort.setLocation(location);
			resort.setPicture("");
			resort.setPriceAdult(priceAdult);
			resort.setPriceChild(priceChild);
			resort.setSpots(spots);
			final Resort saved = this.resortService.save(resort);

			//Listing
			final Collection<Resort> cl = this.resortService.findAll();
			Assert.isTrue(cl.contains(saved));
			Assert.notNull(this.resortService.findOne(saved.getId()));

			//Edition
			saved.setName(name2);
			saved.setDescription(description2);
			saved.setStartDate(startDate2);
			saved.setEndDate(endDate2);
			saved.setFeatures(features2);
			saved.setPriceAdult(priceAdult2);
			saved.setPriceChild(priceChild2);
			saved.setSpots(spots2);
			final Resort saved2 = this.resortService.save(saved);

			//Deletion
			this.resortService.delete(saved2);
			Assert.isNull(this.resortService.findOne(saved2.getId()));

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
				"manager1", "testLocation", "testName", "testDescription", "testFeatures", "editName", "editDescription", "editFeatures", new Date(1539158400000L), new Date(1549158400000L), new Date(1545158400000L), new Date(1555158400000L), 30.0, 10.0,
				20.0, 5.0, 60, 50, null
			},

			//Test #02: Attempt to create a resort without correct authorization. Expected false.
			{
				"user1", "testLocation", "testName", "testDescription", "testFeatures", "editName", "editDescription", "editFeatures", new Date(1539158400000L), new Date(1549158400000L), new Date(1545158400000L), new Date(1555158400000L), 30.0, 10.0,
				20.0, 5.0, 60, 50, ClassCastException.class
			},

			//Test #03: Attempt to create a resort as anonymous. Expected false.
			{
				"null", "testLocation", "testName", "testDescription", "testFeatures", "editName", "editDescription", "editFeatures", new Date(1539158400000L), new Date(1549158400000L), new Date(1545158400000L), new Date(1555158400000L), 30.0, 10.0, 20.0,
				5.0, 60, 50, IllegalArgumentException.class
			},

			//Test #04: Attempt to create a resort with blank fields. Expected false.
			{
				"manager1", "testLocation", "", "", "", "editName", "editDescription", "editFeatures", new Date(1539158400000L), new Date(1549158400000L), new Date(1545158400000L), new Date(1555158400000L), 30.0, 10.0, 20.0, 5.0, 60, 50,
				ConstraintViolationException.class
			},

			//Test #05: Attempt to edit a resort with blank fields. Expected false.
			{
				"manager1", "testLocation", "testName", "testDescription", "testFeatures", "", "", "", new Date(1539158400000L), new Date(1549158400000L), new Date(1545158400000L), new Date(1555158400000L), 30.0, 10.0, 20.0, 5.0, 60, 50,
				ConstraintViolationException.class
			},

			//Test #06: Attempt to create a resort with invalid dates. Expected false.
			{
				"manager1", "testLocation", "testName", "testDescription", "testFeatures", "editName", "editDescription", "editFeatures", null, null, new Date(1545158400000L), new Date(1555158400000L), 30.0, 10.0, 20.0, 5.0, 60, 50,
				NullPointerException.class
			},

			//Test #07: Attempt to create a resort with negative prices and spots. Expected false.
			{
				"manager1", "testLocation", "testName", "testDescription", "testFeatures", "editName", "editDescription", "editFeatures", new Date(1539158400000L), new Date(1549158400000L), new Date(1545158400000L), new Date(1555158400000L), -30.0, -10.0,
				20.0, 5.0, -60, 50, ConstraintViolationException.class
			},

			//Test #08: Attempt to edit a resort with null fields. Expected false.
			{
				"manager1", "testLocation", "testName", "testDescription", "testFeatures", null, null, null, new Date(1539158400000L), new Date(1549158400000L), new Date(1545158400000L), new Date(1555158400000L), 30.0, 10.0, 20.0, 5.0, 60, 50,
				ConstraintViolationException.class
			},

			//Test #09: Attempt to inject unsafe HTML in text fields on edition. Expected false.
			{
				"manager1", "testLocation", "testName", "testDescription", "testFeatures", "<h1>hack</h1>", "<h1>hack</h1>", "editFeatures", new Date(1539158400000L), new Date(1549158400000L), new Date(1545158400000L), new Date(1555158400000L), 30.0,
				10.0, 20.0, 5.0, 60, 50, ConstraintViolationException.class
			},

			//Test #10: Attempt to create a resort with an invalid location. Expected false.
			{
				"manager1", "", "testName", "testDescription", "testFeatures", "editName", "editDescription", "editFeatures", new Date(1539158400000L), new Date(1549158400000L), new Date(1545158400000L), new Date(1555158400000L), 30.0, 10.0, 20.0, 5.0,
				60, 50, ConstraintViolationException.class
			},

		};

		for (int i = 0; i < testingData.length; i++)
			this.Template((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6], (String) testingData[i][7],
				(Date) testingData[i][8], (Date) testingData[i][9], (Date) testingData[i][10], (Date) testingData[i][11], (Double) testingData[i][12], (Double) testingData[i][13], (Double) testingData[i][14], (Double) testingData[i][15],
				(Integer) testingData[i][16], (Integer) testingData[i][17], (Class<?>) testingData[i][18]);
	}
}
