
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Entity
@Access(AccessType.PROPERTY)
public class TagValue extends DomainEntity {

	//Attributes

	private String				value;

	//Relationships

	private Tag					tag;
	private Collection<Resort>	resorts;


	//Getters

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getValue() {
		return this.value;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Tag getTag() {
		return this.tag;
	}

	@NotNull
	@Valid
	@ManyToMany
	public Collection<Resort> getResorts() {
		return this.resorts;
	}

	//Setters

	public void setValue(final String value) {
		this.value = value;
	}

	public void setTag(final Tag tag) {
		this.tag = tag;
	}

	public void setResorts(final Collection<Resort> resorts) {
		this.resorts = resorts;
	}
}
