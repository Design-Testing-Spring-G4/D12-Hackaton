
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ResortRepository;
import domain.Activity;
import domain.Audit;
import domain.Competition;
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

	public void delete(final Resort resort) {
		Assert.notNull(resort);

		//Assertion that the user modifying this miscellaneous record has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == resort.getManager().getId());

		this.resortRepository.delete(resort);
	}

	//Other methods

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
}
