
package controllers.manager;

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

import services.ActivityService;
import services.ActorService;
import services.InstructorService;
import services.ReservationService;
import controllers.AbstractController;
import domain.Activity;
import domain.Instructor;
import domain.Manager;
import domain.Reservation;
import domain.Resort;

@Controller
@RequestMapping("activity/manager")
public class ActivityManagerController extends AbstractController {

	//Services

	@Autowired
	private ActorService		actorService;

	@Autowired
	private ActivityService		activityService;

	@Autowired
	private ReservationService	reservationService;

	@Autowired
	private InstructorService	instructorService;


	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Manager manager = (Manager) this.actorService.findByPrincipal();
		final Collection<Activity> activities = new ArrayList<Activity>();
		for (final Resort r : manager.getResorts())
			activities.addAll(r.getActivities());

		result = new ModelAndView("activity/list");
		result.addObject("activities", activities);
		result.addObject("requestURI", "activity/manager/list.do");

		return result;
	}

	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		final Activity activity = this.activityService.create();
		result = this.createEditModelAndView(activity);

		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int varId) {
		final ModelAndView result;
		final Activity activity = this.activityService.findOne(varId);
		Assert.notNull(activity);
		result = this.createEditModelAndView(activity);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Activity a, final BindingResult binding) {
		ModelAndView result;

		if (a.getId() != 0 && a.getResort() == null)
			binding.rejectValue("resort", "javax.validation.constraints.NotNull.message");
		if (a.getId() != 0 && a.getTitle().isEmpty())
			binding.rejectValue("title", "org.hibernate.validator.constraints.NotEmpty.message");
		if (a.getId() != 0 && a.getDescription().isEmpty())
			binding.rejectValue("description", "org.hibernate.validator.constraints.NotEmpty.message");
		if (binding.hasErrors())
			result = this.createEditModelAndView(a);
		else
			try {
				final Activity activity = this.activityService.reconstruct(a, binding);
				this.activityService.save(activity);
				result = new ModelAndView("redirect:/activity/manager/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(a, "activity.commit.error");
			}
		return result;
	}

	//Deletion

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Activity activity, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(activity);
		else
			try {
				for (final Reservation r : this.reservationService.findAll())
					if (r.getActivities().contains(activity)) {
						result = this.createEditModelAndView(activity, "activity.delete.error");
						break;
					}
				this.activityService.delete(activity);
				result = new ModelAndView("redirect:/activity/manager/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(activity, "activity.commit.error");
			}
		return result;
	}


	//Assignment of instructor

	private Activity	current;


	public Activity getCurrent() {
		return this.current;
	}

	public void setCurrent(final Activity current) {
		this.current = current;
	}

	@RequestMapping(value = "/manage", method = RequestMethod.GET)
	public ModelAndView manage(@RequestParam final int varId) {
		final ModelAndView result;
		final Resort resort = this.activityService.findOne(varId).getResort();
		final Collection<Instructor> instructors = this.instructorService.instructorsWithResort(resort);

		result = new ModelAndView("actor/list");
		result.addObject("participants", instructors);
		result.addObject("requestURI", "activity/manager/manage.do");

		this.setCurrent(this.activityService.findOne(varId));

		return result;
	}

	@RequestMapping(value = "/set", method = RequestMethod.GET)
	public ModelAndView set(@RequestParam final int varId) {
		ModelAndView result;
		final Activity activity = this.getCurrent();
		final Instructor instructor = this.instructorService.findOne(varId);

		try {
			activity.setInstructor(instructor);
			this.activityService.save(activity);
			result = new ModelAndView("redirect:/activity/manager/list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/activity/manager/list.do");
		}

		return result;
	}

	@RequestMapping(value = "/unset", method = RequestMethod.GET)
	public ModelAndView unset(@RequestParam final int varId) {
		ModelAndView result;
		final Activity activity = this.activityService.findOne(varId);
		activity.setInstructor(null);
		this.activityService.save(activity);
		result = new ModelAndView("redirect:/activity/manager/list.do");

		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final Activity activity) {
		ModelAndView result;

		result = this.createEditModelAndView(activity, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Activity activity, final String messageCode) {
		ModelAndView result;
		final Collection<Resort> resorts = ((Manager) this.actorService.findByPrincipal()).getResorts();

		result = new ModelAndView("activity/edit");
		result.addObject("activity", activity);
		result.addObject("message", messageCode);
		result.addObject("resorts", resorts);
		result.addObject("requestURI", "activity/manager/edit.do");

		return result;
	}
}
