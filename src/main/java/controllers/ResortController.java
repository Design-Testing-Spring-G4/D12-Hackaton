
package controllers;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CategoryService;
import services.ResortService;
import domain.Category;
import domain.Resort;

@Controller
@RequestMapping("resort")
public class ResortController extends AbstractController {

	//Services

	@Autowired
	private ResortService	resortService;

	@Autowired
	private CategoryService	categoryService;


	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Resort> resorts = this.resortService.findAll();

		result = new ModelAndView("resort/list");
		result.addObject("resorts", resorts);
		result.addObject("requestURI", "resort/list.do");

		return result;
	}

	@RequestMapping(value = "/listCategory", method = RequestMethod.GET)
	public ModelAndView listCategory(@RequestParam final int varId) {
		final ModelAndView result;
		final Collection<Resort> resorts;
		final Category category;
		final Collection<Category> children = new ArrayList<Category>();

		if (varId == 0)
			category = this.categoryService.findAll().iterator().next();
		else
			category = this.categoryService.findOne(varId);

		resorts = category.getResorts();
		for (final Category c : category.getChildren())
			children.add(c);

		result = new ModelAndView("resort/list");
		result.addObject("resorts", resorts);
		result.addObject("category", category);
		result.addObject("categories", this.categoryService.findAll());
		result.addObject("childrenCategories", children);
		result.addObject("requestURI", "resort/listCategory.do");

		return result;
	}

	//Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int varId) {
		final ModelAndView result;
		final Resort resort = this.resortService.findOne(varId);

		result = new ModelAndView("resort/display");
		result.addObject("resort", resort);
		result.addObject("requestURI", "resort/display.do");

		return result;
	}

	//Searching via keyword

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView search(@RequestParam final String keyword) {
		final ModelAndView result;
		final Collection<Resort> resorts = this.resortService.searchByKeyword(keyword);

		result = new ModelAndView("resort/list");
		result.addObject("resorts", resorts);
		result.addObject("requestURI", "resort/list.do");

		return result;
	}
}
