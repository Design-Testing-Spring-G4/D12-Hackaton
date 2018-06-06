
package controllers.user;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActivityService;
import services.ActorService;
import services.MailMessageService;
import services.ReservationService;
import services.ResortService;
import controllers.AbstractController;
import domain.Activity;
import domain.Reservation;
import domain.Resort;
import domain.Status;
import domain.User;

@Controller
@RequestMapping("reservation/user")
public class ReservationUserController extends AbstractController {

	//Services

	@Autowired
	private ReservationService	reservationService;

	@Autowired
	private ResortService		resortService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private ActivityService		activityService;

	@Autowired
	private MailMessageService	mailMessageService;


	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final User user = (User) this.actorService.findByPrincipal();
		final Collection<Reservation> reservations = user.getReservations();

		result = new ModelAndView("reservation/list");
		result.addObject("reservations", reservations);
		result.addObject("requestURI", "reservation/user/list.do");

		return result;
	}

	//Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int varId) {
		final ModelAndView result;
		Reservation reservation = null;

		if (this.reservationService.findOne(varId) == null)
			result = new ModelAndView("redirect:/welcome/index.do");
		else {
			reservation = this.reservationService.findOne(varId);
			final User user = (User) this.actorService.findByPrincipal();

			if (reservation.getUser() != user)
				result = new ModelAndView("redirect:/reservation/user/list.do");
			else {
				result = new ModelAndView("reservation/display");
				result.addObject("reservation", reservation);
				result.addObject("requestURI", "reservation/user/display.do");
			}
		}

		return result;
	}

	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int varId) {
		final ModelAndView result;
		final Resort resort = this.resortService.findOne(varId);
		if (resort.getSpots() == 0)
			result = new ModelAndView("redirect:/resort/list.do");
		else {
			final Reservation reservation = this.reservationService.create(varId);
			result = this.createEditModelAndView(reservation);
		}

		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int varId) {
		final ModelAndView result;
		final Reservation reservation = this.reservationService.findOne(varId);
		Assert.notNull(reservation);
		result = this.createEditModelAndView(reservation);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Reservation r, final BindingResult binding) {
		ModelAndView result;

		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Assert.isTrue(authentication.getAuthorities().toArray()[0].toString().equals("USER"));

		if (r.getAdults() < 1)
			binding.rejectValue("adults", "javax.validation.constraints.Min2.message");
		if (r.getChildren() == null)
			binding.rejectValue("children", "org.hibernate.validator.constraints.NotEmpty.message");
		if (r.getId() != 0 && r.getCreditCard().getBrand().isEmpty())
			binding.rejectValue("creditCard.brand", "org.hibernate.validator.constraints.NotEmpty.message");
		if (r.getId() != 0 && r.getCreditCard().getHolder().isEmpty())
			binding.rejectValue("creditCard.holder", "org.hibernate.validator.constraints.NotEmpty.message");
		if (r.getId() != 0 && r.getCreditCard().getNumber().isEmpty())
			binding.rejectValue("creditCard.number", "org.hibernate.validator.constraints.NotEmpty.message");
		if (r.getId() != 0 && r.getCreditCard().getExpMonth() == null)
			binding.rejectValue("creditCard.expMonth", "org.hibernate.validator.constraints.NotEmpty.message");
		if (r.getId() != 0 && r.getCreditCard().getExpYear() == null)
			binding.rejectValue("creditCard.expYear", "org.hibernate.validator.constraints.NotEmpty.message");
		if (r.getId() != 0 && r.getCreditCard().getCvv() == null)
			binding.rejectValue("creditCard.cvv", "org.hibernate.validator.constraints.NotEmpty.message");
		if (binding.hasErrors())
			result = this.createEditModelAndView(r, "reservation.commit.error");
		else
			try {
				final Reservation saved = this.reservationService.save(r);
				if (r.getStatus() == Status.ACCEPTED) {
					final Date current = new Date(System.currentTimeMillis());
					if (r.getStartDate().before(current))
						result = this.createEditModelAndView(r, "reservation.date.error");
					else
						result = new ModelAndView("redirect:/reservation/user/list.do");
				} else
					result = new ModelAndView("redirect:/resort/list.do");
				this.mailMessageService.reservationStatusNotification(saved.getUser().getId(), saved.getResort().getManager().getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(r, "reservation.commit.error");
			}
		return result;
	}

	//Cancelling

	@RequestMapping(value = "/cancel", method = RequestMethod.GET)
	public ModelAndView cancel(@RequestParam final int varId) {
		ModelAndView result;
		final Reservation reservation = this.reservationService.findOne(varId);
		final Date current = new Date(System.currentTimeMillis());
		if (reservation.getStatus() == Status.ACCEPTED && reservation.getStartDate().after(current)) {
			reservation.setStatus(Status.CANCELLED);
			this.reservationService.save(reservation);
			result = new ModelAndView("redirect:/reservation/user/list.do");
		} else
			result = new ModelAndView("redirect:/reservation/user/list.do");

		return result;
	}

	//Requesting activities

	@RequestMapping(value = "/activities", method = RequestMethod.GET)
	public ModelAndView activites(@RequestParam final int varId) {
		ModelAndView result;
		final Reservation reservation = this.reservationService.findOne(varId);
		final Resort resort = reservation.getResort();
		final Collection<Activity> activities = this.activityService.activitiesFromResortNotFree(resort);

		result = new ModelAndView("activity/list");
		result.addObject("activities", activities);
		result.addObject("varId", varId);
		result.addObject("requestURI", "reservation/user/activities.do");

		return result;
	}

	@RequestMapping(value = "/request", method = RequestMethod.GET)
	public ModelAndView request(@RequestParam final int varId, final int varId2) {
		ModelAndView result;
		final Activity activity = this.activityService.findOne(varId);
		final Reservation reservation = this.reservationService.findOne(varId2);

		if (!reservation.getActivities().contains(activity)) {
			reservation.getActivities().add(activity);
			this.reservationService.save(reservation);
		}

		result = new ModelAndView("redirect:/reservation/user/activities.do?varId=" + varId2);

		return result;
	}
	//Ancillary methods

	protected ModelAndView createEditModelAndView(final Reservation reservation) {
		ModelAndView result;

		result = this.createEditModelAndView(reservation, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Reservation reservation, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("reservation/edit");
		result.addObject("reservation", reservation);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "reservation/user/edit.do");

		return result;
	}
}
