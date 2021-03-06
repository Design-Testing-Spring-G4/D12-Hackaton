
package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AuditorRepository;
import security.Authority;
import security.UserAccount;
import domain.Audit;
import domain.Auditor;
import domain.Configuration;
import domain.Folder;
import domain.Note;
import domain.Participation;
import domain.SocialIdentity;

@Service
@Transactional
public class AuditorService {

	//Managed repository

	@Autowired
	private AuditorRepository		auditorRepository;

	//Supporting services

	@Autowired
	private ActorService			actorService;

	@Autowired
	private FolderService			folderService;

	@Autowired
	private ConfigurationService	configurationService;


	//Simple CRUD Methods

	public Auditor create() {
		final Authority a = new Authority();
		a.setAuthority(Authority.AUDITOR);
		final UserAccount account = new UserAccount();
		account.setAuthorities(Arrays.asList(a));

		final Auditor auditor = new Auditor();
		auditor.setSocialIdentities(new ArrayList<SocialIdentity>());
		auditor.setUserAccount(account);
		auditor.setFolders(new ArrayList<Folder>());
		auditor.setSuspicious(false);
		auditor.setParticipations(new ArrayList<Participation>());
		auditor.setAudits(new ArrayList<Audit>());
		auditor.setNotes(new ArrayList<Note>());

		return auditor;
	}

	public Collection<Auditor> findAll() {
		return this.auditorRepository.findAll();
	}

	public Auditor findOne(final int id) {
		Assert.notNull(id);

		return this.auditorRepository.findOne(id);
	}

	public Auditor save(final Auditor auditor) {
		Assert.notNull(auditor);

		if (!auditor.getPhone().startsWith("+")) {
			final Configuration configuration = this.configurationService.findAll().iterator().next();
			final String newphone = configuration.getCountryCode() + " " + auditor.getPhone();
			auditor.setPhone(newphone);
		}

		final Auditor saved2;
		//For new actors, generate the default system folders.
		if (auditor.getId() != 0) {
			Assert.isTrue(this.actorService.findByPrincipal().getId() == auditor.getId());
			saved2 = this.auditorRepository.save(auditor);
		} else {
			final Auditor saved = this.auditorRepository.save(auditor);
			saved.setFolders(this.folderService.generateDefaultFolders(saved));
			saved2 = this.auditorRepository.save(saved);
		}

		this.actorService.isSpam(saved2.getAddress());
		this.actorService.isSpam(saved2.getEmail());
		this.actorService.isSpam(saved2.getName());
		this.actorService.isSpam(saved2.getPhone());
		this.actorService.isSpam(saved2.getSurname());

		return saved2;
	}

	public void delete(final Auditor auditor) {
		Assert.notNull(auditor);

		//Assertion that the user deleting this auditor has the correct privilege.
		final Auditor validator = this.findOne(auditor.getId());
		Assert.isTrue(this.actorService.findByPrincipal().getId() == validator.getId());

		this.auditorRepository.delete(auditor);
	}
}
