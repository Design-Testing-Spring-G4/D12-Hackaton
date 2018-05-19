
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Resort;

@Repository
public interface ResortRepository extends JpaRepository<Resort, Integer> {

	//The average, the minimum, the maximum and the standard deviation of the number of reservations per resort.
	@Query("select avg(r.reservations.size), min(r.reservations.size), max(r.reservations.size), stddev(r.reservations.size) from resort r")
	Double[] avgMinMaxStddevReservationsPerResort();

	//The average, the minimum, the maximum and the standard deviation of the number of activities per resort.
	@Query("select avg(r.activities.size), min(r.activities.size), max(r.activities.size), stddev(r.activities.size) from Resort r")
	Double[] avgMinMaxStddevActivitiesPerResort();
}
