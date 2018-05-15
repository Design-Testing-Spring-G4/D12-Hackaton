
package domain;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
public class Sponsor extends Actor {

	//Relationships

	private Collection<Competition>	competitions;


	//Getters

	@NotNull
	@Valid
	@OneToMany(mappedBy = "sponsor")
	public Collection<Competition> getCompetitions() {
		return this.competitions;
	}

	//Setters

	public void setCompetitions(final Collection<Competition> competitions) {
		this.competitions = competitions;
	}
}
