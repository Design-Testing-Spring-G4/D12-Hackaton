
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Entity
@Access(AccessType.PROPERTY)
public class Category extends DomainEntity {

	//Attributes

	private String					name;

	//Relationships

	private Category				parent;
	private Collection<Category>	children;
	private Collection<Resort>		resorts;


	//Getters

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getName() {
		return this.name;
	}

	@ManyToOne(optional = true)
	public Category getParent() {
		return this.parent;
	}

	@ElementCollection
	public Collection<Category> getChildren() {
		return this.children;
	}

	@NotNull
	@Valid
	@OneToMany
	public Collection<Resort> getResorts() {
		return this.resorts;
	}

	//Setters

	public void setName(final String name) {
		this.name = name;
	}

	public void setParent(final Category parent) {
		this.parent = parent;
	}

	public void setChildren(final Collection<Category> children) {
		this.children = children;
	}

	public void setResorts(final Collection<Resort> resorts) {
		this.resorts = resorts;
	}
}
