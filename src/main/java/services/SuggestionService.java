
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SuggestionRepository;
import domain.Actor;
import domain.Suggestion;

@Service
@Transactional
public class SuggestionService {

	//Managed repository

	@Autowired
	private SuggestionRepository	suggestionRepository;

	//Supporting services

	@Autowired
	private ActorService			actorService;


	//Simple CRUD methods

	public Suggestion create() {

		final Suggestion suggestion = new Suggestion();

		final Actor actor = this.actorService.findByPrincipal();
		suggestion.setActor(actor);
		suggestion.setAnonymous(false);

		return suggestion;
	}

	public Suggestion findOne(final int id) {
		Assert.notNull(id);

		return this.suggestionRepository.findOne(id);
	}

	public Collection<Suggestion> findAll() {
		return this.suggestionRepository.findAll();
	}

	public Suggestion save(final Suggestion suggestion) {
		Assert.notNull(suggestion);

		//Assertion that the user modifying this miscellaneous record has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == suggestion.getActor().getId());

		final Suggestion saved = this.suggestionRepository.save(suggestion);
		return saved;
	}

	public void delete(final Suggestion suggestion) {
		Assert.notNull(suggestion);

		//Assertion that the user deleting this miscellaneous record has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == suggestion.getActor().getId());

		this.suggestionRepository.delete(suggestion);
	}

}
