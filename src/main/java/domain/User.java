
package domain;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
public class User extends Actor {

	//Relationships

	private Collection<Reservation>	reservations;


	//Getters

	@NotNull
	@Valid
	@OneToMany(mappedBy = "user")
	public Collection<Reservation> getReservations() {
		return this.reservations;
	}

	//Setters

	public void setReservations(final Collection<Reservation> reservations) {
		this.reservations = reservations;
	}
}
