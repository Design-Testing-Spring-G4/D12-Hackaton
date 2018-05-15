
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

@Embeddable
@Access(AccessType.PROPERTY)
public class CreditCard {

	//Attributes

	private String	holder;
	private String	brand;
	private String	number;
	private Integer	expMonth;
	private Integer	expYear;
	private Integer	cvv;


	//Getters

	@NotBlank
	public String getHolder() {
		return this.holder;
	}

	@NotBlank
	public String getBrand() {
		return this.brand;
	}

	@NotBlank
	@CreditCardNumber
	public String getNumber() {
		return this.number;
	}

	@Range(min = 1, max = 12)
	public Integer getExpMonth() {
		return this.expMonth;
	}

	@Range(min = 2000, max = 2100)
	public Integer getExpYear() {
		return this.expYear;
	}

	@Range(min = 100, max = 999)
	public Integer getCvv() {
		return this.cvv;
	}

	//Setters

	public void setHolder(final String holder) {
		this.holder = holder;
	}

	public void setBrand(final String brand) {
		this.brand = brand;
	}

	public void setNumber(final String number) {
		this.number = number;
	}

	public void setExpMonth(final Integer expMonth) {
		this.expMonth = expMonth;
	}

	public void setExpYear(final Integer expYear) {
		this.expYear = expYear;
	}

	public void setCvv(final Integer cvv) {
		this.cvv = cvv;
	}

}
