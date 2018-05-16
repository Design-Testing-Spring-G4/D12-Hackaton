
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

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


	//Simple CRUD methods

	public EducationRecord create(final int curriculumId) {
		final EducationRecord er = new EducationRecord();

		final Curriculum c = this.curriculumService.findOne(curriculumId);
		er.setCurriculum(c);

		return er;
	}

	public EducationRecord findOne(final int id) {
		Assert.notNull(id);

		return this.educationRecordRepository.findOne(id);
	}

	public Collection<EducationRecord> findAll() {
		return this.educationRecordRepository.findAll();
	}

	public EducationRecord save(final EducationRecord er) {
		Assert.notNull(er);

		//Assertion that the user modifying this education record has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == er.getCurriculum().getInstructor().getId());

		//Business rule: periodStart must be earlier than periodEnd.
		Assert.isTrue(er.getPeriodEnd().after(er.getPeriodStart()));

		final EducationRecord saved = this.educationRecordRepository.save(er);

		this.actorService.isSpam(saved.getAttachment());
		this.actorService.isSpam(saved.getComments());
		this.actorService.isSpam(saved.getDiploma());
		this.actorService.isSpam(saved.getInstitution());

		return saved;
	}

	public void delete(final EducationRecord er) {
		Assert.notNull(er);

		//Assertion that the user deleting this education record has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == er.getCurriculum().getInstructor().getId());

		this.educationRecordRepository.delete(er);
	}

}
