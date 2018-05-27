
package controllers.manager;

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
import services.LegalTextService;
import services.ResortService;
import controllers.AbstractController;
import domain.LegalText;
import domain.Manager;
import domain.Resort;

@Controller
@RequestMapping("resort/manager")
public class ResortManagerController extends AbstractController {

	//Services

	@Autowired
	private ActorService		actorService;

	@Autowired
	private ResortService		resortService;

	@Autowired
	private LegalTextService	legalTextService;


	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Manager manager = (Manager) this.actorService.findByPrincipal();
		final Collection<Resort> resorts = manager.getResorts();

		result = new ModelAndView("resort/list");
		result.addObject("resorts", resorts);
		result.addObject("requestURI", "resort/manager/list.do");

		return result;
	}

	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		final Resort resort = this.resortService.create();
		result = this.createEditModelAndView(resort);

		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int varId) {
		final ModelAndView result;
		final Resort resort = this.resortService.findOne(varId);
		Assert.notNull(resort);
		result = this.createEditModelAndView(resort);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Resort r, final BindingResult binding) {
		ModelAndView result;
		if (r.getId() != 0) {
			if (r.getName().isEmpty()) {
				binding.rejectValue("name", "org.hibernate.validator.constraints.NotBlank.message");
				result = this.createEditModelAndView(r, "resort.commit.error");
			} else if (r.getDescription().isEmpty()) {
				binding.rejectValue("description", "org.hibernate.validator.constraints.NotBlank.message");
				result = this.createEditModelAndView(r, "resort.commit.error");
			} else if (r.getLocation().getLocation().isEmpty()) {
				binding.rejectValue("location.location", "org.hibernate.validator.constraints.NotBlank.message");
				result = this.createEditModelAndView(r, "resort.commit.error");
			} else if (r.getFeatures().isEmpty()) {
				binding.rejectValue("features", "org.hibernate.validator.constraints.NotBlank.message");
				result = this.createEditModelAndView(r, "resort.commit.error");
			} else if (r.getStartDate() == null) {
				binding.rejectValue("startDate", "javax.validation.constraints.NotNull.message");
				result = this.createEditModelAndView(r, "resort.commit.error");
			} else if (r.getEndDate() == null) {
				binding.rejectValue("endDate", "javax.validation.constraints.NotNull.message");
				result = this.createEditModelAndView(r, "resort.commit.error");
			} else if (!r.getLocation().getGpsCoordinates().matches("^[-+]?\\d{1,2}\\.\\d{1,2}\\,\\ [-+]?\\d{1,2}\\.\\d{1,2}$")) {
				binding.rejectValue("location.gpsCoordinates", "javax.validation.constraints.Pattern.message");
				result = this.createEditModelAndView(r, "resort.commit.error");
			} else
				try {
					final Resort resort = this.resortService.reconstruct(r, binding);
					this.resortService.save(resort);
					result = new ModelAndView("redirect:/resort/manager/list.do");
				} catch (final Throwable oops) {
					result = this.createEditModelAndView(r, "resort.commit.error");
				}
		} else if (binding.hasErrors())
			result = this.createEditModelAndView(r);
		else
			try {
				final Resort resort = this.resortService.reconstruct(r, binding);
				this.resortService.save(resort);
				result = new ModelAndView("redirect:/resort/manager/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(r, "resort.commit.error");
			}
		return result;
	}
	//Deletion

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Resort resort, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(resort);
		else
			try {
				final Date currentDate = new Date(System.currentTimeMillis());
				if (resort.getStartDate().before(currentDate) && resort.getEndDate().after(currentDate))
					result = this.createEditModelAndView(resort, "resort.date.error");
				else {
					this.resortService.delete(resort);
					result = new ModelAndView("redirect:/resort/manager/list.do");
				}
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(resort, "resort.commit.error");
			}
		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final Resort resort) {
		ModelAndView result;

		result = this.createEditModelAndView(resort, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Resort resort, final String messageCode) {
		ModelAndView result;
		final Collection<LegalText> legalTexts = this.legalTextService.legalTextsFinalMode();

		result = new ModelAndView("resort/edit");
		result.addObject("resort", resort);
		result.addObject("message", messageCode);
		result.addObject("legalTexts", legalTexts);
		result.addObject("requestURI", "resort/manager/edit.do");

		return result;
	}
}
