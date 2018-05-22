
package forms;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

public class ActorRegisterForm {

	//Attributes

	private String	username;
	private String	password;
	private String	repeatPassword;
	private String	name;
	private String	surname;
	private String	address;
	private String	phone;
	private String	email;
	private boolean	acceptedTerms;


	//Getters

	@Size(min = 5, max = 32)
	@Column(unique = true)
	public String getUsername() {
		return this.username;
	}

	@Size(min = 5, max = 32)
	public String getPassword() {
		return this.password;
	}

	@Size(min = 5, max = 32)
	public String getRepeatPassword() {
		return this.repeatPassword;
	}

	@NotBlank
	public String getName() {
		return this.name;
	}

	@NotBlank
	public String getSurname() {
		return this.surname;
	}

	public String getAddress() {
		return this.address;
	}

	public String getPhone() {
		return this.phone;
	}

	@NotNull
	@Email
	public String getEmail() {
		return this.email;
	}

	@NotNull
	public boolean isAcceptedTerms() {
		return this.acceptedTerms;
	}

	//Setters

	public void setUsername(final String username) {
		this.username = username;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public void setRepeatPassword(final String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public void setAcceptedTerms(final boolean acceptedTerms) {
		this.acceptedTerms = acceptedTerms;
	}
}
