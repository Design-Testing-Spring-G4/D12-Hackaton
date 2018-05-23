
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "category")
})
public class Activity extends DomainEntity {

	//Attributes

	private String				title;
	private String				description;
	private ActivityCategory	category;
	private double				price;

	//Relationships

	private Instructor			instructor;
	private Collection<Note>	notes;


	//Getters

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getTitle() {
		return this.title;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getDescription() {
		return this.description;
	}

	@NotNull
	@Valid
	public ActivityCategory getCategory() {
		return this.category;
	}

	@Min(0)
	public double getPrice() {
		return this.price;
	}

	@Valid
	@ManyToOne(optional = true)
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

	public void setTitle(final String title) {
		this.title = title;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setCategory(final ActivityCategory category) {
		this.category = category;
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
