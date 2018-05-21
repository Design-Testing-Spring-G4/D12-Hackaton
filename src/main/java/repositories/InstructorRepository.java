
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Instructor;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Integer> {

	//The ratio of instructors who have registered their curricula.
	@Query("select (select count(i) from Instructor i where i.curriculum != null)*1.0/count(i) from Instructor i")
	Double ratioInstructorsWithCurriculum();

	//The ratio of instructors whose curriculum's been endorsed.
	@Query("select (select count(i) from Instructor i join i.curriculum c where c.endorserRecord is not empty)*1.0/count(i) from Instructor i")
	Double ratioInstructorsEndorsed();
}
