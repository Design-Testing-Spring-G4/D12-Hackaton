
package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.UserRepository;
import security.Authority;
import security.UserAccount;
import domain.Participation;
import domain.Reservation;
import domain.SocialIdentity;
import domain.User;

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


	//Simple CRUD Methods

	public User create() {
		final Authority a = new Authority();
		a.setAuthority(Authority.ADMIN);
		final UserAccount account = new UserAccount();
		account.setAuthorities(Arrays.asList(a));

		final User user = new User();
		user.setSuspicious(false);
		user.setSocialIdentities(new ArrayList<SocialIdentity>());
		user.setUserAccount(account);
		user.setFolders(this.folderService.generateDefaultFolders(user));
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

		//Assertion that the user modifying this user has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == user.getId());

		final User saved2;
		//Assertion that the final user modifying this final explorer has the final correct privilege.
		if (user.getId() != 0) {
			Assert.isTrue(this.actorService.findByPrincipal().getId() == user.getId());
			saved2 = this.userRepository.save(user);
		} else {
			final User saved = this.userRepository.save(user);
			saved.setFolders(this.folderService.generateDefaultFolders(saved));
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
}
