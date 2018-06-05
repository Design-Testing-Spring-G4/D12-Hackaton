
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
import domain.TagValue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class TagValueServiceTest extends AbstractTest {

	//Service under test

	@Autowired
	private TagValueService	tagValueService;


	//Test template

	protected void Template(final String username, final String value, final String value2, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);

			//Creation

			final TagValue tagValue = this.tagValueService.create(this.getEntityId("tag2"));
			tagValue.setValue(value);
			final TagValue saved = this.tagValueService.save(tagValue);

			//Listing
			final Collection<TagValue> cl = this.tagValueService.findAll();
			Assert.isTrue(cl.contains(saved));
			Assert.notNull(this.tagValueService.findOne(saved.getId()));

			//Edition
			saved.setValue(value2);
			final TagValue saved2 = this.tagValueService.save(saved);

			//Deletion
			this.tagValueService.delete(saved2);
			Assert.isNull(this.tagValueService.findOne(saved2.getId()));

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
				"admin", "testValue", "editValue", null
			},

			//Test #02: Attempt to create a tag value without value. Expected false.
			{
				"admin", "", "editValue", ConstraintViolationException.class
			},

			//Test #03: Attempt to edit a tag value without value. Expected false.
			{
				"admin", "testValue", "", ConstraintViolationException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.Template((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}
}
