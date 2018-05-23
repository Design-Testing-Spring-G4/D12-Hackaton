
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Entity
@Access(AccessType.PROPERTY)
public class Lesson extends DomainEntity {

	//Attributes

	private String				name;
	private String				description;
	private Schedule			schedule;
	private double				price;

	//Relationship

	private Instructor			instructor;
	private Collection<Note>	notes;


	//Getters

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getName() {
		return this.name;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getDescription() {
		return this.description;
	}

	@NotNull
	@Valid
	public Schedule getSchedule() {
		return this.schedule;
	}

	@Min(0)
	public double getPrice() {
		return this.price;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Instructor getInstructor() {
		return this.instructor;
	}

	@NotNull
	@Valid
	@OneToMany
	public Collection<Note> getNotes() {
		return this.notes;
	}

	//Setters

	public void setName(final String name) {
		this.name = name;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setSchedule(final Schedule schedule) {
		this.schedule = schedule;
	}

	public void setPrice(final double price) {
		this.price = price;
	}

	public void setInstructor(final Instructor instructor) {
		this.instructor = instructor;
	}

	public void setNotes(final Collection<Note> notes) {
		this.notes = notes;
	}
}
