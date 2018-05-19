
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

	//The average, the minimum, the maximum and the standard deviation of the reservations' total prices.
	@Query("select avg(r.price), min(r.price), max(r.price), stddev(r.price) from Reservation r")
	Double[] avgMinMaxStddevPricePerReservation();
}
