
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.LegalText;
import domain.LegalTextTable;

@Repository
public interface LegalTextRepository extends JpaRepository<LegalText, Integer> {

	//A table with the number of times that each legal text has been referenced.
	@Query("select new domain.LegalTextTable(t.legalText, count(t)) from Trip t group by t.legalText")
	Collection<LegalTextTable> legalTextTable();
}
