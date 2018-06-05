
package controllers.sponsor;

import java.util.Collection;
import java.util.Date;

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
import domain.Competition;
import domain.Sponsor;

@Controller
@RequestMapping("competition/sponsor")
public class CompetitionSponsorController extends AbstractController {

	//Services

	@Autowired
	private ActorService		actorService;

	@Autowired
	private CompetitionService	competitionService;


	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Sponsor sponsor = (Sponsor) this.actorService.findByPrincipal();
		final Collection<Competition> competitions = sponsor.getCompetitions();

		result = new ModelAndView("competition/list");
		result.addObject("competitions", competitions);
		result.addObject("requestURI", "competition/sponsor/list.do");

		return result;
	}

	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		final Competition competition = this.competitionService.create();
		result = this.createEditModelAndView(competition);

		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int varId) {
		final ModelAndView result;
		final Competition competition = this.competitionService.findOne(varId);
		Assert.notNull(competition);
		result = this.createEditModelAndView(competition);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Competition c, final BindingResult binding) {
		ModelAndView result;

		if (c.getId() != 0 && c.getMaxParticipants() < 2)
			binding.rejectValue("maxParticipants", "javax.validation.constraints.Min2.message");
		if (binding.hasErrors())
			result = this.createEditModelAndView(c);
		else
			try {
				final Competition competition = this.competitionService.reconstruct(c, binding);
				this.competitionService.save(competition);
				result = new ModelAndView("redirect:/competition/sponsor/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(c, "competition.commit.error");
			}
		return result;
	}

	//Deletion

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Competition competition, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(competition);
		else
			try {
				final Date currentDate = new Date(System.currentTimeMillis());
				if (competition.getStartDate().before(currentDate) && competition.getEndDate().after(currentDate))
					result = this.createEditModelAndView(competition, "competition.delete.error");
				else {
					this.competitionService.delete(competition);
					result = new ModelAndView("redirect:/competition/sponsor/list.do");
				}
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(competition, "competition.commit.error");
			}
		return result;
	}
	//Ancillary methods

	protected ModelAndView createEditModelAndView(final Competition competition) {
		ModelAndView result;

		result = this.createEditModelAndView(competition, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Competition competition, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("competition/edit");
		result.addObject("competition", competition);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "competition/sponsor/edit.do");

		return result;
	}
}
