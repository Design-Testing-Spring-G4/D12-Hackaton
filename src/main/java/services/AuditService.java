
package services;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

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

	@Autowired
	private Validator		validator;


	//Simple CRUD methods

	public Audit create(final int varId) {

		final Audit audit = new Audit();

		final Auditor a = (Auditor) this.actorService.findByPrincipal();
		audit.setAuditor(a);
		audit.setResort(this.resortService.findOne(varId));

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

		audit.setMoment(new Date(System.currentTimeMillis() - 1));

		final Audit saved = this.auditRepository.save(audit);

		this.actorService.isSpam(saved.getAttachments());
		this.actorService.isSpam(saved.getDescription());
		this.actorService.isSpam(saved.getTitle());

		return saved;
	}

	public void delete(final Audit audit) {
		Assert.notNull(audit);
		//Draft/final mode assertion is done via controller.

		//Assertion that the user deleting this audit has the correct privilege.
		final Audit validator = this.findOne(audit.getId());
		Assert.isTrue(this.actorService.findByPrincipal().getId() == validator.getAuditor().getId());

		this.auditRepository.delete(audit);
	}

	//Deletion for internal operations such as cross-authorized requests.
	public void deleteInternal(final Audit audit) {
		Assert.notNull(audit);
		this.auditRepository.delete(audit);
	}

	//Other methods

	public Audit reconstruct(final Audit audit, final BindingResult binding) {
		Audit result;

		if (audit.getId() == 0) {
			result = this.create(audit.getResort().getId());
			result.setAttachments(audit.getAttachments());
			result.setDescription(audit.getDescription());
			result.setFinalMode(audit.isFinalMode());
			result.setTitle(audit.getTitle());
		} else {
			result = this.findOne(audit.getId());
			result.setAttachments(audit.getAttachments());
			result.setDescription(audit.getDescription());
			result.setFinalMode(audit.isFinalMode());
			result.setTitle(audit.getTitle());
		}

		this.validator.validate(result, binding);

		return result;
	}

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
