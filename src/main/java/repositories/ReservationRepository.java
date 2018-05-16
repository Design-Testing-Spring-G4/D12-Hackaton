
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

}
