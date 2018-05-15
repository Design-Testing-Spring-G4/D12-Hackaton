
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class EndorserRecord extends DomainEntity {

	//Attributes

	private String		name;
	private String		email;
	private String		phone;
	private String		profile;
	private String		comments;

	//Relationships

	private Curriculum	curriculum;


	//Getters

	@NotBlank
	public String getName() {
		return this.name;
	}

	@NotBlank
	@Email
	public String getEmail() {
		return this.email;
	}

	@NotBlank
	public String getPhone() {
		return this.phone;
	}

	@NotBlank
	@URL
	public String getProfile() {
		return this.profile;
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

	public void setName(final String name) {
		this.name = name;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}

	public void setProfile(final String profile) {
		this.profile = profile;
	}

	public void setComments(final String comments) {
		this.comments = comments;
	}

	public void setCurriculum(final Curriculum curriculum) {
		this.curriculum = curriculum;
	}

}
