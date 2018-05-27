
package repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Competition;
import domain.Participation;

@Repository
public interface CompetitionRepository extends JpaRepository<Competition, Integer> {

	//The top five competitions by prize pool.
	@Query("select c from Competition c order by c.prizePool desc")
	List<Competition> topFiveCompetitionsPrizePool(Pageable pageable);

	//The top five competitions by maximum number of participants allowed.
	@Query("select c from Competition c order by c.maxParticipants desc")
	List<Competition> topFiveCompetitionsMaxParticipants(Pageable pageable);

	@Query("select c from Competition c where c.banner != ''")
	Collection<Competition> competitionsWithBanner();

	@Query("select c from Competition c where ?1 member of c.participations")
	Competition competitionWithParticipation(Participation participation);
}
