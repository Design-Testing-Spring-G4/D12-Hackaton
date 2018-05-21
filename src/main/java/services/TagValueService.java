
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.TagValueRepository;
import domain.Resort;
import domain.TagValue;

@Service
@Transactional
public class TagValueService {

	//ManagedRepository

	@Autowired
	private TagValueRepository	valueRepository;

	//Supporting services

	@Autowired
	private TagService			tagService;

	@Autowired
	private ResortService		resortService;


	//Simple CRUD methods

	public TagValue create(final int tagId) {
		final TagValue t = new TagValue();

		t.setTag(this.tagService.findOne(tagId));
		t.setResorts(new ArrayList<Resort>());

		return t;
	}

	public TagValue findOne(final int id) {
		Assert.notNull(id);

		return this.valueRepository.findOne(id);
	}

	public Collection<TagValue> findAll() {
		return this.valueRepository.findAll();
	}

	public TagValue save(final TagValue t) {
		Assert.notNull(t);

		//Business rule: a tag can only be modified if no trip is using it.
		Assert.isTrue(!t.getResorts().isEmpty());

		return this.valueRepository.save(t);
	}

	public void delete(final TagValue tv) {
		Assert.notNull(tv);

		for (final Resort r : tv.getResorts()) {
			final Collection<TagValue> tags = r.getTags();
			tags.remove(tv);
			r.setTags(tags);
			this.resortService.save(r);
		}

		this.valueRepository.delete(tv);
	}
}
