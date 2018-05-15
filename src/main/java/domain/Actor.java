
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import security.UserAccount;

@Entity
@Access(AccessType.PROPERTY)
public class Actor extends DomainEntity {

	//Attributes

	private String						name;
	private String						surname;
	private String						email;
	private String						phone;
	private String						address;
	private UserAccount					userAccount;
	private boolean						suspicious;

	//Relationships

	private Collection<SocialIdentity>	socialIdentities;
	private Collection<Folder>			folders;
	private Collection<Participation>	participations;


	//Getters

	@NotBlank
	public String getName() {
		return this.name;
	}

	@NotBlank
	public String getSurname() {
		return this.surname;
	}

	@NotBlank
	@Email
	public String getEmail() {
		return this.email;
	}

	public String getPhone() {
		return this.phone;
	}

	public String getAddress() {
		return this.address;
	}

	@Valid
	@OneToOne(cascade = CascadeType.ALL)
	public UserAccount getUserAccount() {
		return this.userAccount;
	}

	@Valid
	@NotNull
	@OneToMany
	public Collection<Folder> getFolders() {
		return this.folders;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "actor")
	public Collection<SocialIdentity> getSocialIdentities() {
		return this.socialIdentities;
	}

	public boolean isSuspicious() {
		return this.suspicious;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "actor")
	public Collection<Participation> getParticipations() {
		return this.participations;
	}

	//Setters

	public void setName(final String name) {
		this.name = name;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	public void setUserAccount(final UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	public void setFolders(final Collection<Folder> folders) {
		this.folders = folders;
	}

	public void setSocialIdentities(final Collection<SocialIdentity> socialIdentities) {
		this.socialIdentities = socialIdentities;
	}

	public void setSuspicious(final boolean suspicious) {
		this.suspicious = suspicious;
	}

	public void setParticipations(final Collection<Participation> participations) {
		this.participations = participations;
	}
}
