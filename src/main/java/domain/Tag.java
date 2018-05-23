
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Entity
@Access(AccessType.PROPERTY)
public class Tag extends DomainEntity {

	//Attributes

	private String					name;

	//Relationships

	private Collection<TagValue>	tagValues;


	//Getters

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getName() {
		return this.name;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "tag")
	public Collection<TagValue> getTagValues() {
		return this.tagValues;
	}

	//Setters

	public void setName(final String name) {
		this.name = name;
	}

	public void setTagValues(final Collection<TagValue> tagValues) {
		this.tagValues = tagValues;
	}
}
