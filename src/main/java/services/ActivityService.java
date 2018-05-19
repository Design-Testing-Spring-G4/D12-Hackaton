
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ActivityRepository;
import domain.Activity;
import domain.ActivityCategory;
import domain.Instructor;
import domain.Note;

@Service
@Transactional
public class ActivityService {

	//Managed repository

	@Autowired
	private ActivityRepository	activityRepository;

	//Supporting services

	@Autowired
	private ActorService		actorService;


	//Simple CRUD methods

	public Activity create() {

		final Activity activity = new Activity();

		final Instructor instructor = (Instructor) this.actorService.findByPrincipal();
		activity.setInstructor(instructor);
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

		//Assertion that the user modifying this miscellaneous record has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == activity.getInstructor().getId());

		//Business rule: only activities in the SPORT category may have an instructor assigned.
		if (activity.getInstructor() != null)
			Assert.isTrue(activity.getCategory() == ActivityCategory.SPORT);

		final Activity saved = this.activityRepository.save(activity);
		return saved;
	}

	public void delete(final Activity activity) {
		Assert.notNull(activity);

		//Assertion that the user deleting this miscellaneous record has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == activity.getInstructor().getId());

		this.activityRepository.delete(activity);
	}

	//Other methods

	public Double ratioEntertainmentActivities() {
		return this.activityRepository.ratioEntertainmentActivities();
	}

}
