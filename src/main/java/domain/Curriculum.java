
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Curriculum extends DomainEntity {

	//Attributes

	private String							ticker;

	//Relationships

	private Collection<EducationRecord>		educationRecord;
	private Collection<EndorserRecord>		endorserRecord;
	private Collection<MiscellaneousRecord>	miscellaneousRecord;
	private PersonalRecord					personalRecord;
	private Collection<ProfessionalRecord>	professionalRecord;
	private Instructor						instructor;


	//Getters

	@Column(unique = true)
	@NotBlank
	@Pattern(regexp = "\\d{6}-\\w{4}$")
	public String getTicker() {
		return this.ticker;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "curriculum")
	public Collection<EducationRecord> getEducationRecord() {
		return this.educationRecord;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "curriculum")
	public Collection<EndorserRecord> getEndorserRecord() {
		return this.endorserRecord;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "curriculum")
	public Collection<MiscellaneousRecord> getMiscellaneousRecord() {
		return this.miscellaneousRecord;
	}

	@Valid
	@OneToOne(optional = true)
	public PersonalRecord getPersonalRecord() {
		return this.personalRecord;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "curriculum")
	public Collection<ProfessionalRecord> getProfessionalRecord() {
		return this.professionalRecord;
	}

	@NotNull
	@Valid
	@OneToOne(optional = false)
	public Instructor getInstructor() {
		return this.instructor;
	}

	//Setters

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

	public void setEducationRecord(final Collection<EducationRecord> educationRecord) {
		this.educationRecord = educationRecord;
	}

	public void setEndorserRecord(final Collection<EndorserRecord> endorserRecord) {
		this.endorserRecord = endorserRecord;
	}

	public void setMiscellaneousRecord(final Collection<MiscellaneousRecord> miscellaneousRecord) {
		this.miscellaneousRecord = miscellaneousRecord;
	}

	public void setPersonalRecord(final PersonalRecord personalRecord) {
		this.personalRecord = personalRecord;
	}

	public void setProfessionalRecord(final Collection<ProfessionalRecord> professionalRecord) {
		this.professionalRecord = professionalRecord;
	}

	public void setInstructor(final Instructor instructor) {
		this.instructor = instructor;
	}
}
