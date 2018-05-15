
package domain;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
public class Auditor extends Actor {

	//Relationships

	private Collection<Audit>	audits;
	private Collection<Note>	notes;


	//Getters

	@NotNull
	@Valid
	@OneToMany(mappedBy = "auditor")
	public Collection<Audit> getAudits() {
		return this.audits;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "auditor")
	public Collection<Note> getNotes() {
		return this.notes;
	}

	//Setters

	public void setAudits(final Collection<Audit> audits) {
		this.audits = audits;
	}

	public void setNotes(final Collection<Note> notes) {
		this.notes = notes;
	}
}
