
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "spots")
})
public class Resort extends DomainEntity {

	//Attributes

	private String					name;
	private Location				location;
	private String					description;
	private String					features;
	private Date					startDate;
	private Date					endDate;
	private String					picture;
	private int						spots;
	private double					priceAdult;
	private double					priceChild;

	//Relationships

	private Manager					manager;
	private LegalText				legalText;
	private Collection<TagValue>	tags;
	private Collection<Activity>	activities;
	private Collection<Reservation>	reservations;
	private Collection<Audit>		audits;
	private Collection<Competition>	competitions;


	//Getters

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getName() {
		return this.name;
	}

	@NotNull
	@Valid
	public Location getLocation() {
		return this.location;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getDescription() {
		return this.description;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getFeatures() {
		return this.features;
	}

	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getStartDate() {
		return this.startDate;
	}

	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getEndDate() {
		return this.endDate;
	}

	@URL
	public String getPicture() {
		return this.picture;
	}

	@Min(0)
	public int getSpots() {
		return this.spots;
	}

	@Min(0)
	public double getPriceAdult() {
		return this.priceAdult;
	}

	@Min(0)
	public double getPriceChild() {
		return this.priceChild;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Manager getManager() {
		return this.manager;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public LegalText getLegalText() {
		return this.legalText;
	}

	@NotNull
	@Valid
	@ManyToMany
	public Collection<TagValue> getTags() {
		return this.tags;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "resort")
	public Collection<Activity> getActivities() {
		return this.activities;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "resort")
	public Collection<Reservation> getReservations() {
		return this.reservations;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "resort")
	public Collection<Audit> getAudits() {
		return this.audits;
	}

	@NotNull
	@Valid
	@OneToMany
	public Collection<Competition> getCompetitions() {
		return this.competitions;
	}

	//Setters

	public void setName(final String name) {
		this.name = name;
	}

	public void setLocation(final Location location) {
		this.location = location;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setFeatures(final String features) {
		this.features = features;
	}

	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}

	public void setPicture(final String picture) {
		this.picture = picture;
	}

	public void setSpots(final int spots) {
		this.spots = spots;
	}

	public void setPriceAdult(final double priceAdult) {
		this.priceAdult = priceAdult;
	}

	public void setPriceChild(final double priceChild) {
		this.priceChild = priceChild;
	}

	public void setManager(final Manager manager) {
		this.manager = manager;
	}

	public void setLegalText(final LegalText legalText) {
		this.legalText = legalText;
	}

	public void setTags(final Collection<TagValue> tags) {
		this.tags = tags;
	}

	public void setActivities(final Collection<Activity> activities) {
		this.activities = activities;
	}

	public void setReservations(final Collection<Reservation> reservations) {
		this.reservations = reservations;
	}

	public void setAudits(final Collection<Audit> audits) {
		this.audits = audits;
	}

	public void setCompetitions(final Collection<Competition> competitions) {
		this.competitions = competitions;
	}
}
