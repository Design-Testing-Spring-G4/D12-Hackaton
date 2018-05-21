
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Activity;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Integer> {

	//The ratio of "ENTERTAINMENT" activities.
	@Query("select (select a from Activity a where a.category = 'ENTERTAINMENT')*1.0/a from Activity a")
	Double ratioEntertainmentActivities();

	//The ratio of "SPORT" activities with instructor.
	@Query("select (select a from Activity a where a.category = 'SPORT' and a.instructor != null)*1.0/a from Activity a")
	Double ratioSportActivitiesWithInstructor();

	//The ratio of "SPORT" activities without instructor.
	@Query("select (select a from Activity a where a.category = 'SPORT' and a.instructor = null)*1.0/a from Activity a")
	Double ratioSportActivitiesWithoutInstructor();

	//The ratio of "SPORT" activities.
	@Query("select (select a from Activity a where a.category = 'SPORT')*1.0/a from Activity a")
	Double ratioSportActivities();

	//The ratio of "TOURISM" activities.
	@Query("select (select a from Activity a where a.category = 'TOURISM')*1.0/a from Activity a")
	Double ratioTourismActivities();

	//The minimum, the maximum, the average, and the standard deviation	of the number of notes per activity.
	@Query("select min(a.notes.size), max(a.notes.size), avg(a.notes.size), stddev(a.notes.size) from Activity a")
	Double[] minMaxAvgStddevNotesPerActivity();
}