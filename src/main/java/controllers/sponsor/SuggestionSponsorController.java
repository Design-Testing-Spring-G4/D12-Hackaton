
package controllers.sponsor;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CompetitionService;
import controllers.AbstractController;
import domain.Competition;
import domain.Suggestion;

@Controller
@RequestMapping("suggestion/sponsor")
public class SuggestionSponsorController extends AbstractController {

	//Services

	@Autowired
	private CompetitionService	competitionService;


	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int varId) {
		final ModelAndView result;
		Competition competition = null;

		if (this.competitionService.findOne(varId) == null)
			result = new ModelAndView("redirect:/welcome/index.do");
		else {
			competition = this.competitionService.findOne(varId);
			final Collection<Suggestion> suggestions = competition.getSuggestions();

			result = new ModelAndView("suggestion/list");
			result.addObject("suggestions", suggestions);
			result.addObject("requestURI", "suggestion/sponsor/list.do");
		}

		return result;
	}
}
