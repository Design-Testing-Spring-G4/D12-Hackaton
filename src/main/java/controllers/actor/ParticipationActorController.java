
package controllers.actor;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.CompetitionService;
import services.ParticipationService;
import controllers.AbstractController;
import domain.Actor;
import domain.Competition;
import domain.Participation;
import domain.Status;

@Controller
@RequestMapping("participation/actor")
public class ParticipationActorController extends AbstractController {

	//Services

	@Autowired
	private ActorService			actorService;

	@Autowired
	private ParticipationService	participationService;

	@Autowired
	private CompetitionService		competitionService;


	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Actor actor = this.actorService.findByPrincipal();
		final Collection<Participation> participations = actor.getParticipations();

		result = new ModelAndView("participation/list");
		result.addObject("participations", participations);
		result.addObject("requestURI", "participation/actor/list.do");

		return result;
	}


	//Creation

	Integer	parId;


	public Integer getParId() {
		return this.parId;
	}

	public void setParId(final Integer parId) {
		this.parId = parId;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int varId) {
		final ModelAndView result;
		final Participation participation = this.participationService.create();
		result = this.createEditModelAndView(participation);
		this.setParId(varId);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Participation p, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(p);
		else
			try {
				final Participation participation = this.participationService.reconstruct(p, binding);
				final Participation saved = this.participationService.save(participation);
				final Competition competition = this.competitionService.findOne(this.getParId());
				competition.getParticipations().add(saved);
				this.competitionService.saveInternal(competition);
				result = new ModelAndView("redirect:/participation/actor/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(p, "participation.commit.error");
			}
		return result;
	}

	//Cancelling

	@RequestMapping(value = "/cancel", method = RequestMethod.GET)
	public ModelAndView cancel(@RequestParam final int varId) {
		ModelAndView result;
		final Participation participation = this.participationService.findOne(varId);
		final Competition competition = this.competitionService.competitionWithParticipation(participation);
		final Date current = new Date(System.currentTimeMillis());
		if (competition.getStartDate().after(current)) {
			participation.setStatus(Status.CANCELLED);
			this.participationService.save(participation);
			result = new ModelAndView("redirect:/participation/actor/list.do");
		} else
			result = new ModelAndView("redirect:/participation/actor/list.do");

		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final Participation participation) {
		ModelAndView result;

		result = this.createEditModelAndView(participation, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Participation participation, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("participation/edit");
		result.addObject("participation", participation);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "participation/actor/edit.do");

		return result;
	}
}
