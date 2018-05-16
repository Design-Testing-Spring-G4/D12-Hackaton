
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.TagValueRepository;
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


	//Simple CRUD methods

	public TagValue create(final int tagId) {
		final TagValue t = new TagValue();

		t.setTag(this.tagService.findOne(tagId));

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

		return this.valueRepository.save(t);
	}

	public void delete(final TagValue tv) {
		Assert.notNull(tv);

		this.valueRepository.delete(tv);
	}
}
