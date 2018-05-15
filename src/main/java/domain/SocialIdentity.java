
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class SocialIdentity extends DomainEntity {

	//Attributes

	private String	nick;
	private String	network;
	private String	profile;
	private String	photo;


	//Getters

	@NotBlank
	public String getNick() {
		return this.nick;
	}

	@NotBlank
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
}
