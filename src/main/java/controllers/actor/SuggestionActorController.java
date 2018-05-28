
package controllers.actor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CompetitionService;
import services.SuggestionService;
import controllers.AbstractController;
import domain.Competition;
import domain.Suggestion;

@Controller
@RequestMapping("suggestion/actor")
public class SuggestionActorController extends AbstractController {

	//Services

	@Autowired
	private SuggestionService	suggestionService;

	@Autowired
	private CompetitionService	competitionService;


	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int varId) {
		final ModelAndView result;
		final Competition competition = this.competitionService.findOne(varId);
		final Suggestion suggestion = this.suggestionService.create(competition);
		result = this.createEditModelAndView(suggestion);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Suggestion s, final BindingResult binding) {
		ModelAndView result;

		if (s.getTitle().isEmpty())
			binding.rejectValue("title", "org.hibernate.validator.constraints.NotEmpty.message");
		if (s.getComments().isEmpty())
			binding.rejectValue("comments", "org.hibernate.validator.constraints.NotEmpty.message");
		if (binding.hasErrors())
			result = this.createEditModelAndView(s);
		else
			try {
				final Suggestion suggestion = this.suggestionService.reconstruct(s, s.getCompetition(), binding);
				this.suggestionService.save(suggestion);

				result = new ModelAndView("redirect:/resort/list.do");
			} catch (final Throwable oops) {
				oops.printStackTrace();
				result = this.createEditModelAndView(s, "suggestion.commit.error");
			}
		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final Suggestion suggestion) {
		ModelAndView result;

		result = this.createEditModelAndView(suggestion, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Suggestion suggestion, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("suggestion/edit");
		result.addObject("suggestion", suggestion);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "suggestion/actor/edit.do");

		return result;
	}
}
