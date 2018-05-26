
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.PersonalRecordRepository;
import domain.Configuration;
import domain.Curriculum;
import domain.PersonalRecord;

@Service
@Transactional
public class PersonalRecordService {

	//Managed service

	@Autowired
	private PersonalRecordRepository	personalRecordRepository;

	//Supporting services

	@Autowired
	private CurriculumService			curriculumService;

	@Autowired
	private ActorService				actorService;

	@Autowired
	private Validator					validator;

	@Autowired
	private ConfigurationService		configurationService;


	//Simple CRUD methods

	public PersonalRecord create(final int curriculumId) {
		final PersonalRecord personalRecord = new PersonalRecord();

		final Curriculum c = this.curriculumService.findOne(curriculumId);
		personalRecord.setCurriculum(c);
		personalRecord.setEmail("");
		personalRecord.setName("");
		personalRecord.setPhone("");
		personalRecord.setPhoto("");
		personalRecord.setProfile("");

		return personalRecord;
	}

	public PersonalRecord findOne(final int id) {
		Assert.notNull(id);

		return this.personalRecordRepository.findOne(id);
	}

	public Collection<PersonalRecord> findAll() {
		return this.personalRecordRepository.findAll();
	}

	public PersonalRecord save(final PersonalRecord personalRecord) {
		Assert.notNull(personalRecord);

		if (!personalRecord.getPhone().startsWith("+")) {
			final Configuration configuration = this.configurationService.findAll().iterator().next();
			final String newphone = configuration.getCountryCode() + " " + personalRecord.getPhone();
			personalRecord.setPhone(newphone);
		}

		//Assertion that the user modifying this personal record has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == personalRecord.getCurriculum().getInstructor().getId());

		final PersonalRecord saved = this.personalRecordRepository.save(personalRecord);

		this.actorService.isSpam(saved.getEmail());
		this.actorService.isSpam(saved.getName());
		this.actorService.isSpam(saved.getPhone());
		this.actorService.isSpam(saved.getPhoto());
		this.actorService.isSpam(saved.getProfile());

		return saved;
	}

	public void delete(final PersonalRecord personalRecord) {
		Assert.notNull(personalRecord);

		//Assertion that the user deleting this personal record has the correct privilege.
		final PersonalRecord validator = this.findOne(personalRecord.getId());
		Assert.isTrue(this.actorService.findByPrincipal().getId() == validator.getCurriculum().getInstructor().getId());

		this.personalRecordRepository.delete(personalRecord);
	}

	//Other methods

	public PersonalRecord reconstruct(final PersonalRecord personalRecord, final BindingResult binding) {
		PersonalRecord result;

		if (personalRecord.getId() == 0) {
			result = this.create(personalRecord.getCurriculum().getId());
			result.setEmail(personalRecord.getEmail());
			result.setName(personalRecord.getName());
			result.setPhone(personalRecord.getPhone());
			result.setPhoto(personalRecord.getPhoto());
			result.setProfile(personalRecord.getProfile());
		} else {
			result = this.findOne(personalRecord.getId());
			result.setEmail(personalRecord.getEmail());
			result.setName(personalRecord.getName());
			result.setPhone(personalRecord.getPhone());
			result.setPhoto(personalRecord.getPhoto());
			result.setProfile(personalRecord.getProfile());
		}

		this.validator.validate(result, binding);

		return result;
	}
}
