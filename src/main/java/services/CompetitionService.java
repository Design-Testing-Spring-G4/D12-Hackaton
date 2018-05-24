
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CompetitionRepository;
import domain.Competition;
import domain.Participation;
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

	public void delete(final Competition competition) {
		Assert.notNull(competition);

		//Assertion that the user deleting this miscellaneous record has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == competition.getSponsor().getId());

		this.competitionRepository.delete(competition);
	}

	//Other methods

	public List<Competition> topFiveCompetitionsPrizePool() {
		final Pageable top = new PageRequest(0, 5);
		return this.competitionRepository.topFiveCompetitionsPrizePool(top);
	}

	public List<Competition> topFiveCompetitionsMaxParticipants() {
		final Pageable top = new PageRequest(0, 5);
		return this.competitionRepository.topFiveCompetitionsMaxParticipants(top);
	}
}
