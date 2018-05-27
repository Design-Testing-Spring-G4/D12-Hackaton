
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.LegalTextRepository;
import domain.LegalText;
import domain.LegalTextTable;

@Service
@Transactional
public class LegalTextService {

	//Managed repository

	@Autowired
	private LegalTextRepository	legalRepository;

	//Supporting services

	@Autowired
	private ActorService		actorService;


	//Simple CRUD methods

	public LegalText create() {
		final LegalText legalText = new LegalText();

		legalText.setRegistered(new Date());

		return legalText;
	}

	public LegalText findOne(final int id) {
		Assert.notNull(id);

		return this.legalRepository.findOne(id);
	}

	public Collection<LegalText> findAll() {
		return this.legalRepository.findAll();
	}

	public LegalText save(final LegalText legalText) {
		Assert.notNull(legalText);
		//Draft/final mode assertion is done via controller.

		//Assertion that the user modifying this text has the correct privilege.
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Assert.isTrue(authentication.getAuthorities().toArray()[0].toString().equals("ADMIN"));

		legalText.setRegistered(new Date(System.currentTimeMillis() - 1));

		final LegalText saved = this.legalRepository.save(legalText);

		this.actorService.isSpam(saved.getBody());
		this.actorService.isSpam(saved.getLaws());
		this.actorService.isSpam(saved.getTitle());

		return saved;
	}

	public void delete(final LegalText legalText) {
		Assert.notNull(legalText);
		//A legal text cannot be deleted outside of draft mode.
		Assert.isTrue(!legalText.isFinalMode());

		//Assertion that the user modifying this text has the correct privilege.
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Assert.isTrue(authentication.getAuthorities().toArray()[0].toString().equals("ADMIN"));

		this.legalRepository.delete(legalText);
	}

	//Other methods

	//A table with the number of times that each legal text has been referenced.
	public Collection<LegalTextTable> legalTextTable() {
		return this.legalRepository.legalTextTable();
	}

	public Collection<LegalText> legalTextsFinalMode() {
		return this.legalRepository.legalTextsFinalMode();
	}
}
