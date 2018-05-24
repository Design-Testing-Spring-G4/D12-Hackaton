
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.TagRepository;
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
	private ActorService	actorService;


	//Simple CRUD methods

	public Tag create() {
		final Tag t = new Tag();

		t.setTagValues(new ArrayList<TagValue>());

		return t;
	}

	public Tag findOne(final int id) {
		Assert.notNull(id);

		return this.tagRepository.findOne(id);
	}

	public Collection<Tag> findAll() {
		return this.tagRepository.findAll();
	}

	public Tag save(final Tag tag) {
		Assert.notNull(tag);

		//Assertion that the user modifying this tag has the correct privilege.
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Assert.isTrue(authentication.getAuthorities().toArray()[0].toString().equals("ADMIN"));

		final Tag saved = this.tagRepository.save(tag);

		this.actorService.isSpam(saved.getName());

		return saved;
	}

	public void delete(final Tag tag) {
		Assert.notNull(tag);

		//Assertion that the user modifying this tag has the correct privilege.
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Assert.isTrue(authentication.getAuthorities().toArray()[0].toString().equals("ADMIN"));

		for (final TagValue tv : tag.getTagValues())
			this.tagValueService.delete(tv);

		this.tagRepository.delete(tag);
	}

}
