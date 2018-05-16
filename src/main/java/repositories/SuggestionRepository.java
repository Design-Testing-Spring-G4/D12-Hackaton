
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Suggestion;

@Repository
public interface SuggestionRepository extends JpaRepository<Suggestion, Integer> {

}
