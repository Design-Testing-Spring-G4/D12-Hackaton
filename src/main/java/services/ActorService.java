
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.LoginService;
import security.UserAccount;
import security.UserAccountRepository;
import domain.Actor;

@Service
@Transactional
public class ActorService {

	// Managed repository

	@Autowired
	private ActorRepository			actorRepository;

	@Autowired
	private UserAccountRepository	userAccountRepository;

	//Supporting services

	@Autowired
	private ConfigurationService	configurationService;


	//Simple CRUD Methods

	public Collection<Actor> findAll() {
		return this.actorRepository.findAll();
	}

	public Actor findOne(final int id) {
		Assert.notNull(id);

		return this.actorRepository.findOne(id);
	}

	public Actor save(final Actor actor) {
		Assert.notNull(actor);

		final Actor saved = this.actorRepository.save(actor);

		return saved;
	}

	public void delete(final Actor actor) {
		Assert.notNull(actor);

		Assert.isTrue(this.findByPrincipal().getId() == actor.getId());

		this.actorRepository.delete(actor);
	}

	// Other business methods

	public Actor findByPrincipal() {
		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		final Actor a = this.actorRepository.findByUserAccountId(userAccount.getId());
		return a;
	}

	public void hashPassword(final Actor a) {
		final String oldPs = a.getUserAccount().getPassword();
		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		final String hash = encoder.encodePassword(oldPs, null);
		final UserAccount old = a.getUserAccount();
		old.setPassword(hash);
		final UserAccount newOne = this.userAccountRepository.save(old);
		a.setUserAccount(newOne);
	}

	public boolean isSpam(final String s) {
		final String[] words = this.configurationService.getAllWords();
		boolean isSpam = false;

		for (final String e : words)
			if (s.contains(e))
				isSpam = true;

		return isSpam;
	}
}
