
package controllers.actor;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.CompetitionService;
import controllers.AbstractController;
import controllers.WelcomeController;
import domain.Actor;
import domain.Competition;
import domain.Participation;
import domain.Status;

@Controller
@RequestMapping("actor")
public class ActorController extends AbstractController {

	//Services

	@Autowired
	private ActorService		actorService;

	@Autowired
	private CompetitionService	competitionService;

	@Autowired
	private WelcomeController	welcomeController;


	//Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int varId) {
		final ModelAndView result;
		Actor actor = null;

		if (this.actorService.findOne(varId) == null)
			result = this.welcomeController.index(null);
		else {
			actor = this.actorService.findOne(varId);

			result = new ModelAndView("actor/display");
			result.addObject("actor", actor);
			result.addObject("requestURI", "actor/display.do");
		}

		return result;
	}

	//Listing a competition's participants

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int varId) {
		final ModelAndView result;
		Competition competition = null;

		if (this.competitionService.findOne(varId) == null)
			result = new ModelAndView("redirect:/welcome/index.do");
		else {
			competition = this.competitionService.findOne(varId);
			final Collection<Actor> participants = new ArrayList<Actor>();
			for (final Participation p : competition.getParticipations())
				if (p.getStatus() == Status.ACCEPTED)
					participants.add(p.getActor());

			result = new ModelAndView("actor/list");
			result.addObject("participants", participants);
			result.addObject("varId", varId);
			result.addObject("requestURI", "actor/list.do");
		}

		return result;
	}

	//Editing

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		final ModelAndView result;
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		result = this.createEditModelAndView(actor);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Actor a, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(a);
		else
			try {
				final Actor actor = this.actorService.reconstruct(a, binding);
				this.actorService.save(actor);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(a, "actor.commit.error");
			}
		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final Actor actor) {
		ModelAndView result;

		result = this.createEditModelAndView(actor, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Actor actor, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("actor/edit");
		result.addObject("actor", actor);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "actor/edit.do");

		return result;

	}
}
