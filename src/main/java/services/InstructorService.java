
package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.InstructorRepository;
import security.Authority;
import security.UserAccount;
import domain.Instructor;
import domain.Participation;
import domain.Resort;
import domain.SocialIdentity;

@Service
@Transactional
public class InstructorService {

	//Managed repository

	@Autowired
	private InstructorRepository	instructorRepository;

	//Supporting services

	@Autowired
	private FolderService			folderService;

	@Autowired
	private ActorService			actorService;


	//Simple CRUD Methods

	public Instructor create() {
		final Authority a = new Authority();
		a.setAuthority(Authority.INSTRUCTOR);
		final UserAccount account = new UserAccount();
		account.setAuthorities(Arrays.asList(a));

		final Instructor instructor = new Instructor();
		instructor.setSuspicious(false);
		instructor.setSocialIdentities(new ArrayList<SocialIdentity>());
		instructor.setUserAccount(account);
		instructor.setFolders(this.folderService.generateDefaultFolders(instructor));
		instructor.setParticipations(new ArrayList<Participation>());
		instructor.setResorts(new ArrayList<Resort>());

		return instructor;
	}

	public Collection<Instructor> findAll() {
		return this.instructorRepository.findAll();
	}

	public Instructor findOne(final int id) {
		Assert.notNull(id);

		return this.instructorRepository.findOne(id);
	}

	public Instructor save(final Instructor instructor) {
		Assert.notNull(instructor);

		//Assertion that the user modifying this instructor has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == instructor.getId());

		final Instructor saved2;
		//Assertion that the final user modifying this final explorer has the final correct privilege.
		if (instructor.getId() != 0) {
			Assert.isTrue(this.actorService.findByPrincipal().getId() == instructor.getId());
			saved2 = this.instructorRepository.save(instructor);
		} else {
			final Instructor saved = this.instructorRepository.save(instructor);
			saved.setFolders(this.folderService.generateDefaultFolders(saved));
			saved2 = this.instructorRepository.save(saved);
		}

		return saved2;
	}

	public void delete(final Instructor instructor) {
		Assert.notNull(instructor);

		//Assertion that the user deleting this instructor has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == instructor.getId());

		this.instructorRepository.delete(instructor);
	}
}
