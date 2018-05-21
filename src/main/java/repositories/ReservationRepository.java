
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

	//The ratio of "PENDING" reservations.
	@Query("select (select r from Reservation r where r.status = 'PENDING')*1.0/r from Reservation r")
	Double ratioPendingReservations();

	//The ratio of "DUE" reservations.
	@Query("select (select r from Reservation r where r.status = 'DUE')*1.0/r from Reservation r")
	Double ratioDueReservations();

	//The ratio of "ACCEPTED" reservations.
	@Query("select (select r from Reservation r where r.status = 'ACCEPTED')*1.0/r from Reservation r")
	Double ratioAcceptedReservations();

	//The ratio of "REJECTED" reservations.
	@Query("select (select r from Reservation r where r.status = 'REJECTED')*1.0/r from Reservation r")
	Double ratioRejectedReservations();
}
