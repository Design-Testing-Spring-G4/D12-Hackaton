
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
import domain.Configuration;
import domain.Folder;
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

	@Autowired
	private ConfigurationService	configurationService;


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
		instructor.setFolders(new ArrayList<Folder>());
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

		if (!instructor.getPhone().startsWith("+")) {
			final Configuration configuration = this.configurationService.findAll().iterator().next();
			final String newphone = configuration.getCountryCode() + " " + instructor.getPhone();
			instructor.setPhone(newphone);
		}

		final Instructor saved2;
		//For new actors, generate the default system folders.
		if (instructor.getId() != 0) {
			Assert.isTrue(this.actorService.findByPrincipal().getId() == instructor.getId());
			saved2 = this.instructorRepository.save(instructor);
		} else {
			final Instructor saved = this.instructorRepository.save(instructor);
			saved.setFolders(this.folderService.generateDefaultFolders(saved));
			saved2 = this.instructorRepository.save(saved);
		}

		this.actorService.isSpam(saved2.getAddress());
		this.actorService.isSpam(saved2.getEmail());
		this.actorService.isSpam(saved2.getName());
		this.actorService.isSpam(saved2.getPhone());
		this.actorService.isSpam(saved2.getSurname());

		return saved2;
	}

	//Save for internal operations such as cross-authorized modifications.
	public Instructor saveInternal(final Instructor instructor) {
		Assert.notNull(instructor);
		final Instructor saved = this.instructorRepository.save(instructor);
		return saved;
	}

	public void delete(final Instructor instructor) {
		Assert.notNull(instructor);

		//Assertion that the user deleting this instructor has the correct privilege.
		final Instructor validator = this.findOne(instructor.getId());
		Assert.isTrue(this.actorService.findByPrincipal().getId() == validator.getId());

		this.instructorRepository.delete(instructor);
	}

	//Other methods

	public Double ratioInstructorsWithCurriculum() {
		return this.instructorRepository.ratioInstructorsWithCurriculum();
	}

	public Double ratioInstructorsEndorsed() {
		return this.instructorRepository.ratioInstructorsEndorsed();
	}

	public Double ratioSuspiciousInstructors() {
		return this.instructorRepository.ratioSuspiciousInstructors();
	}

	public Collection<Instructor> instructorsWithResort(final Resort resort) {
		return this.instructorRepository.instructorsWithResort(resort);
	}
}
