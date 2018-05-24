
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.MailMessageRepository;
import domain.Actor;
import domain.Folder;
import domain.MailMessage;
import domain.Priority;

@Service
@Transactional
public class MailMessageService {

	//Managed repository

	@Autowired
	private MailMessageRepository	mailMessageRepository;

	//Supporting services

	@Autowired
	private ActorService			actorService;

	@Autowired
	private FolderService			folderService;

	@Autowired
	private Validator				validator;


	//Simple CRUD methods

	public MailMessage create() {
		final MailMessage mailMessage = new MailMessage();

		mailMessage.setPriority(Priority.NEUTRAL);
		mailMessage.setSender(this.actorService.findByPrincipal());

		return mailMessage;
	}

	public Collection<MailMessage> findAll() {

		return this.mailMessageRepository.findAll();
	}

	public MailMessage findOne(final int id) {
		Assert.notNull(id);

		return this.mailMessageRepository.findOne(id);
	}

	public MailMessage save(final MailMessage mailMessage) {
		Assert.notNull(mailMessage);

		//Assertion that the user modifying this mail message has the correct privilege, that is, he or she is the sender or receiver.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == mailMessage.getSender().getId() || this.actorService.findByPrincipal().getId() == mailMessage.getReceiver().getId());

		mailMessage.setSent(new Date(System.currentTimeMillis() - 1));

		final MailMessage saved = this.mailMessageRepository.save(mailMessage);

		this.actorService.isSpam(saved.getBody());
		this.actorService.isSpam(saved.getSubject());

		return saved;
	}
	public void delete(final MailMessage mailMessage) {
		Assert.notNull(mailMessage);

		//Assertion that the user deleting this mail message has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == mailMessage.getFolder().getActor().getId());

		this.mailMessageRepository.delete(mailMessage);
	}

	//Other methods

	public MailMessage reconstruct(final MailMessage mailMessage, final BindingResult binding) {
		MailMessage result;

		result = this.create();
		result.setReceiver(mailMessage.getReceiver());
		result.setPriority(mailMessage.getPriority());
		result.setSubject(mailMessage.getSubject());
		result.setBody(mailMessage.getBody());

		this.validator.validate(result, binding);

		return result;
	}

	//Creates a copy of a message and sends it to the receiver of the original message. Necessary to save outside this method to avoid complications.
	public MailMessage send(final MailMessage m) {
		Assert.notNull(m);

		//Parsing the message's subject and body for spam words.
		final boolean isSpamSubject = this.actorService.isSpam(m.getSubject());
		final boolean isSpamBody = this.actorService.isSpam(m.getBody());
		String folderName = "In box";
		if (isSpamSubject == true || isSpamBody == true)
			folderName = "Spam box";

		//Finds the system folder where the message must be sent to.
		final Folder f = this.folderService.getSystemFolderByName(m.getReceiver().getId(), folderName);

		final MailMessage sentMsg = this.create();

		sentMsg.setSubject(m.getSubject());
		sentMsg.setBody(m.getBody());
		sentMsg.setPriority(m.getPriority());
		sentMsg.setReceiver(m.getReceiver());
		sentMsg.setFolder(f);

		return sentMsg;
	}

	//Moves a message from one folder to another.
	public void move(final MailMessage message, final Folder newOne) {
		Assert.notNull(message);
		Assert.notNull(newOne);
		final Folder folder = message.getFolder();
		folder.getMailMessage().remove(message);
		this.folderService.save(folder);
		newOne.getMailMessage().add(message);
		message.setFolder(newOne);
		this.save(message);
		this.folderService.save(newOne);
	}

	//Sends a message to every notification box in the system.
	public void broadcastNotification(final MailMessage m) {
		Assert.notNull(m);

		for (final Actor a : this.actorService.findAll()) {
			final Folder notificationBox = this.folderService.getSystemFolderByName(a.getId(), "notification box");
			final MailMessage sentMsg = this.create();

			sentMsg.setSubject(m.getSubject());
			sentMsg.setBody(m.getBody());
			sentMsg.setPriority(m.getPriority());
			sentMsg.setReceiver(a);
			sentMsg.setFolder(notificationBox);

			this.save(sentMsg);
		}
	}

	public Collection<MailMessage> mailMessagesFromFolder(final int id) {
		return this.mailMessageRepository.mailMessagesFromFolder(id);
	}
}
