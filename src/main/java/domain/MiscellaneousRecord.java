
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class MiscellaneousRecord extends DomainEntity {

	//Attributes

	private String		title;
	private String		link;
	private String		comments;

	//Relationships

	private Curriculum	curriculum;


	//Getters

	@NotBlank
	public String getTitle() {
		return this.title;
	}

	@URL
	public String getLink() {
		return this.link;
	}

	public String getComments() {
		return this.comments;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Curriculum getCurriculum() {
		return this.curriculum;
	}

	//Setters

	public void setTitle(final String title) {
		this.title = title;
	}

	public void setLink(final String link) {
		this.link = link;
	}

	public void setComments(final String comments) {
		this.comments = comments;
	}

	public void setCurriculum(final Curriculum curriculum) {
		this.curriculum = curriculum;
	}

}
