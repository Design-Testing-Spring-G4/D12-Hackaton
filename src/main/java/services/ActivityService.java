
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ActivityRepository;
import domain.Activity;
import domain.ActivityCategory;
import domain.Note;
import domain.Resort;

@Service
@Transactional
public class ActivityService {

	//Managed repository

	@Autowired
	private ActivityRepository	activityRepository;

	//Supporting services

	@Autowired
	private ActorService		actorService;

	@Autowired
	private Validator			validator;


	//Simple CRUD methods

	public Activity create() {

		final Activity activity = new Activity();

		activity.setNotes(new ArrayList<Note>());

		return activity;
	}

	public Activity findOne(final int id) {
		Assert.notNull(id);

		return this.activityRepository.findOne(id);
	}

	public Collection<Activity> findAll() {
		return this.activityRepository.findAll();
	}

	public Activity save(final Activity activity) {
		Assert.notNull(activity);

		//Assertion that the user modifying this activity has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == activity.getResort().getManager().getId());

		//Business rule: only activities in the SPORT category may have an instructor assigned.
		if (activity.getInstructor() != null)
			Assert.isTrue(activity.getCategory() == ActivityCategory.SPORT);

		final Activity saved = this.activityRepository.save(activity);

		this.actorService.isSpam(saved.getDescription());
		this.actorService.isSpam(saved.getTitle());

		return saved;
	}

	//Save for internal operations such as a cross-authorized requests.
	public Activity saveInternal(final Activity activity) {
		Assert.notNull(activity);

		//Assertion that the user modifying this activity has the correct privilege.
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Assert.isTrue(authentication.getAuthorities().toArray()[0].toString().equals("AUDITOR"));

		final Activity saved = this.activityRepository.save(activity);
		return saved;
	}

	public void delete(final Activity activity) {
		Assert.notNull(activity);

		//Assertion that the user deleting this activity has the correct privilege.
		final Activity validator = this.findOne(activity.getId());
		Assert.isTrue(this.actorService.findByPrincipal().getId() == validator.getResort().getManager().getId());

		this.activityRepository.delete(activity);
	}
	//Other methods

	public Activity reconstruct(final Activity activity, final BindingResult binding) {
		Activity result;

		if (activity.getId() == 0) {
			result = this.create();
			result.setCategory(activity.getCategory());
			result.setDescription(activity.getDescription());
			result.setPrice(activity.getPrice());
			result.setTitle(activity.getTitle());
			result.setResort(activity.getResort());
		} else {
			result = this.findOne(activity.getId());
			result.setCategory(activity.getCategory());
			result.setDescription(activity.getDescription());
			result.setPrice(activity.getPrice());
			result.setTitle(activity.getTitle());
			result.setResort(activity.getResort());
		}

		this.validator.validate(result, binding);

		return result;
	}
	public Double ratioEntertainmentActivities() {
		return this.activityRepository.ratioEntertainmentActivities();
	}

	public Double ratioSportActivitiesWithInstructor() {
		return this.activityRepository.ratioSportActivitiesWithInstructor();
	}

	public Double ratioSportActivitiesWithoutInstructor() {
		return this.activityRepository.ratioSportActivitiesWithoutInstructor();
	}

	public Double ratioSportActivities() {
		return this.activityRepository.ratioSportActivities();
	}

	public Double ratioTourismActivities() {
		return this.activityRepository.ratioTourismActivities();
	}

	public Double[] minMaxAvgStddevNotesPerActivity() {
		return this.activityRepository.minMaxAvgStddevNotesPerActivity();
	}

	public Collection<Activity> activitiesFromResortNotFree(final Resort resort) {
		return this.activityRepository.activitiesFromResortNotFree(resort);
	}
}
