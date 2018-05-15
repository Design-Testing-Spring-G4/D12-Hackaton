
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class LegalText extends DomainEntity {

	//Attributes

	private String	title;
	private String	body;
	private String	laws;
	private Date	registered;
	private boolean	finalMode;


	//Getters

	@NotBlank
	public String getTitle() {
		return this.title;
	}

	@NotBlank
	public String getBody() {
		return this.body;
	}

	@NotBlank
	public String getLaws() {
		return this.laws;
	}

	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getRegistered() {
		return this.registered;
	}

	public boolean isFinalMode() {
		return this.finalMode;
	}

	//Setters

	public void setTitle(final String title) {
		this.title = title;
	}

	public void setBody(final String body) {
		this.body = body;
	}

	public void setLaws(final String laws) {
		this.laws = laws;
	}

	public void setRegistered(final Date registered) {
		this.registered = registered;
	}

	public void setFinalMode(final boolean finalMode) {
		this.finalMode = finalMode;
	}

}
