
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Participation extends DomainEntity {

	//Attributes

	private Date	moment;
	private Status	status;
	private String	comments;

	//Relationships

	private Actor	actor;


	//Getters

	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	@NotNull
	@Valid
	public Status getStatus() {
		return this.status;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getComments() {
		return this.comments;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Actor getActor() {
		return this.actor;
	}

	//Setters

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	public void setStatus(final Status status) {
		this.status = status;
	}

	public void setComments(final String comments) {
		this.comments = comments;
	}

	public void setActor(final Actor actor) {
		this.actor = actor;
	}
}
