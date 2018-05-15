
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Reservation extends DomainEntity {

	//Attributes

	private int						adults;
	private int						children;
	private Date					startDate;
	private Date					endDate;
	private double					price;
	private Status					status;
	private String					comments;
	private CreditCard				creditCard;
	private String					reason;

	//Relationships

	private User					user;
	private Resort					resort;
	private Collection<Activity>	activities;
	private Collection<Lesson>		lessons;


	//Getters

	@Min(1)
	public int getAdults() {
		return this.adults;
	}

	@Min(0)
	public int getChildren() {
		return this.children;
	}

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getStartDate() {
		return this.startDate;
	}

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getEndDate() {
		return this.endDate;
	}

	@Min(0)
	public double getPrice() {
		return this.price;
	}

	@NotNull
	@Valid
	public Status getStatus() {
		return this.status;
	}

	public String getComments() {
		return this.comments;
	}

	@Valid
	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	public String getReason() {
		return this.reason;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public User getUser() {
		return this.user;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Resort getResort() {
		return this.resort;
	}

	@NotNull
	@Valid
	@ManyToMany
	public Collection<Activity> getActivities() {
		return this.activities;
	}

	@NotNull
	@Valid
	@ManyToMany
	public Collection<Lesson> getLessons() {
		return this.lessons;
	}

	//Setters

	public void setAdults(final int adults) {
		this.adults = adults;
	}

	public void setChildren(final int children) {
		this.children = children;
	}

	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}

	public void setPrice(final double price) {
		this.price = price;
	}

	public void setStatus(final Status status) {
		this.status = status;
	}

	public void setComments(final String comments) {
		this.comments = comments;
	}

	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	public void setReason(final String reason) {
		this.reason = reason;
	}

	public void setUser(final User user) {
		this.user = user;
	}

	public void setResort(final Resort resort) {
		this.resort = resort;
	}

	public void setActivities(final Collection<Activity> activities) {
		this.activities = activities;
	}

	public void setLessons(final Collection<Lesson> lessons) {
		this.lessons = lessons;
	}
}
