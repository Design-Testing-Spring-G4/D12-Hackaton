
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.MailMessage;

@Repository
public interface MailMessageRepository extends JpaRepository<MailMessage, Integer> {

	//Finds the messages of a certain folder.
	@Query("select m from MailMessage m where m.folder.id=?1")
	Collection<MailMessage> mailMessagesFromFolder(int id);
}
