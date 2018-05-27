
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.CompetitionRepository;
import domain.Competition;
import domain.Participation;
import domain.Resort;
import domain.Sponsor;
import domain.Suggestion;

@Service
@Transactional
public class CompetitionService {

	//Managed repository

	@Autowired
	private CompetitionRepository	competitionRepository;

	//Supporting services

	@Autowired
	private ActorService			actorService;

	@Autowired
	private Validator				validator;

	@Autowired
	private ResortService			resortService;


	//Simple CRUD methods

	public Competition create() {

		final Competition competition = new Competition();

		final Sponsor sponsor = (Sponsor) this.actorService.findByPrincipal();
		competition.setSponsor(sponsor);
		competition.setSuggestions(new ArrayList<Suggestion>());
		competition.setParticipations(new ArrayList<Participation>());

		return competition;
	}

	public Competition findOne(final int id) {
		Assert.notNull(id);

		return this.competitionRepository.findOne(id);
	}

	public Collection<Competition> findAll() {
		return this.competitionRepository.findAll();
	}

	public Competition save(final Competition competition) {
		Assert.notNull(competition);

		//Assertion that the user modifying this miscellaneous record has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == competition.getSponsor().getId());

		//Business rule: the end date must be after the start date.
		Assert.isTrue(competition.getEndDate().after(competition.getStartDate()));

		final Competition saved = this.competitionRepository.save(competition);

		this.actorService.isSpam(saved.getBanner());
		this.actorService.isSpam(saved.getDescription());
		this.actorService.isSpam(saved.getLink());
		this.actorService.isSpam(saved.getRules());
		this.actorService.isSpam(saved.getSports());
		this.actorService.isSpam(saved.getTitle());

		return saved;
	}

	//Save for internal operations such as cross-authorized requests.
	public Competition saveInternal(final Competition competition) {
		Assert.notNull(competition);

		//Assertion that the user modifying this miscellaneous record has the correct privilege.
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		final String auth = authentication.getAuthorities().toArray()[0].toString();
		Assert.isTrue(auth.equals("USER") || auth.equals("INSTRUCTOR"));

		final Competition saved = this.competitionRepository.save(competition);
		return saved;
	}

	public void delete(final Competition competition) {
		Assert.notNull(competition);

		//Assertion that the user deleting this miscellaneous record has the correct privilege.
		final Competition validator = this.findOne(competition.getId());
		Assert.isTrue(this.actorService.findByPrincipal().getId() == validator.getSponsor().getId());

		final Resort resort = this.resortService.resortWithCompetition(competition);
		resort.getCompetitions().remove(competition);
		this.resortService.saveInternal(resort);

		this.competitionRepository.delete(competition);
	}

	//Other methods

	public Competition reconstruct(final Competition competition, final BindingResult binding) {
		Competition result;

		if (competition.getId() == 0) {
			result = this.create();
			result.setBanner(competition.getBanner());
			result.setDescription(competition.getDescription());
			result.setEndDate(competition.getEndDate());
			result.setStartDate(competition.getStartDate());
			result.setEntry(competition.getEntry());
			result.setLink(competition.getLink());
			result.setMaxParticipants(competition.getMaxParticipants());
			result.setPrizePool(competition.getPrizePool());
			result.setRules(competition.getRules());
			result.setSports(competition.getSports());
			result.setTitle(competition.getTitle());
		} else {
			result = this.findOne(competition.getId());
			result.setBanner(competition.getBanner());
			result.setDescription(competition.getDescription());
			result.setEndDate(competition.getEndDate());
			result.setStartDate(competition.getStartDate());
			result.setEntry(competition.getEntry());
			result.setLink(competition.getLink());
			result.setMaxParticipants(competition.getMaxParticipants());
			result.setPrizePool(competition.getPrizePool());
			result.setRules(competition.getRules());
			result.setSports(competition.getSports());
			result.setTitle(competition.getTitle());
		}

		this.validator.validate(result, binding);

		return result;
	}

	public List<Competition> topFiveCompetitionsPrizePool() {
		final Pageable top = new PageRequest(0, 5);
		return this.competitionRepository.topFiveCompetitionsPrizePool(top);
	}

	public List<Competition> topFiveCompetitionsMaxParticipants() {
		final Pageable top = new PageRequest(0, 5);
		return this.competitionRepository.topFiveCompetitionsMaxParticipants(top);
	}

	public Collection<Competition> competitionsWithBanner() {
		return this.competitionRepository.competitionsWithBanner();
	}

	public Competition competitionWithParticipation(final Participation participation) {
		return this.competitionRepository.competitionWithParticipation(participation);
	}
}
