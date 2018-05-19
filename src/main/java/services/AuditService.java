
package services;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AuditRepository;
import domain.Audit;
import domain.Auditor;

@Service
@Transactional
public class AuditService {

	//Managed repository

	@Autowired
	private AuditRepository	auditRepository;

	//Supporting services

	@Autowired
	private ActorService	actorService;

	@Autowired
	private ResortService	resortService;


	//Simple CRUD methods

	public Audit create(final int varId) {

		final Audit audit = new Audit();

		final Auditor a = (Auditor) this.actorService.findByPrincipal();
		audit.setAuditor(a);
		audit.setResort(this.resortService.findOne(varId));
		audit.setMoment(new Date(System.currentTimeMillis() - 1));

		return audit;
	}

	public Collection<Audit> findAll() {

		return this.auditRepository.findAll();
	}

	public Audit findOne(final int id) {
		Assert.notNull(id);

		return this.auditRepository.findOne(id);
	}

	public Audit save(final Audit audit) {
		Assert.notNull(audit);
		//Draft/final mode assertion is done via controller.

		//Assertion that every attachment is an URL.
		final String attachments = audit.getAttachments();
		if (attachments != null && !attachments.isEmpty())
			Assert.isTrue(this.isValidURLCollection(attachments));

		//Assertion that the user modifying this audit has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == audit.getAuditor().getId());

		this.actorService.isSpam(audit.getAttachments());
		this.actorService.isSpam(audit.getDescription());
		this.actorService.isSpam(audit.getTitle());

		return this.auditRepository.save(audit);
	}

	public void delete(final Audit audit) {
		Assert.notNull(audit);
		//Draft/final mode assertion is done via controller.

		//Assertion that the user deleting this audit has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == audit.getAuditor().getId());

		this.auditRepository.delete(audit);
	}

	//Other methods

	public boolean isValidURLCollection(final String attachments) {
		boolean result = false;
		final String[] parts = attachments.split("\\,");

		for (final String s : parts)
			try {
				new URI(s).parseServerAuthority();
				result = true;
			} catch (final URISyntaxException e) {
				result = false;
			}
		return result;
	}
}
