
package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ManagerRepository;
import security.Authority;
import security.UserAccount;
import domain.Manager;
import domain.Participation;
import domain.Resort;
import domain.SocialIdentity;

@Service
@Transactional
public class ManagerService {

	//Managed repository

	@Autowired
	private ManagerRepository	managerRepository;

	//Supporting services

	@Autowired
	private FolderService		folderService;

	@Autowired
	private ActorService		actorService;


	//Simple CRUD Methods

	public Manager create() {
		final Authority a = new Authority();
		a.setAuthority(Authority.ADMIN);
		final UserAccount account = new UserAccount();
		account.setAuthorities(Arrays.asList(a));

		final Manager manager = new Manager();
		manager.setSuspicious(false);
		manager.setSocialIdentities(new ArrayList<SocialIdentity>());
		manager.setUserAccount(account);
		manager.setFolders(this.folderService.generateDefaultFolders(manager));
		manager.setParticipations(new ArrayList<Participation>());
		manager.setResorts(new ArrayList<Resort>());

		return manager;
	}

	public Collection<Manager> findAll() {
		return this.managerRepository.findAll();
	}

	public Manager findOne(final int id) {
		Assert.notNull(id);

		return this.managerRepository.findOne(id);
	}

	public Manager save(final Manager manager) {
		Assert.notNull(manager);

		//Assertion that the user modifying this manager has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == manager.getId());

		final Manager saved2;
		//Assertion that the final user modifying this final explorer has the final correct privilege.
		if (manager.getId() != 0) {
			Assert.isTrue(this.actorService.findByPrincipal().getId() == manager.getId());
			saved2 = this.managerRepository.save(manager);
		} else {
			final Manager saved = this.managerRepository.save(manager);
			saved.setFolders(this.folderService.generateDefaultFolders(saved));
			saved2 = this.managerRepository.save(saved);
		}

		return saved2;
	}

	public void delete(final Manager manager) {
		Assert.notNull(manager);

		//Assertion that the user deleting this manager has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == manager.getId());

		this.managerRepository.delete(manager);
	}
}
