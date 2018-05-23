
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
public class Audit extends DomainEntity {

	//Attributes

	private Date	moment;
	private String	title;
	private String	description;
	private String	attachments;
	private boolean	finalMode;

	//Relationships

	private Auditor	auditor;
	private Resort	resort;


	//Getters

	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}

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

	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getAttachments() {
		return this.attachments;
	}

	public boolean isFinalMode() {
		return this.finalMode;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Auditor getAuditor() {
		return this.auditor;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Resort getResort() {
		return this.resort;
	}

	//Setters

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setAttachments(final String attachments) {
		this.attachments = attachments;
	}

	public void setFinalMode(final boolean finalMode) {
		this.finalMode = finalMode;
	}

	public void setAuditor(final Auditor auditor) {
		this.auditor = auditor;
	}

	public void setResort(final Resort resort) {
		this.resort = resort;
	}
}
