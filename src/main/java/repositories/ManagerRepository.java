
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Manager;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Integer> {

	//The average, the minimum, the maximum and the standard deviation of the number of resorts managed per manager.
	@Query("select avg(m.resorts.size), min(m.resorts.size), max(m.resorts.size), stddev(m.resorts.size) from Manager m")
	Double[] avgMinMaxStddevResortsPerManager();

	//The ratio of suspicious managers.
	@Query("select (select count(m) from Manager m where m.suspicious = true)*1.0/count(m) from Manager m")
	Double ratioSuspiciousManagers();
}
