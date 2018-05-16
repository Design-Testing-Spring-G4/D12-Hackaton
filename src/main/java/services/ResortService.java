
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
import domain.Manager;
import domain.Reservation;
import domain.Resort;
import domain.Tag;

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
		resort.setTags(new ArrayList<Tag>());
		resort.setActivities(new ArrayList<Activity>());
		resort.setReservations(new ArrayList<Reservation>());
		resort.setAudits(new ArrayList<Audit>());

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

		final Resort saved = this.resortRepository.save(resort);

		return saved;
	}

	public void delete(final Resort resort) {
		Assert.notNull(resort);

		this.resortRepository.delete(resort);
	}
}
