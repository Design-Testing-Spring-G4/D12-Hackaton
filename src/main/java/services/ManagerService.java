
package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ManagerRepository;
import security.Authority;
import security.UserAccount;
import domain.Folder;
import domain.Manager;
import domain.Participation;
import domain.Resort;
import domain.SocialIdentity;
import forms.ActorRegisterForm;

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

	@Autowired
	private Validator			validator;


	//Simple CRUD Methods

	public Manager create() {
		final Authority a = new Authority();
		a.setAuthority(Authority.MANAGER);
		final UserAccount account = new UserAccount();
		account.setAuthorities(Arrays.asList(a));

		final Manager manager = new Manager();
		manager.setSuspicious(false);
		manager.setSocialIdentities(new ArrayList<SocialIdentity>());
		manager.setUserAccount(account);
		manager.setFolders(new ArrayList<Folder>());
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

		final Manager saved2;
		//For new actors, generate the default system folders.
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

	//Other methods

	public Manager reconstruct(final ActorRegisterForm arf, final BindingResult binding) {
		Manager manager;
		Assert.isTrue(arf.isAcceptedTerms());
		Assert.isTrue(arf.getPassword().equals(arf.getRepeatPassword()));

		manager = this.create();
		manager.getUserAccount().setUsername(arf.getUsername());
		manager.getUserAccount().setPassword(arf.getPassword());
		manager.setName(arf.getName());
		manager.setSurname(arf.getSurname());
		manager.setEmail(arf.getEmail());
		manager.setPhone(arf.getPhone());
		manager.setAddress(arf.getAddress());

		this.validator.validate(manager, binding);

		return manager;
	}

	public Double[] avgMinMaxStddevResortsPerManager() {
		return this.managerRepository.avgMinMaxStddevResortsPerManager();
	}

	public Double ratioSuspiciousManagers() {
		return this.managerRepository.ratioSuspiciousManagers();
	}
}
