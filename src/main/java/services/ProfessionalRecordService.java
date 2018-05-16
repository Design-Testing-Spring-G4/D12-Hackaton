
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

	// Managed repository

	@Autowired
	private ProfessionalRecordRepository	professionalRecordRepository;

	// Supporting services

	@Autowired
	private CurriculumService				curriculumService;

	@Autowired
	private ActorService					actorService;


	// Simple CRUD methods

	public ProfessionalRecord create(final int curriculumId) {
		final ProfessionalRecord pr = new ProfessionalRecord();

		final Curriculum c = this.curriculumService.findOne(curriculumId);
		pr.setCurriculum(c);

		return pr;
	}

	public ProfessionalRecord findOne(final int id) {
		Assert.notNull(id);

		return this.professionalRecordRepository.findOne(id);
	}

	public Collection<ProfessionalRecord> findAll() {
		return this.professionalRecordRepository.findAll();
	}

	public ProfessionalRecord save(final ProfessionalRecord pr) {
		Assert.notNull(pr);

		//Assertion that the user modifying this professional record has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == pr.getCurriculum().getInstructor().getId());

		//Business rule: periodStart must be earlier than periodEnd.
		Assert.isTrue(pr.getPeriodEnd().after(pr.getPeriodStart()));

		final ProfessionalRecord saved = this.professionalRecordRepository.save(pr);

		this.actorService.isSpam(saved.getAttachment());
		this.actorService.isSpam(saved.getComments());
		this.actorService.isSpam(saved.getCompany());
		this.actorService.isSpam(saved.getRole());

		return saved;
	}

	public void delete(final ProfessionalRecord pr) {
		Assert.notNull(pr);

		//Assertion that the user deleting this professional record has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == pr.getCurriculum().getInstructor().getId());

		this.professionalRecordRepository.delete(pr);
	}
}
