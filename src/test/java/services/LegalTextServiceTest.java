
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
import domain.LegalText;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class LegalTextServiceTest extends AbstractTest {

	//Service under test

	@Autowired
	private LegalTextService	legalTextService;


	//Test template

	protected void Template(final String username, final String body, final String laws, final String title, final String title2, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);

			//Creation
			final LegalText legalText = this.legalTextService.create();
			legalText.setBody(body);
			legalText.setLaws(laws);
			legalText.setTitle(title);
			legalText.setFinalMode(false);
			final LegalText saved = this.legalTextService.save(legalText);

			//Listing
			final Collection<LegalText> cl = this.legalTextService.findAll();
			Assert.isTrue(cl.contains(saved));
			Assert.notNull(this.legalTextService.findOne(saved.getId()));

			//Edition
			saved.setTitle(title2);
			final LegalText saved2 = this.legalTextService.save(saved);

			//Deletion
			this.legalTextService.delete(saved2);
			Assert.isNull(this.legalTextService.findOne(saved2.getId()));

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
				"admin", "testBody", "testLaws", "testTitle", "editTitle", null
			},

			//Test #02: Attempt to create a text attempting to inject HTML code. Expected false.
			{
				"admin", "<h1>hack</h1>", "testLaws", "testTitle", "editTitle", ConstraintViolationException.class
			},

			//Test #03: Attempt to create a text without body. Expected false.
			{
				"admin", "", "testLaws", "testTitle", "editTitle", ConstraintViolationException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.Template((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (Class<?>) testingData[i][5]);
	}
}
