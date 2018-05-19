
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
}
