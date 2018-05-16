
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Folder;

@Repository
public interface FolderRepository extends JpaRepository<Folder, Integer> {

	//Find a folder in an actor's collection given the folder's name.
	@Query("select f from Folder f where f.actor.id = ?1 and f.name = ?2")
	Folder getFolderByName(int id, String name);

	//Find a system folder in an actor's collection given the folder's name.
	@Query("select f from Folder f where f.actor.id = ?1 and f.name = ?2 and f.system = true")
	Folder getSystemFolderByName(int id, String name);
}
