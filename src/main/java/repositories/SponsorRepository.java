
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Sponsor;

@Repository
public interface SponsorRepository extends JpaRepository<Sponsor, Integer> {

	//The minimum, the maximum, the average, and the standard deviation	of competitions per sponsor.
	@Query("select min(s.competitions.size), max(s.competitions.size), avg(s.competitions.size), stddev(s.competitions.size) from Sponsor s")
	Double[] minMaxAvgStdddevCompetitionsPerSponsor();
}
