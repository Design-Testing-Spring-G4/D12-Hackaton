
package domain;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
public class Manager extends Actor {

	//Relationships

	private Collection<Resort>	resorts;


	//Getters

	@NotNull
	@Valid
	@OneToMany(mappedBy = "manager")
	public Collection<Resort> getResorts() {
		return this.resorts;
	}

	//Setters

	public void setResorts(final Collection<Resort> resorts) {
		this.resorts = resorts;
	}
}
