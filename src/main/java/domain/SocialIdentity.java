
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class SocialIdentity extends DomainEntity {

	//Attributes

	private String	nick;
	private String	network;
	private String	profile;
	private String	photo;

	//Relationships

	private Actor	actor;


	//Getters

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getNick() {
		return this.nick;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getNetwork() {
		return this.network;
	}

	@NotBlank
	@URL
	public String getProfile() {
		return this.profile;
	}

	@URL
	public String getPhoto() {
		return this.photo;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Actor getActor() {
		return this.actor;
	}

	//Setters

	public void setNick(final String nick) {
		this.nick = nick;
	}

	public void setNetwork(final String network) {
		this.network = network;
	}

	public void setProfile(final String link) {
		this.profile = link;
	}

	public void setPhoto(final String photo) {
		this.photo = photo;
	}

	public void setActor(final Actor actor) {
		this.actor = actor;
	}
}
