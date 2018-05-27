
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ParticipationRepository;
import domain.Actor;
import domain.Participation;
import domain.Status;

@Service
@Transactional
public class ParticipationService {

	//Managed repository

	@Autowired
	private ParticipationRepository	participationRepository;

	//Supporting services

	@Autowired
	private ActorService			actorService;

	@Autowired
	private Validator				validator;


	//Simple CRUD methods

	public Participation create() {

		final Participation participation = new Participation();

		final Actor actor = this.actorService.findByPrincipal();
		participation.setActor(actor);
		participation.setStatus(Status.ACCEPTED);

		return participation;
	}

	public Participation findOne(final int id) {
		Assert.notNull(id);

		return this.participationRepository.findOne(id);
	}

	public Collection<Participation> findAll() {
		return this.participationRepository.findAll();
	}

	public Participation save(final Participation participation) {
		Assert.notNull(participation);

		//Assertion that the user modifying this miscellaneous record has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == participation.getActor().getId());

		participation.setMoment(new Date(System.currentTimeMillis() - 1));

		final Participation saved = this.participationRepository.save(participation);

		this.actorService.isSpam(saved.getComments());

		return saved;
	}

	public void delete(final Participation participation) {
		Assert.notNull(participation);

		//Assertion that the user deleting this miscellaneous record has the correct privilege.
		final Participation validator = this.findOne(participation.getId());
		Assert.isTrue(this.actorService.findByPrincipal().getId() == validator.getActor().getId());

		this.participationRepository.delete(participation);
	}

	public Participation reconstruct(final Participation participation, final BindingResult binding) {
		Participation result;

		if (participation.getId() == 0) {
			result = this.create();
			result.setComments(participation.getComments());
		} else {
			result = this.findOne(participation.getId());
			result.setComments(participation.getComments());
		}

		this.validator.validate(result, binding);

		return result;
	}
}
