
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

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class MailMessage extends DomainEntity {

	//Attributes

	private Date		sent;
	private String		subject;
	private String		body;

	//Relationships

	private Priority	priority;
	private Actor		sender;
	private Actor		receiver;
	private Folder		folder;


	//Getters

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Folder getFolder() {
		return this.folder;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Actor getSender() {
		return this.sender;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Actor getReceiver() {
		return this.receiver;
	}

	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getSent() {
		return this.sent;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getSubject() {
		return this.subject;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getBody() {
		return this.body;
	}

	@NotNull
	@Valid
	public Priority getPriority() {
		return this.priority;
	}

	//Setters

	public void setFolder(final Folder folder) {
		this.folder = folder;
	}

	public void setSender(final Actor sender) {
		this.sender = sender;
	}

	public void setReceiver(final Actor receiver) {
		this.receiver = receiver;
	}

	public void setSent(final Date sent) {
		this.sent = sent;
	}

	public void setSubject(final String subject) {
		this.subject = subject;
	}

	public void setBody(final String body) {
		this.body = body;
	}

	public void setPriority(final Priority priority) {
		this.priority = priority;
	}
}
