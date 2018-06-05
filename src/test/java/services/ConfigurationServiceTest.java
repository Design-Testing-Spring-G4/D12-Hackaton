
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
import domain.Configuration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ConfigurationServiceTest extends AbstractTest {

	//Service under test

	@Autowired
	private ConfigurationService	configurationService;


	//Test template

	protected void Template(final String banner, final Double vat, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate("admin");

			//Creation
			final Configuration configuration = this.configurationService.create();
			final Configuration saved = this.configurationService.save(configuration);

			//Listing
			final Collection<Configuration> cl = this.configurationService.findAll();
			Assert.isTrue(cl.contains(saved));
			Assert.notNull(this.configurationService.findOne(saved.getId()));

			//Edition
			saved.setVat(vat);
			saved.setBanner(banner);
			final Configuration saved2 = this.configurationService.save(saved);

			//Deletion
			this.configurationService.delete(saved2);
			Assert.isNull(this.configurationService.findOne(saved2.getId()));

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
				"http://www.banner.com", 39.0, null
			},

			//Test #02: Attempt to save a configuration with a non URL banner. Expected false.
			{
				"testBaner", 39.0, ConstraintViolationException.class
			},

			//Test #03: Attempt to introduce an invalid VAT tax. Expected false.
			{
				"http://www.banner.com", -10.0, ConstraintViolationException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.Template((String) testingData[i][0], (Double) testingData[i][1], (Class<?>) testingData[i][2]);
	}
}
