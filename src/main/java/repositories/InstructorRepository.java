
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Instructor;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Integer> {

}
