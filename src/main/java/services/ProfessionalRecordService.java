
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ProfessionalRecordRepository;
import domain.Curriculum;
import domain.ProfessionalRecord;

@Service
@Transactional
public class ProfessionalRecordService {

	//Managed repository

	@Autowired
	private ProfessionalRecordRepository	professionalRecordRepository;

	//Supporting services

	@Autowired
	private CurriculumService				curriculumService;

	@Autowired
	private ActorService					actorService;


	//Simple CRUD methods

	public ProfessionalRecord create(final int curriculumId) {
		final ProfessionalRecord professionalRecord = new ProfessionalRecord();

		final Curriculum c = this.curriculumService.findOne(curriculumId);
		professionalRecord.setCurriculum(c);

		return professionalRecord;
	}

	public ProfessionalRecord findOne(final int id) {
		Assert.notNull(id);

		return this.professionalRecordRepository.findOne(id);
	}

	public Collection<ProfessionalRecord> findAll() {
		return this.professionalRecordRepository.findAll();
	}

	public ProfessionalRecord save(final ProfessionalRecord professionalRecord) {
		Assert.notNull(professionalRecord);

		//Assertion that the user modifying this professional record has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == professionalRecord.getCurriculum().getInstructor().getId());

		//Business rule: periodStart must be earlier than periodEnd.
		Assert.isTrue(professionalRecord.getPeriodEnd().after(professionalRecord.getPeriodStart()));

		final ProfessionalRecord saved = this.professionalRecordRepository.save(professionalRecord);

		this.actorService.isSpam(saved.getAttachment());
		this.actorService.isSpam(saved.getComments());
		this.actorService.isSpam(saved.getCompany());
		this.actorService.isSpam(saved.getRole());

		return saved;
	}

	public void delete(final ProfessionalRecord professionalRecord) {
		Assert.notNull(professionalRecord);

		//Assertion that the user deleting this professional record has the correct privilege.
		final ProfessionalRecord validator = this.findOne(professionalRecord.getId());
		Assert.isTrue(this.actorService.findByPrincipal().getId() == validator.getCurriculum().getInstructor().getId());

		this.professionalRecordRepository.delete(professionalRecord);
	}
}
