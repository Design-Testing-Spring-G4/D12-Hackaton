
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Competition;

@Repository
public interface CompetitionRepository extends JpaRepository<Competition, Integer> {

}
