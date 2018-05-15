
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
public class Folder extends DomainEntity {

	//Attributes

	private String					name;
	private boolean					system;

	//Relationship

	private Collection<MailMessage>	mailMessage;
	private Actor					actor;
	private Folder					parent;
	private Collection<Folder>		children;


	//Getters

	@ManyToOne(optional = true)
	public Folder getParent() {
		return this.parent;
	}

	@OneToMany(mappedBy = "parent")
	@ElementCollection
	public Collection<Folder> getChildren() {
		return this.children;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Actor getActor() {
		return this.actor;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getName() {
		return this.name;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "folder")
	public Collection<MailMessage> getMailMessage() {
		return this.mailMessage;
	}

	public boolean isSystem() {
		return this.system;
	}

	//Setters

	public void setParent(final Folder parent) {
		this.parent = parent;
	}

	public void setChildren(final Collection<Folder> children) {
		this.children = children;
	}

	public void setActor(final Actor actor) {
		this.actor = actor;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setMailMessage(final Collection<MailMessage> mailMessage) {
		this.mailMessage = mailMessage;
	}

	public void setSystem(final boolean system) {
		this.system = system;
	}
}
