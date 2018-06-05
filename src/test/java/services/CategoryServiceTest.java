
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
import domain.Category;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class CategoryServiceTest extends AbstractTest {

	//Service under test

	@Autowired
	private CategoryService	categoryService;


	//Test template

	protected void Template(final String categoryParent, final String name, final String name2, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate("admin");

			//Creation
			final Category category = this.categoryService.create();
			category.setName(name);
			final Category parent = this.categoryService.findOne(this.getEntityId(categoryParent));
			category.setParent(parent);
			final Category saved = this.categoryService.save(category);

			//Listing
			final Collection<Category> cl = this.categoryService.findAll();
			Assert.isTrue(cl.contains(saved));
			Assert.notNull(this.categoryService.findOne(saved.getId()));

			//Edition
			saved.setName(name2);
			final Category saved2 = this.categoryService.save(saved);

			//Deletion
			this.categoryService.delete(saved2);
			Assert.isNull(this.categoryService.findOne(saved2.getId()));

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
				"category1", "testName", "editName", null
			},

			//Test #02: Attempt to create a category without parent. Expected false.
			{
				null, "testName", "editName", NullPointerException.class
			},

			//Test #03: Attempt to edit an category without name. Expected false.
			{
				"category1", "testName", "", ConstraintViolationException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.Template((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}
}
