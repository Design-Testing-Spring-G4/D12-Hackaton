
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Integer> {

	//The minimum, the maximum, the average, and the standard deviation	of the number of notes.
	@Query("select min(n), max(n), avg(n), stddev(n) from Note n")
	Double[] minMaxAvgStddevNotes();
}
