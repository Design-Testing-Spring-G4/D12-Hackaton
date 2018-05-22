
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

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

		//Assertion that the user modifying this miscellaneous record has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == note.getAuditor().getId());

		note.setMoment(new Date(System.currentTimeMillis() - 1));

		final Note saved = this.noteRepository.save(note);
		return saved;
	}

	public void delete(final Note note) {
		Assert.notNull(note);

		//Assertion that the user deleting this miscellaneous record has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == note.getAuditor().getId());

		this.noteRepository.delete(note);
	}
}
