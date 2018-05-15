
package domain;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
public class Instructor extends Actor {

	//Relationships

	private Curriculum			curriculum;
	private Collection<Resort>	resorts;


	//Getters

	@Valid
	@OneToOne(optional = true)
	public Curriculum getCurriculum() {
		return this.curriculum;
	}

	@NotNull
	@Valid
	@ManyToMany
	public Collection<Resort> getResorts() {
		return this.resorts;
	}

	//Setters

	public void setCurriculum(final Curriculum curriculum) {
		this.curriculum = curriculum;
	}

	public void setResorts(final Collection<Resort> resorts) {
		this.resorts = resorts;
	}
}
