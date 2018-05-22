
package repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Competition;

@Repository
public interface CompetitionRepository extends JpaRepository<Competition, Integer> {

	//The top five competitions by prize pool.
	@Query("select c from Competition c order by c.prizePool desc")
	List<Competition> topFiveCompetitionsPrizePool(Pageable pageable);

	//The top five competitions by maximum number of participants allowed.
	@Query("select c from Competition c order by c.maxParticipants desc")
	List<Competition> topFiveCompetitionsMaxParticipants(Pageable pageable);
}
