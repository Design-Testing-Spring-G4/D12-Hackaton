
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SocialIdentityRepository;
import domain.Actor;
import domain.SocialIdentity;

@Service
@Transactional
public class SocialIdentityService {

	// Managed service
	@Autowired
	private SocialIdentityRepository	socialIdentityRepository;

	// Supporting service

	@Autowired
	private ActorService				actorService;


	// Simple CRUD methods

	public SocialIdentity create() {
		final SocialIdentity socialIdentity = new SocialIdentity();
		final Actor actor = this.actorService.findByPrincipal();
		socialIdentity.setActor(actor);

		return socialIdentity;
	}

	public SocialIdentity findOne(final int id) {
		Assert.notNull(id);

		return this.socialIdentityRepository.findOne(id);
	}

	public Collection<SocialIdentity> findAll() {
		return this.socialIdentityRepository.findAll();
	}

	public SocialIdentity save(final SocialIdentity socialIdentity) {
		Assert.notNull(socialIdentity);

		//Assertion that the user deleting this social identity has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == socialIdentity.getActor().getId());

		final SocialIdentity saved = this.socialIdentityRepository.save(socialIdentity);

		this.actorService.isSpam(saved.getProfile());
		this.actorService.isSpam(saved.getNick());
		this.actorService.isSpam(saved.getPhoto());
		this.actorService.isSpam(saved.getNetwork());

		return saved;
	}

	public void delete(final SocialIdentity si) {
		Assert.notNull(si);

		//Assertion that the user deleting this social identity has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == si.getActor().getId());

		this.socialIdentityRepository.delete(si);

	}
}
