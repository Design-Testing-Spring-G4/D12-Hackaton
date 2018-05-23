
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
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class EducationRecord extends DomainEntity {

	//Attributes

	private String		diploma;
	private Date		periodStart;
	private Date		periodEnd;
	private String		institution;
	private String		attachment;
	private String		comments;

	//Relationships

	private Curriculum	curriculum;


	//Getters

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getDiploma() {
		return this.diploma;
	}

	@Past
	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getPeriodStart() {
		return this.periodStart;
	}

	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getPeriodEnd() {
		return this.periodEnd;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getInstitution() {
		return this.institution;
	}

	@URL
	public String getAttachment() {
		return this.attachment;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
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

	public void setDiploma(final String diploma) {
		this.diploma = diploma;
	}

	public void setPeriodStart(final Date periodStart) {
		this.periodStart = periodStart;
	}

	public void setPeriodEnd(final Date periodEnd) {
		this.periodEnd = periodEnd;
	}

	public void setInstitution(final String institution) {
		this.institution = institution;
	}

	public void setAttachment(final String attachment) {
		this.attachment = attachment;
	}

	public void setComments(final String comments) {
		this.comments = comments;
	}

	public void setCurriculum(final Curriculum curriculum) {
		this.curriculum = curriculum;
	}

}
