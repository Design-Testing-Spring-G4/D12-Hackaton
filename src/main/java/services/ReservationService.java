
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ReservationRepository;
import domain.Activity;
import domain.Lesson;
import domain.Reservation;
import domain.Resort;
import domain.Status;
import domain.User;

@Service
@Transactional
public class ReservationService {

	//Managed repository

	@Autowired
	private ReservationRepository	reservationRepository;

	//Supporting services

	@Autowired
	private ActorService			actorService;

	@Autowired
	private ResortService			resortService;

	@Autowired
	private ConfigurationService	configurationService;


	//Simple CRUD methods

	public Reservation create(final int varId) {

		final Reservation reservation = new Reservation();

		final User user = (User) this.actorService.findByPrincipal();
		reservation.setUser(user);
		final Resort resort = this.resortService.findOne(varId);
		reservation.setResort(resort);
		reservation.setStatus(Status.PENDING);
		reservation.setActivities(new ArrayList<Activity>());
		reservation.setLessons(new ArrayList<Lesson>());

		return reservation;
	}

	public Reservation findOne(final int id) {
		Assert.notNull(id);

		return this.reservationRepository.findOne(id);
	}

	public Collection<Reservation> findAll() {
		return this.reservationRepository.findAll();
	}

	public Reservation save(final Reservation reservation) {
		Assert.notNull(reservation);

		//Assertion that the user modifying this miscellaneous record has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == reservation.getUser().getId());

		//Business rule: the end date must be after the start date.
		Assert.isTrue(reservation.getEndDate().after(reservation.getStartDate()));
		//Business rule: the reservation period must be contained in the resort's activity period.
		Assert.isTrue(reservation.getStartDate().after(reservation.getResort().getStartDate()));
		Assert.isTrue(reservation.getEndDate().before(reservation.getResort().getEndDate()));

		reservation.setPrice(this.computePrice(reservation));

		final Reservation saved = this.reservationRepository.save(reservation);
		return saved;
	}

	public void delete(final Reservation reservation) {
		Assert.notNull(reservation);

		//Assertion that the user deleting this miscellaneous record has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == reservation.getUser().getId());

		this.reservationRepository.delete(reservation);
	}

	//Other methods

	public Double[] avgMinMaxStddevPricePerReservation() {
		return this.reservationRepository.avgMinMaxStddevPricePerReservation();
	}

	//Automatically compute the reservation's price from the number of adults and children, activities and lessons, plus the system's VAT tax.
	public Double computePrice(final Reservation reservation) {
		final Double adultCost = reservation.getAdults() * reservation.getResort().getPriceAdult();
		final Double childrenCost = reservation.getChildren() * reservation.getResort().getPriceChild();
		Double price = adultCost + childrenCost;
		for (final Activity a : reservation.getActivities())
			price += a.getPrice();
		for (final Lesson l : reservation.getLessons())
			price += l.getPrice();
		final Double vat = this.configurationService.findAll().iterator().next().getVat();

		return price + (price * vat);
	}
}
