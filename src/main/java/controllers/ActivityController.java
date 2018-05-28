
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ResortService;
import domain.Activity;
import domain.Resort;

@Controller
@RequestMapping("activity")
public class ActivityController extends AbstractController {

	//Services

	@Autowired
	private ResortService	resortService;


	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int varId) {
		final ModelAndView result;
		Resort resort = null;

		if (this.resortService.findOne(varId) == null)
			result = new ModelAndView("redirect:/welcome/index.do");
		else {
			resort = this.resortService.findOne(varId);
			final Collection<Activity> activities = resort.getActivities();

			result = new ModelAndView("activity/list");
			result.addObject("activities", activities);
			result.addObject("requestURI", "activity/list.do");
		}

		return result;
	}
}
