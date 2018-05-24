
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Entity
@Access(AccessType.PROPERTY)
public class Suggestion extends DomainEntity {

	//Attributes

	private String		title;
	private String		comments;
	private String		attachments;
	private boolean		anonymous;

	//Relationships

	private Actor		actor;
	private Competition	competition;


	//Getters

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getTitle() {
		return this.title;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getComments() {
		return this.comments;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getAttachments() {
		return this.attachments;
	}

	public boolean isAnonymous() {
		return this.anonymous;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Actor getActor() {
		return this.actor;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Competition getCompetition() {
		return this.competition;
	}

	//Setters

	public void setTitle(final String title) {
		this.title = title;
	}

	public void setComments(final String comments) {
		this.comments = comments;
	}

	public void setAttachments(final String attachments) {
		this.attachments = attachments;
	}

	public void setAnonymous(final boolean anonymous) {
		this.anonymous = anonymous;
	}

	public void setActor(final Actor actor) {
		this.actor = actor;
	}

	public void setCompetition(final Competition competition) {
		this.competition = competition;
	}
}
