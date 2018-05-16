
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Lesson;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Integer> {

}
