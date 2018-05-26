
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.MiscellaneousRecordRepository;
import domain.Curriculum;
import domain.MiscellaneousRecord;

@Service
@Transactional
public class MiscellaneousRecordService {

	//Managed repository
	@Autowired
	private MiscellaneousRecordRepository	miscellaneousRecordRepository;

	//Supporting services

	@Autowired
	private CurriculumService				curriculumService;

	@Autowired
	private ActorService					actorService;

	@Autowired
	private Validator						validator;


	//Simple CRUD methods

	public MiscellaneousRecord create(final int curriculumId) {

		final MiscellaneousRecord miscellaneousRecord = new MiscellaneousRecord();

		final Curriculum c = this.curriculumService.findOne(curriculumId);
		miscellaneousRecord.setCurriculum(c);

		return miscellaneousRecord;
	}

	public MiscellaneousRecord findOne(final int id) {
		Assert.notNull(id);

		return this.miscellaneousRecordRepository.findOne(id);
	}

	public Collection<MiscellaneousRecord> findAll() {
		return this.miscellaneousRecordRepository.findAll();
	}

	public MiscellaneousRecord save(final MiscellaneousRecord miscellaneousRecord) {
		Assert.notNull(miscellaneousRecord);

		//Assertion that the user modifying this miscellaneous record has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == miscellaneousRecord.getCurriculum().getInstructor().getId());

		final MiscellaneousRecord saved = this.miscellaneousRecordRepository.save(miscellaneousRecord);

		this.actorService.isSpam(saved.getComments());
		this.actorService.isSpam(saved.getLink());
		this.actorService.isSpam(saved.getTitle());

		return saved;
	}

	public void delete(final MiscellaneousRecord miscellaneousRecord) {
		Assert.notNull(miscellaneousRecord);

		//Assertion that the user deleting this miscellaneous record has the correct privilege.
		final MiscellaneousRecord validator = this.findOne(miscellaneousRecord.getId());
		Assert.isTrue(this.actorService.findByPrincipal().getId() == validator.getCurriculum().getInstructor().getId());

		this.miscellaneousRecordRepository.delete(miscellaneousRecord);
	}

	//Other methods

	public MiscellaneousRecord reconstruct(final MiscellaneousRecord miscellaneousRecord, final BindingResult binding) {
		MiscellaneousRecord result;

		if (miscellaneousRecord.getId() == 0) {
			result = this.create(miscellaneousRecord.getCurriculum().getId());
			result.setComments(miscellaneousRecord.getComments());
			result.setLink(miscellaneousRecord.getLink());
			result.setTitle(miscellaneousRecord.getTitle());
		} else {
			result = this.findOne(miscellaneousRecord.getId());
			result.setComments(miscellaneousRecord.getComments());
			result.setLink(miscellaneousRecord.getLink());
			result.setTitle(miscellaneousRecord.getTitle());
		}

		this.validator.validate(result, binding);

		return result;
	}
}
