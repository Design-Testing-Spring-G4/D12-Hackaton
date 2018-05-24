
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

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


	//Simple CRUD methods

	public MiscellaneousRecord create(final int curriculumId) {

		final MiscellaneousRecord mr = new MiscellaneousRecord();

		final Curriculum c = this.curriculumService.findOne(curriculumId);
		mr.setCurriculum(c);

		return mr;
	}

	public MiscellaneousRecord findOne(final int id) {
		Assert.notNull(id);

		return this.miscellaneousRecordRepository.findOne(id);
	}

	public Collection<MiscellaneousRecord> findAll() {
		return this.miscellaneousRecordRepository.findAll();
	}

	public MiscellaneousRecord save(final MiscellaneousRecord mr) {
		Assert.notNull(mr);

		//Assertion that the user modifying this miscellaneous record has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == mr.getCurriculum().getInstructor().getId());

		final MiscellaneousRecord saved = this.miscellaneousRecordRepository.save(mr);

		this.actorService.isSpam(saved.getComments());
		this.actorService.isSpam(saved.getLink());
		this.actorService.isSpam(saved.getTitle());

		return saved;
	}

	public void delete(final MiscellaneousRecord mr) {
		Assert.notNull(mr);

		//Assertion that the user deleting this miscellaneous record has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == mr.getCurriculum().getInstructor().getId());

		this.miscellaneousRecordRepository.delete(mr);
	}

}
