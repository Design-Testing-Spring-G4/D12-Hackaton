
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

import repositories.UserRepository;
import security.Authority;
import security.UserAccount;
import domain.Folder;
import domain.Participation;
import domain.Reservation;
import domain.SocialIdentity;
import domain.User;
import forms.ActorRegisterForm;

@Service
@Transactional
public class UserService {

	//Managed repository

	@Autowired
	private UserRepository	userRepository;

	//Supporting services

	@Autowired
	private FolderService	folderService;

	@Autowired
	private ActorService	actorService;

	@Autowired
	private Validator		validator;


	//Simple CRUD Methods

	public User create() {
		final Authority a = new Authority();
		a.setAuthority(Authority.USER);
		final UserAccount account = new UserAccount();
		account.setAuthorities(Arrays.asList(a));

		final User user = new User();
		user.setSuspicious(false);
		user.setSocialIdentities(new ArrayList<SocialIdentity>());
		user.setUserAccount(account);
		user.setFolders(new ArrayList<Folder>());
		user.setParticipations(new ArrayList<Participation>());
		user.setReservations(new ArrayList<Reservation>());

		return user;
	}

	public Collection<User> findAll() {
		return this.userRepository.findAll();
	}

	public User findOne(final int id) {
		Assert.notNull(id);

		return this.userRepository.findOne(id);
	}

	public User save(final User user) {
		Assert.notNull(user);

		final User saved2;
		//For new actors, generate the default system folders.
		if (user.getId() != 0) {
			Assert.isTrue(this.actorService.findByPrincipal().getId() == user.getId());
			saved2 = this.userRepository.save(user);
		} else {
			final User saved = this.userRepository.save(user);
			final Collection<Folder> cf = this.folderService.generateDefaultFolders(saved);
			saved.setFolders(cf);
			saved2 = this.userRepository.save(saved);
		}

		return saved2;
	}

	public void delete(final User user) {
		Assert.notNull(user);

		//Assertion that the user deleting this user has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == user.getId());

		this.userRepository.delete(user);
	}

	//Other methods

	public User reconstruct(final ActorRegisterForm arf, final BindingResult binding) {
		User user;
		Assert.isTrue(arf.isAcceptedTerms());
		Assert.isTrue(arf.getPassword().equals(arf.getRepeatPassword()));

		user = this.create();
		user.getUserAccount().setUsername(arf.getUsername());
		user.getUserAccount().setPassword(arf.getPassword());
		user.setName(arf.getName());
		user.setSurname(arf.getSurname());
		user.setEmail(arf.getEmail());
		user.setPhone(arf.getPhone());
		user.setAddress(arf.getAddress());

		this.validator.validate(user, binding);

		return user;
	}

	public Double ratioSuspiciousUsers() {
		return this.userRepository.ratioSuspiciousUsers();
	}
}
