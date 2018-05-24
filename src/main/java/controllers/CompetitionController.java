
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CompetitionService;
import services.ResortService;
import domain.Competition;
import domain.Resort;

@Controller
@RequestMapping("competition")
public class CompetitionController extends AbstractController {

	//Services

	@Autowired
	private CompetitionService	competitionService;

	@Autowired
	private ResortService		resortService;


	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int varId) {
		final ModelAndView result;
		final Resort resort = this.resortService.findOne(varId);
		final Collection<Competition> competitions = resort.getCompetitions();

		result = new ModelAndView("competition/list");
		result.addObject("competitions", competitions);
		result.addObject("varId", varId);
		result.addObject("requestURI", "competition/list.do");

		return result;
	}

	//Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int varId, final int varId2) {
		final ModelAndView result;
		final Competition competition = this.competitionService.findOne(varId);

		result = new ModelAndView("competition/display");
		result.addObject("competition", competition);
		result.addObject("varId", varId2);
		result.addObject("requestURI", "competition/display.do");

		return result;
	}
}
