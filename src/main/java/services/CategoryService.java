
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.CategoryRepository;
import domain.Category;
import domain.Resort;

@Service
@Transactional
public class CategoryService {

	//Managed repository

	@Autowired
	private CategoryRepository	categoryRepository;

	//Supporting services

	@Autowired
	private ActorService		actorService;

	@Autowired
	private Validator			validator;


	//Simple CRUD methods

	public Category create() {
		final Category category = new Category();

		category.setChildren(new ArrayList<Category>());
		category.setResorts(new ArrayList<Resort>());

		return category;
	}

	public Category findOne(final int id) {
		Assert.notNull(id);

		return this.categoryRepository.findOne(id);
	}

	public Collection<Category> findAll() {
		return this.categoryRepository.findAll();
	}

	public Category save(final Category category) {
		Assert.notNull(category);

		//Business rule: since the only category without parent must be the root, all others must have a parent ID.
		Assert.notNull(category.getParent());

		//Business rule: two categories with the same parent cannot have the same name.
		if (category.getId() == 0)
			for (final Category a : this.findAll())
				Assert.isTrue(!(a.getParent() == (category.getParent()) && a.getName().equals(category.getName())));

		final Category saved = this.categoryRepository.save(category);

		if (category.getId() == 0) {
			Category parent = this.categoryRepository.findOne(category.getParent().getId());
			parent.getChildren().add(saved);
			parent = this.categoryRepository.save(parent);
		}

		this.actorService.isSpam(saved.getName());

		return saved;
	}

	//Save for internal operations such as cross-authorized requests.
	public Category saveInternal(final Category category) {
		Assert.notNull(category);
		final Category saved = this.categoryRepository.save(category);
		return saved;
	}

	public void delete(final Category category) {
		Assert.notNull(category);

		//The root category should not be deleted.
		Assert.isTrue(!(category.getParent() == null));

		final Category def = this.categoryRepository.defaultCategory();
		def.getResorts().addAll(category.getResorts());
		this.categoryRepository.save(def);

		final Category parent = category.getParent();
		parent.getChildren().remove(category);
		this.categoryRepository.save(parent);

		this.categoryRepository.delete(category);
	}

	//Other methods

	public Category reconstruct(final Category category, final BindingResult binding) {
		Category result;

		if (category.getId() == 0) {
			result = this.create();
			result.setName(category.getName());
			result.setParent(category.getParent());
		} else {
			result = this.findOne(category.getId());
			result.setName(category.getName());
			result.setParent(category.getParent());
		}

		this.validator.validate(result, binding);

		return result;
	}
}
