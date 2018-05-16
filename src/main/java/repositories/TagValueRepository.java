
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.TagValue;

@Repository
public interface TagValueRepository extends JpaRepository<TagValue, Integer> {

}
