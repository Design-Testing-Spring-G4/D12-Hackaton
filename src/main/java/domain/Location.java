
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Embeddable
@Access(AccessType.PROPERTY)
public class Location {

	//Attributes

	private String	location;
	private String	gpsCoordinates;


	//Getters

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getLocation() {
		return this.location;
	}

	@NotBlank
	@Pattern(regexp = "^[-+]?\\d{1,2}\\.\\d{1,2}\\,\\ [-+]?\\d{1,2}\\.\\d{1,2}$")
	public String getGpsCoordinates() {
		return this.gpsCoordinates;
	}

	//Setters

	public void setLocation(final String location) {
		this.location = location;
	}

	public void setGpsCoordinates(final String gpsCoordinates) {
		this.gpsCoordinates = gpsCoordinates;
	}

}
