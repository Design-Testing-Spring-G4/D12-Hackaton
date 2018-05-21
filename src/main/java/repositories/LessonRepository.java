
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Lesson;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Integer> {

	//The minimum, the maximum, the average, and the standard deviation	of the number of notes per lesson.
	@Query("select min(l.notes.size), max(l.notes.size), avg(l.notes.size), stddev(l.notes.size) from Lesson l")
	Double[] minMaxAvgStddevNotesPerLesson();
}
