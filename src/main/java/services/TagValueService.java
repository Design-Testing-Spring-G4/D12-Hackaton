
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

	@Autowired
	private ActorService		actorService;


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

	public TagValue save(final TagValue tagValue) {
		Assert.notNull(tagValue);

		//Assertion that the user modifying this tag has the correct privilege.
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Assert.isTrue(authentication.getAuthorities().toArray()[0].toString().equals("ADMIN"));

		//Business rule: a tag can only be modified if no resort is using it.
		Assert.isTrue(!tagValue.getResorts().isEmpty());

		final TagValue saved = this.valueRepository.save(tagValue);

		this.actorService.isSpam(saved.getValue());

		return saved;
	}

	//Save for internal operations such as cross-authorized requests.
	public TagValue saveInternal(final TagValue tagValue) {
		Assert.notNull(tagValue);
		final TagValue saved = this.valueRepository.save(tagValue);
		return saved;
	}

	public void delete(final TagValue tagValue) {
		Assert.notNull(tagValue);

		//Assertion that the user modifying this tag has the correct privilege.
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Assert.isTrue(authentication.getAuthorities().toArray()[0].toString().equals("ADMIN"));

		for (final Resort r : tagValue.getResorts()) {
			final Collection<TagValue> tags = r.getTags();
			tags.remove(tagValue);
			r.setTags(tags);
			this.resortService.save(r);
		}

		this.valueRepository.delete(tagValue);
	}
}
