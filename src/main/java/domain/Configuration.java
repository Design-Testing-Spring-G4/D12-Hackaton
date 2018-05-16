
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Configuration extends DomainEntity {

	//Attributes

	private String	banner;
	private double	vat;
	private String	countryCode;
	private String	spamWords;
	private String	welcomeEN;
	private String	welcomeES;


	//Getters

	@NotBlank
	@URL
	public String getBanner() {
		return this.banner;
	}

	@Min(value = 0)
	public double getVat() {
		return this.vat;
	}

	@NotBlank
	public String getCountryCode() {
		return this.countryCode;
	}

	@NotBlank
	public String getSpamWords() {
		return this.spamWords;
	}

	@NotNull
	public String getWelcomeEN() {
		return this.welcomeEN;
	}

	@NotNull
	public String getWelcomeES() {
		return this.welcomeES;
	}

	//Setters

	public void setBanner(final String banner) {
		this.banner = banner;
	}

	public void setVat(final double vat) {
		this.vat = vat;
	}

	public void setCountryCode(final String countryCode) {
		this.countryCode = countryCode;
	}

	public void setSpamWords(final String spamWords) {
		this.spamWords = spamWords;
	}

	public void setWelcomeEN(final String welcomeEN) {
		this.welcomeEN = welcomeEN;
	}

	public void setWelcomeES(final String welcomeES) {
		this.welcomeES = welcomeES;
	}
}
