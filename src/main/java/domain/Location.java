
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

@Embeddable
@Access(AccessType.PROPERTY)
public class Location {

	//Attributes

	private String	name;
	private String	gpsCoordinates;


	//Getters

	@NotBlank
	public String getName() {
		return this.name;
	}

	@NotBlank
	@Pattern(regexp = "^[-+]?([1-8]?\\d(\\.\\d+)?|90(\\.0+)?),\\s*[-+]?(180(\\.0+)?|((1[0-7]\\d)|([1-9]?\\d))(\\.\\d+)?)$")
	public String getGpsCoordinates() {
		return this.gpsCoordinates;
	}

	//Setters

	public void setName(final String name) {
		this.name = name;
	}

	public void setGpsCoordinates(final String gpsCoordinates) {
		this.gpsCoordinates = gpsCoordinates;
	}

}
