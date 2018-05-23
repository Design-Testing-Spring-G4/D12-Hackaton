
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
public class Competition extends DomainEntity {

	//Attributes

	private String						title;
	private String						description;
	private Date						startDate;
	private Date						endDate;
	private String						sports;
	private int							maxParticipants;
	private String						banner;
	private String						link;
	private double						entry;
	private double						prizePool;
	private String						rules;

	//Relationships

	private Sponsor						sponsor;
	private Collection<Suggestion>		suggestions;
	private Collection<Participation>	participations;


	//Getters

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getTitle() {
		return this.title;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getDescription() {
		return this.description;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getStartDate() {
		return this.startDate;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getEndDate() {
		return this.endDate;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getSports() {
		return this.sports;
	}

	@Min(2)
	public int getMaxParticipants() {
		return this.maxParticipants;
	}

	@URL
	public String getBanner() {
		return this.banner;
	}

	@URL
	public String getLink() {
		return this.link;
	}

	@Min(0)
	public double getEntry() {
		return this.entry;
	}

	@Min(0)
	public double getPrizePool() {
		return this.prizePool;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getRules() {
		return this.rules;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Sponsor getSponsor() {
		return this.sponsor;
	}

	@NotNull
	@Valid
	@OneToMany
	public Collection<Suggestion> getSuggestions() {
		return this.suggestions;
	}

	@NotNull
	@Valid
	@OneToMany
	public Collection<Participation> getParticipations() {
		return this.participations;
	}

	//Setters

	public void setTitle(final String title) {
		this.title = title;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}

	public void setSports(final String sports) {
		this.sports = sports;
	}

	public void setMaxParticipants(final int maxParticipants) {
		this.maxParticipants = maxParticipants;
	}

	public void setBanner(final String banner) {
		this.banner = banner;
	}

	public void setLink(final String link) {
		this.link = link;
	}

	public void setEntry(final double entry) {
		this.entry = entry;
	}

	public void setPrizePool(final double prizePool) {
		this.prizePool = prizePool;
	}

	public void setRules(final String rules) {
		this.rules = rules;
	}

	public void setSponsor(final Sponsor sponsor) {
		this.sponsor = sponsor;
	}

	public void setSuggestions(final Collection<Suggestion> suggestions) {
		this.suggestions = suggestions;
	}

	public void setParticipations(final Collection<Participation> participations) {
		this.participations = participations;
	}
}
