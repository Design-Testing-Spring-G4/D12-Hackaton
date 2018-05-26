
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ResortRepository;
import domain.Activity;
import domain.Audit;
import domain.Category;
import domain.Competition;
import domain.Instructor;
import domain.Manager;
import domain.Reservation;
import domain.Resort;
import domain.TagValue;

@Service
@Transactional
public class ResortService {

	//Managed repository

	@Autowired
	private ResortRepository	resortRepository;

	//Supporting services

	@Autowired
	private ActorService		actorService;

	@Autowired
	private Validator			validator;

	@Autowired
	private InstructorService	instructorService;

	@Autowired
	private ReservationService	reservationService;

	@Autowired
	private AuditService		auditService;

	@Autowired
	private CategoryService		categoryService;

	@Autowired
	private TagValueService		tagValueService;


	//Simple CRUD Methods

	public Resort create() {
		final Resort resort = new Resort();

		final Manager manager = (Manager) this.actorService.findByPrincipal();
		resort.setManager(manager);
		resort.setTags(new ArrayList<TagValue>());
		resort.setActivities(new ArrayList<Activity>());
		resort.setReservations(new ArrayList<Reservation>());
		resort.setAudits(new ArrayList<Audit>());
		resort.setCompetitions(new ArrayList<Competition>());

		return resort;
	}

	public Collection<Resort> findAll() {
		return this.resortRepository.findAll();
	}

	public Resort findOne(final int id) {
		Assert.notNull(id);

		return this.resortRepository.findOne(id);
	}

	public Resort save(final Resort resort) {
		Assert.notNull(resort);

		//Assertion that the user modifying this resort has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == resort.getManager().getId());

		//Business rule: the end date must be after the start date.
		Assert.isTrue(resort.getEndDate().after(resort.getStartDate()));

		final Resort saved = this.resortRepository.save(resort);

		this.actorService.isSpam(saved.getDescription());
		this.actorService.isSpam(saved.getFeatures());
		this.actorService.isSpam(saved.getName());
		this.actorService.isSpam(saved.getPicture());

		return saved;
	}

	public Resort saveInternal(final Resort resort) {
		Assert.notNull(resort);
		//Assertion that the user modifying this resort has the correct privilege.
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Assert.isTrue(authentication.getAuthorities().toArray()[0].toString().equals("SPONSOR"));
		final Resort saved = this.resortRepository.save(resort);
		return saved;
	}

	public void delete(final Resort resort) {
		Assert.notNull(resort);

		//Assertion that the user modifying this miscellaneous record has the correct privilege.
		final Resort validator = this.findOne(resort.getId());
		Assert.isTrue(this.actorService.findByPrincipal().getId() == validator.getManager().getId());

		for (final Instructor i : this.instructorService.findAll())
			if (i.getResorts().contains(resort)) {
				i.getResorts().remove(resort);
				this.instructorService.saveInternal(i);
			}
		for (final Reservation r : this.reservationService.findAll())
			if (r.getResort().equals(resort))
				this.reservationService.deleteInternal(r);
		for (final Audit a : this.auditService.findAll())
			if (a.getResort().equals(resort))
				this.auditService.deleteInternal(a);
		for (final Category c : this.categoryService.findAll())
			if (c.getResorts().contains(resort)) {
				c.getResorts().remove(resort);
				this.categoryService.saveInternal(c);
			}
		for (final TagValue tv : this.tagValueService.findAll())
			if (tv.getResorts().contains(resort)) {
				tv.getResorts().remove(resort);
				this.tagValueService.saveInternal(tv);
			}

		this.resortRepository.delete(resort);
	}
	//Other methods

	public Resort reconstruct(final Resort resort, final BindingResult binding) {
		Resort result;

		if (resort.getId() == 0) {
			result = this.create();
			result.setDescription(resort.getDescription());
			result.setEndDate(resort.getEndDate());
			result.setFeatures(resort.getFeatures());
			result.setLegalText(resort.getLegalText());
			result.setLocation(resort.getLocation());
			result.setName(resort.getName());
			result.setPicture(resort.getPicture());
			result.setPriceAdult(resort.getPriceAdult());
			result.setPriceChild(resort.getPriceChild());
			result.setSpots(resort.getSpots());
			result.setStartDate(resort.getStartDate());
		} else {
			result = this.findOne(resort.getId());
			result.setDescription(resort.getDescription());
			result.setEndDate(resort.getEndDate());
			result.setFeatures(resort.getFeatures());
			result.setLegalText(resort.getLegalText());
			result.setLocation(resort.getLocation());
			result.setName(resort.getName());
			result.setPicture(resort.getPicture());
			result.setPriceAdult(resort.getPriceAdult());
			result.setPriceChild(resort.getPriceChild());
			result.setSpots(resort.getSpots());
			result.setStartDate(resort.getStartDate());
		}

		this.validator.validate(result, binding);

		return result;
	}

	//Search the list of resorts and retrieve only those matching the keyword.
	public Collection<Resort> searchByKeyword(final String keyword) {
		final Collection<Resort> result = new ArrayList<Resort>();

		for (final Resort r : this.findAll())
			if (r.getName().toLowerCase().contains(keyword.toLowerCase()) || r.getDescription().toLowerCase().contains(keyword.toLowerCase()) || r.getLocation().getLocation().toLowerCase().contains(keyword.toLowerCase()))
				result.add(r);

		return result;
	}

	public Double[] avgMinMaxStddevReservationsPerResort() {
		return this.resortRepository.avgMinMaxStddevReservationsPerResort();
	}

	public Double[] avgMinMaxStddevActivitiesPerResort() {
		return this.resortRepository.avgMinMaxStddevActivitiesPerResort();
	}

	public Collection<Resort> resortsWithAboveAverageReservations() {
		return this.resortRepository.resortsWithAboveAverageReservations();
	}

	public Double ratioFullResorts() {
		return this.resortRepository.ratioFullResorts();
	}

	public Double[] minMaxAvgStddevAuditsPerResort() {
		return this.resortRepository.minMaxAvgStddevAuditsPerResort();
	}

	public Double ratioResortsWithAudit() {
		return this.resortRepository.ratioResortsWithAudit();
	}

	public Resort resortWithCompetition(final Competition competition) {
		return this.resortRepository.resortWithCompetition(competition);
	}
}
