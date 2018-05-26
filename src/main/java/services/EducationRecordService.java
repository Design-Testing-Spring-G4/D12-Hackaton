
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.EducationRecordRepository;
import domain.Curriculum;
import domain.EducationRecord;

@Service
@Transactional
public class EducationRecordService {

	//Managed repository

	@Autowired
	private EducationRecordRepository	educationRecordRepository;

	//Supporting services

	@Autowired
	private CurriculumService			curriculumService;

	@Autowired
	private ActorService				actorService;

	@Autowired
	private Validator					validator;


	//Simple CRUD methods

	public EducationRecord create(final int curriculumId) {
		final EducationRecord educationRecord = new EducationRecord();

		final Curriculum c = this.curriculumService.findOne(curriculumId);
		educationRecord.setCurriculum(c);

		return educationRecord;
	}

	public EducationRecord findOne(final int id) {
		Assert.notNull(id);

		return this.educationRecordRepository.findOne(id);
	}

	public Collection<EducationRecord> findAll() {
		return this.educationRecordRepository.findAll();
	}

	public EducationRecord save(final EducationRecord educationRecord) {
		Assert.notNull(educationRecord);

		//Assertion that the user modifying this education record has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == educationRecord.getCurriculum().getInstructor().getId());

		//Business rule: periodStart must be earlier than periodEnd.
		Assert.isTrue(educationRecord.getPeriodEnd().after(educationRecord.getPeriodStart()));

		final EducationRecord saved = this.educationRecordRepository.save(educationRecord);

		this.actorService.isSpam(saved.getAttachment());
		this.actorService.isSpam(saved.getComments());
		this.actorService.isSpam(saved.getDiploma());
		this.actorService.isSpam(saved.getInstitution());

		return saved;
	}

	public void delete(final EducationRecord educationRecord) {
		Assert.notNull(educationRecord);

		//Assertion that the user deleting this education record has the correct privilege.
		final EducationRecord validator = this.findOne(educationRecord.getId());
		Assert.isTrue(this.actorService.findByPrincipal().getId() == validator.getCurriculum().getInstructor().getId());

		this.educationRecordRepository.delete(educationRecord);
	}

	//Other methods

	public EducationRecord reconstruct(final EducationRecord educationRecord, final BindingResult binding) {
		EducationRecord result;

		if (educationRecord.getId() == 0) {
			result = this.create(educationRecord.getCurriculum().getId());
			result.setComments(educationRecord.getComments());
			result.setAttachment(educationRecord.getAttachment());
			result.setDiploma(educationRecord.getDiploma());
			result.setInstitution(educationRecord.getInstitution());
			result.setPeriodEnd(educationRecord.getPeriodEnd());
			result.setPeriodStart(educationRecord.getPeriodStart());
		} else {
			result = this.findOne(educationRecord.getId());
			result.setComments(educationRecord.getComments());
			result.setAttachment(educationRecord.getAttachment());
			result.setDiploma(educationRecord.getDiploma());
			result.setInstitution(educationRecord.getInstitution());
			result.setPeriodEnd(educationRecord.getPeriodEnd());
			result.setPeriodStart(educationRecord.getPeriodStart());
		}

		this.validator.validate(result, binding);

		return result;
	}
}
