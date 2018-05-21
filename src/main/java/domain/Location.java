
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

import org.hibernate.validator.constraints.NotBlank;

@Embeddable
@Access(AccessType.PROPERTY)
public class Location {

	//Attributes

	private String	location;
	private String	gpsCoordinates;


	//Getters

	@NotBlank
	public String getLocation() {
		return this.location;
	}

	@NotBlank
	//@Pattern(regexp = "^[-+]?([1-8]?\\d(\\.\\d+)?|90(\\.0+)?),\\s*[-+]?(180(\\.0+)?|((1[0-7]\\d)|([1-9]?\\d))(\\.\\d+)?)$")
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
