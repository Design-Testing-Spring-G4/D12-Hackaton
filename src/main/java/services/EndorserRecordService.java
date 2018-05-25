
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EndorserRecordRepository;
import domain.Curriculum;
import domain.EndorserRecord;

@Service
@Transactional
public class EndorserRecordService {

	//Managed repository

	@Autowired
	private EndorserRecordRepository	endorserRecordRepository;

	//Supporting services

	@Autowired
	private CurriculumService			curriculumService;
	@Autowired
	private ActorService				actorService;


	//Simple CRUD methods

	public EndorserRecord create(final int curriculumId) {
		final EndorserRecord endorserRecord = new EndorserRecord();

		final Curriculum c = this.curriculumService.findOne(curriculumId);
		endorserRecord.setCurriculum(c);

		return endorserRecord;
	}

	public EndorserRecord findOne(final int id) {
		Assert.notNull(id);

		return this.endorserRecordRepository.findOne(id);
	}

	public Collection<EndorserRecord> findAll() {
		return this.endorserRecordRepository.findAll();
	}

	public EndorserRecord save(final EndorserRecord endorserRecord) {
		Assert.notNull(endorserRecord);

		//Assertion that the user modifying this endorser record has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == endorserRecord.getCurriculum().getInstructor().getId());

		final EndorserRecord saved = this.endorserRecordRepository.save(endorserRecord);

		this.actorService.isSpam(saved.getComments());
		this.actorService.isSpam(saved.getEmail());
		this.actorService.isSpam(saved.getName());
		this.actorService.isSpam(saved.getPhone());
		this.actorService.isSpam(saved.getProfile());

		return saved;
	}

	public void delete(final EndorserRecord endorserRecord) {
		Assert.notNull(endorserRecord);

		//Assertion that the user deleting this endorser record has the correct privilege.
		final EndorserRecord validator = this.findOne(endorserRecord.getId());
		Assert.isTrue(this.actorService.findByPrincipal().getId() == validator.getCurriculum().getInstructor().getId());

		this.endorserRecordRepository.delete(endorserRecord);
	}

}
