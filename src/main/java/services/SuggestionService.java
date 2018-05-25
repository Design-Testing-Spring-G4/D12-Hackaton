
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.SuggestionRepository;
import domain.Actor;
import domain.Competition;
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

	@Autowired
	private Validator				validator;


	//Simple CRUD methods

	public Suggestion create(final Competition competition) {

		final Suggestion suggestion = new Suggestion();

		final Actor actor = this.actorService.findByPrincipal();
		suggestion.setActor(actor);
		suggestion.setAnonymous(false);
		suggestion.setCompetition(competition);

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

		this.actorService.isSpam(saved.getAttachments());
		this.actorService.isSpam(saved.getComments());
		this.actorService.isSpam(saved.getTitle());

		return saved;
	}

	public void delete(final Suggestion suggestion) {
		Assert.notNull(suggestion);

		//Assertion that the user deleting this miscellaneous record has the correct privilege.
		final Suggestion validator = this.findOne(suggestion.getId());
		Assert.isTrue(this.actorService.findByPrincipal().getId() == validator.getActor().getId());

		this.suggestionRepository.delete(suggestion);
	}

	//Other methods

	public Suggestion reconstruct(final Suggestion suggestion, final Competition competition, final BindingResult binding) {
		Suggestion result;

		result = this.create(competition);
		result.setAnonymous(suggestion.isAnonymous());
		result.setAttachments(suggestion.getAttachments());
		result.setComments(suggestion.getComments());
		result.setTitle(suggestion.getTitle());

		this.validator.validate(result, binding);

		return result;
	}
}
