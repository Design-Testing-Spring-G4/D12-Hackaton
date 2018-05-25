
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Instructor;
import domain.Resort;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Integer> {

	//The ratio of instructors who have registered their curricula.
	@Query("select (select count(i) from Instructor i where i.curriculum != null)*1.0/count(i) from Instructor i")
	Double ratioInstructorsWithCurriculum();

	//The ratio of instructors whose curriculum's been endorsed.
	@Query("select (select count(i) from Instructor i join i.curriculum c where c.endorserRecord is not empty)*1.0/count(i) from Instructor i")
	Double ratioInstructorsEndorsed();

	//The ratio of suspicious instructors.
	@Query("select (select count(i) from Instructor i where i.suspicious = true)*1.0/count(i) from Instructor i")
	Double ratioSuspiciousInstructors();

	@Query("select i from Instructor i where ?1 member of i.resorts")
	Collection<Instructor> instructorsWithResort(Resort resort);
}
