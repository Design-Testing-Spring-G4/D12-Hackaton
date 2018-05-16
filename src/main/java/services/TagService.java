
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.TagRepository;
import domain.Resort;
import domain.Tag;
import domain.TagValue;

@Service
@Transactional
public class TagService {

	//Managed repository

	@Autowired
	private TagRepository	tagRepository;

	//Supporting services

	@Autowired
	private TagValueService	tagValueService;

	@Autowired
	private ResortService	resortService;


	//Simple CRUD methods

	public Tag create() {
		final Tag t = new Tag();

		t.setTagValue(new ArrayList<TagValue>());
		t.setResorts(new ArrayList<Resort>());

		return t;
	}

	public Tag findOne(final int id) {
		Assert.notNull(id);

		return this.tagRepository.findOne(id);
	}

	public Collection<Tag> findAll() {
		return this.tagRepository.findAll();
	}

	public Tag save(final Tag t) {
		Assert.notNull(t);

		//Business rule: a tag can only be modified if no trip is using it.
		Assert.isTrue(!t.getResorts().isEmpty());

		return this.tagRepository.save(t);
	}

	public void delete(final Tag t) {
		Assert.notNull(t);

		for (final TagValue tv : t.getTagValue())
			this.tagValueService.delete(tv);

		for (final Resort r : t.getResorts()) {
			final Collection<Tag> tags = r.getTags();
			tags.remove(t);
			r.setTags(tags);
			this.resortService.save(r);
		}

		this.tagRepository.delete(t);
	}

}
