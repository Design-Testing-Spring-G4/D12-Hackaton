
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
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class ProfessionalRecord extends DomainEntity {

	//Attributes

	private String		company;
	private Date		periodStart;
	private Date		periodEnd;
	private String		role;
	private String		attachment;
	private String		comments;

	//Relationship

	private Curriculum	curriculum;


	//Getters

	@NotBlank
	public String getCompany() {
		return this.company;
	}

	@NotNull
	@Past
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
	public String getRole() {
		return this.role;
	}

	@URL
	public String getAttachment() {
		return this.attachment;
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

	public void setCompany(final String company) {
		this.company = company;
	}

	public void setPeriodStart(final Date periodStart) {
		this.periodStart = periodStart;
	}

	public void setPeriodEnd(final Date periodEnd) {
		this.periodEnd = periodEnd;
	}

	public void setRole(final String role) {
		this.role = role;
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
