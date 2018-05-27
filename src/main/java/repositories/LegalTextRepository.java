
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
	@Query("select new domain.LegalTextTable(r.legalText, count(r)) from Resort r group by r.legalText")
	Collection<LegalTextTable> legalTextTable();

	@Query("select l from LegalText l where l.finalMode = true")
	Collection<LegalText> legalTextsFinalMode();
}
