
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	//The ratio of suspicious users.
	@Query("select (select count(u) from User u where u.suspicious = true)*1.0/count(u) from User u")
	Double ratioSuspiciousUsers();
}
