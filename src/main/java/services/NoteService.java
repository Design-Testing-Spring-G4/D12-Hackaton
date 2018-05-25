
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.NoteRepository;
import domain.Auditor;
import domain.Note;

@Service
@Transactional
public class NoteService {

	//Managed repository

	@Autowired
	private NoteRepository	noteRepository;

	//Supporting services

	@Autowired
	private ActorService	actorService;

	@Autowired
	private Validator		validator;


	//Simple CRUD methods

	public Note create() {

		final Note note = new Note();

		final Auditor auditor = (Auditor) this.actorService.findByPrincipal();
		note.setAuditor(auditor);

		return note;
	}

	public Note findOne(final int id) {
		Assert.notNull(id);

		return this.noteRepository.findOne(id);
	}

	public Collection<Note> findAll() {
		return this.noteRepository.findAll();
	}

	public Note save(final Note note) {
		Assert.notNull(note);

		//Assertion that the user modifying this note has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == note.getAuditor().getId());

		note.setMoment(new Date(System.currentTimeMillis() - 1));

		final Note saved = this.noteRepository.save(note);

		this.actorService.isSpam(saved.getRemark());

		return saved;
	}

	//Specific save for a manager's reply.
	public Note saveMng(final Note note) {
		Assert.notNull(note);

		//Assertion that the user modifying this note has the correct privilege.
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Assert.isTrue(authentication.getAuthorities().toArray()[0].toString().equals("MANAGER"));

		note.setReplyMoment(new Date(System.currentTimeMillis() - 1));

		final Note saved = this.noteRepository.save(note);

		this.actorService.isSpam(saved.getReply());

		return saved;
	}

	public void delete(final Note note) {
		Assert.notNull(note);

		//Assertion that the user deleting this note has the correct privilege.
		final Note validator = this.findOne(note.getId());
		Assert.isTrue(this.actorService.findByPrincipal().getId() == validator.getAuditor().getId());

		this.noteRepository.delete(note);
	}

	//Other methods

	public Note reconstruct(final Note note, final BindingResult binding) {
		Note result;

		if (note.getId() == 0) {
			result = this.create();
			result.setRemark(note.getRemark());
		} else {
			result = this.findOne(note.getId());
			result.setReply(note.getReply());
		}

		this.validator.validate(result, binding);

		return result;
	}
}
