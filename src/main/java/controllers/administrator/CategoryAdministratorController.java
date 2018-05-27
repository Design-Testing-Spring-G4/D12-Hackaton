
package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CategoryService;
import controllers.AbstractController;
import domain.Category;

@Controller
@RequestMapping("category/administrator")
public class CategoryAdministratorController extends AbstractController {

	//Services

	@Autowired
	private CategoryService	categoryService;


	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Category> categories = this.categoryService.findAll();

		result = new ModelAndView("category/list");
		result.addObject("categories", categories);
		result.addObject("requestURI", "category/administrator/list.do");

		return result;
	}

	@RequestMapping(value = "/childrenList", method = RequestMethod.GET)
	public ModelAndView childrenList(@RequestParam final int varId) {
		final ModelAndView result;
		Collection<Category> categories;
		final Category category = this.categoryService.findOne(varId);
		categories = category.getChildren();

		result = new ModelAndView("category/childrenList");
		result.addObject("categories", categories);
		result.addObject("requestURI", "category/administrator/childrenList.do");

		return result;
	}

	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		Category category;

		category = this.categoryService.create();
		result = this.createEditModelAndView(category);

		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int varId) {
		final ModelAndView result;
		Category category;

		category = this.categoryService.findOne(varId);
		Assert.notNull(category);
		result = this.createEditModelAndView(category);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Category c, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(c);
		else
			try {
				final Category category = this.categoryService.reconstruct(c, binding);
				this.categoryService.save(category);
				result = new ModelAndView("redirect:/category/administrator/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(c, "category.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Category c, final BindingResult binding) {
		ModelAndView result;

		try {
			final Category category = this.categoryService.reconstruct(c, binding);
			this.categoryService.delete(category);
			result = new ModelAndView("redirect:/category/administrator/list.do");
		} catch (final Throwable oops) {
			oops.printStackTrace();
			result = this.createEditModelAndView(c, "category.commit.error");
		}
		return result;
	}

	//Delete

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int varId) {
		ModelAndView result;
		Collection<Category> categories;
		Category category;
		result = new ModelAndView("category/list");
		categories = this.categoryService.findAll();

		category = this.categoryService.findOne(varId);
		if (category.getParent() == null)
			result.addObject("message", "category.parent.error");
		else if (!category.getChildren().isEmpty())
			result.addObject("message", "category.delete.error");
		else {
			this.categoryService.delete(category);
			categories = this.categoryService.findAll();
		}

		result.addObject("categories", categories);
		result.addObject("requestURI", "category/administrator/list.do");

		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final Category category) {
		ModelAndView result;

		result = this.createEditModelAndView(category, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Category category, final String messageCode) {
		ModelAndView result;
		final Collection<Category> categories = this.categoryService.findAll();

		result = new ModelAndView("category/edit");
		result.addObject("category", category);
		result.addObject("categories", categories);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "category/administrator/edit.do");

		return result;

	}
}
