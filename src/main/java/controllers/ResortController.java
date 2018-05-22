
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ResortService;
import domain.Resort;

@Controller
@RequestMapping("resort")
public class ResortController extends AbstractController {

	//Services

	@Autowired
	private ResortService	resortService;


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
}
