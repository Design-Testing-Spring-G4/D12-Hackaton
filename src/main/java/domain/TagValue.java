
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class TagValue extends DomainEntity {

	//Attributes

	private String	value;

	//Relationships

	private Tag		tag;


	//Getters

	@NotBlank
	public String getValue() {
		return this.value;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Tag getTag() {
		return this.tag;
	}

	//Setters

	public void setValue(final String value) {
		this.value = value;
	}

	public void setTag(final Tag tag) {
		this.tag = tag;
	}
}
