
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

import services.ActorService;
import services.MailMessageService;
import services.ReservationService;
import controllers.AbstractController;
import domain.Manager;
import domain.Reservation;
import domain.Resort;
import domain.Status;

@Controller
@RequestMapping("reservation/manager")
public class ReservationManagerController extends AbstractController {

	//Services

	@Autowired
	private ActorService		actorService;

	@Autowired
	private ReservationService	reservationService;

	@Autowired
	private MailMessageService	mailMessageService;


	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Manager manager = (Manager) this.actorService.findByPrincipal();
		final Collection<Reservation> reservations = new ArrayList<Reservation>();

		for (final Resort r : manager.getResorts())
			reservations.addAll(r.getReservations());

		result = new ModelAndView("reservation/list");
		result.addObject("reservations", reservations);
		result.addObject("requestURI", "reservation/manager/list.do");

		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int varId) {
		final ModelAndView result;
		final Reservation reservation = this.reservationService.findOne(varId);
		Assert.notNull(reservation);

		if (reservation.getStatus() != Status.PENDING)
			result = this.list();
		else
			result = this.createEditModelAndView(reservation);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Reservation r, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(r);
		else
			try {
				if (r.getStatus() == Status.REJECTED && (r.getReason() == null || r.getReason().length() == 0))
					result = this.createEditModelAndView(r, "reservation.status.error");
				else {
					//Reconstruction functions carried over due to service request failures.
					final Reservation reservation = this.reservationService.findOne(r.getId());
					reservation.setCreditCard(r.getCreditCard());
					reservation.setStatus(r.getStatus());
					if (r.getStatus() == Status.REJECTED)
						reservation.setReason(r.getReason());
					final Reservation saved = this.reservationService.saveInternal(reservation);
					result = new ModelAndView("redirect:/reservation/manager/list.do");
					this.mailMessageService.applicationStatusNotification(saved.getUser().getId(), saved.getResort().getManager().getId());
				}
			} catch (final Throwable oops) {
				oops.printStackTrace();
				result = this.createEditModelAndView(r, "reservation.commit.error");
			}
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
		result.addObject("requestURI", "reservation/manager/edit.do");

		return result;
	}
}
