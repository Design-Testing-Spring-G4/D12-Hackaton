
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.LegalTextRepository;
import domain.LegalText;

@Service
@Transactional
public class LegalTextService {

	//Managed repository

	@Autowired
	private LegalTextRepository	legalRepository;


	//Simple CRUD methods

	public LegalText create() {
		final LegalText l = new LegalText();

		l.setRegistered(new Date());

		return l;
	}

	public LegalText findOne(final int id) {
		Assert.notNull(id);

		return this.legalRepository.findOne(id);
	}

	public Collection<LegalText> findAll() {
		return this.legalRepository.findAll();
	}

	public LegalText save(final LegalText l) {
		Assert.notNull(l);
		//Draft/final mode assertion is done via controller.

		l.setRegistered(new Date(System.currentTimeMillis() - 1));

		final LegalText saved = this.legalRepository.save(l);

		return saved;
	}

	public void delete(final LegalText l) {
		Assert.notNull(l);
		//A legal text cannot be deleted outside of draft mode.
		Assert.isTrue(!l.isFinalMode());

		this.legalRepository.delete(l);
	}

	//Other methods

	//A table with the number of times that each legal text has been referenced.
	//	public Collection<LegalTextTable> legalTextTable() {
	//		return this.legalRepository.legalTextTable();
	//	}
}
