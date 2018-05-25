
package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.Authority;
import security.UserAccount;
import domain.Administrator;
import domain.Configuration;
import domain.Folder;
import domain.Participation;
import domain.SocialIdentity;

@Service
@Transactional
public class AdministratorService {

	//Managed repository

	@Autowired
	private AdministratorRepository	administratorRepository;

	//Supporting services

	@Autowired
	private FolderService			folderService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private ConfigurationService	configurationService;


	//Simple CRUD Methods

	public Administrator create() {
		final Authority a = new Authority();
		a.setAuthority(Authority.ADMIN);
		final UserAccount account = new UserAccount();
		account.setAuthorities(Arrays.asList(a));

		final Administrator administrator = new Administrator();
		administrator.setSuspicious(false);
		administrator.setSocialIdentities(new ArrayList<SocialIdentity>());
		administrator.setUserAccount(account);
		administrator.setFolders(new ArrayList<Folder>());
		administrator.setParticipations(new ArrayList<Participation>());

		return administrator;
	}

	public Collection<Administrator> findAll() {
		return this.administratorRepository.findAll();
	}

	public Administrator findOne(final int id) {
		Assert.notNull(id);

		return this.administratorRepository.findOne(id);
	}

	public Administrator save(final Administrator administrator) {
		Assert.notNull(administrator);

		if (!administrator.getPhone().startsWith("+")) {
			final Configuration configuration = this.configurationService.findAll().iterator().next();
			final String newphone = configuration.getCountryCode() + " " + administrator.getPhone();
			administrator.setPhone(newphone);
		}

		final Administrator saved2;
		//For new actors, generate the default system folders.
		if (administrator.getId() != 0) {
			Assert.isTrue(this.actorService.findByPrincipal().getId() == administrator.getId());
			saved2 = this.administratorRepository.save(administrator);
		} else {
			final Administrator saved = this.administratorRepository.save(administrator);
			saved.setFolders(this.folderService.generateDefaultFolders(saved));
			saved2 = this.administratorRepository.save(saved);
		}

		this.actorService.isSpam(saved2.getAddress());
		this.actorService.isSpam(saved2.getEmail());
		this.actorService.isSpam(saved2.getName());
		this.actorService.isSpam(saved2.getPhone());
		this.actorService.isSpam(saved2.getSurname());

		return saved2;
	}

	public void delete(final Administrator administrator) {
		Assert.notNull(administrator);

		//Assertion that the user deleting this administrator has the correct privilege.
		final Administrator validator = this.findOne(administrator.getId());
		Assert.isTrue(this.actorService.findByPrincipal().getId() == validator.getId());

		this.administratorRepository.delete(administrator);
	}
}
