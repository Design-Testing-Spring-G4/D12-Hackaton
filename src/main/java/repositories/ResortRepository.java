
package repositories;

import java.util.Collection;

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

	//The resorts that have got at least 15% more reservations than the	average, ordered by number of reservations.
	@Query("select r from Resort r where r.reservations.size >= (select avg(r.reservations.size)*1.15 from Resort r) order by t.reservations.size")
	Collection<Resort> resortsWithAboveAverageReservations();

	//The ratio of full resorts.
	@Query("select (select r from Resort r where r.spots = 0)*1.0/r from Resort r")
	Double ratioFullResorts();

	//The minimum, the maximum, the average, and the standard deviation	of audit records per resort.
	@Query("select min(r.audits.size), max(r.audits.size), avg(r.audits.size), stddev(r.audits.size) from Resort r")
	Double[] minMaxAvgStddevAuditsPerResort();

	//The ratio of resorts with an audit record.
	@Query("select (select count(r) from Resort r where r.audits.size > 0)*1.0/count(r) from Resort r")
	Double ratioResortsWithAudit();
}
