
package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SponsorRepository;
import security.Authority;
import security.UserAccount;
import domain.Competition;
import domain.Folder;
import domain.Participation;
import domain.SocialIdentity;
import domain.Sponsor;

@Service
@Transactional
public class SponsorService {

	//Managed repository

	@Autowired
	private SponsorRepository	sponsorRepository;

	//Supporting services

	@Autowired
	private FolderService		folderService;

	@Autowired
	private ActorService		actorService;


	//Simple CRUD Methods

	public Sponsor create() {
		final Authority a = new Authority();
		a.setAuthority(Authority.SPONSOR);
		final UserAccount account = new UserAccount();
		account.setAuthorities(Arrays.asList(a));

		final Sponsor sponsor = new Sponsor();
		sponsor.setSuspicious(false);
		sponsor.setSocialIdentities(new ArrayList<SocialIdentity>());
		sponsor.setUserAccount(account);
		sponsor.setFolders(new ArrayList<Folder>());
		sponsor.setParticipations(new ArrayList<Participation>());
		sponsor.setCompetitions(new ArrayList<Competition>());

		return sponsor;
	}

	public Collection<Sponsor> findAll() {
		return this.sponsorRepository.findAll();
	}

	public Sponsor findOne(final int id) {
		Assert.notNull(id);

		return this.sponsorRepository.findOne(id);
	}

	public Sponsor save(final Sponsor sponsor) {
		Assert.notNull(sponsor);

		final Sponsor saved2;
		//For new actors, generate the default system folders.
		if (sponsor.getId() != 0) {
			Assert.isTrue(this.actorService.findByPrincipal().getId() == sponsor.getId());
			saved2 = this.sponsorRepository.save(sponsor);
		} else {
			final Sponsor saved = this.sponsorRepository.save(sponsor);
			saved.setFolders(this.folderService.generateDefaultFolders(saved));
			saved2 = this.sponsorRepository.save(saved);
		}

		this.actorService.isSpam(saved2.getAddress());
		this.actorService.isSpam(saved2.getEmail());
		this.actorService.isSpam(saved2.getName());
		this.actorService.isSpam(saved2.getPhone());
		this.actorService.isSpam(saved2.getSurname());

		return saved2;
	}

	public void delete(final Sponsor sponsor) {
		Assert.notNull(sponsor);

		//Assertion that the user deleting this sponsor has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == sponsor.getId());

		this.sponsorRepository.delete(sponsor);
	}

	//Other methods

	public Double[] minMaxAvgStdddevCompetitionsPerSponsor() {
		return this.sponsorRepository.minMaxAvgStdddevCompetitionsPerSponsor();
	}
}
