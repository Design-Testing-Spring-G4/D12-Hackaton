
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.FolderRepository;
import domain.Actor;
import domain.Folder;
import domain.MailMessage;

@Service
@Transactional
public class FolderService {

	//Managed repository

	@Autowired
	private FolderRepository	folderRepository;

	//Supporting Services

	@Autowired
	private ActorService		actorService;

	@Autowired
	private Validator			validator;


	//Simple CRUD methods

	public Folder create(final Actor actor) {
		final Folder folder = new Folder();

		folder.setMailMessage(new ArrayList<MailMessage>());
		folder.setActor(actor);
		folder.setChildren(new ArrayList<Folder>());
		folder.setSystem(false);

		return folder;
	}

	public Folder findOne(final int id) {
		Assert.notNull(id);

		return this.folderRepository.findOne(id);
	}

	public Collection<Folder> findAll() {
		return this.folderRepository.findAll();
	}

	public Folder save(final Folder folder) {
		Assert.notNull(folder);

		if (folder.getActor().getId() != 0 && folder.getId() != 0) {
			//The five default folders cannot be modified or moved.
			Assert.isTrue(!folder.isSystem());

			//Assertion that the user modifying this folder has the correct privilege.
			Assert.isTrue(this.actorService.findByPrincipal().getId() == folder.getActor().getId());
		}

		final Folder saved = this.folderRepository.save(folder);

		this.actorService.isSpam(saved.getName());

		return saved;
	}

	public void delete(final Folder folder) {
		Assert.notNull(folder);

		//The five default folders cannot be deleted.
		Assert.isTrue(!folder.isSystem());

		//Assertion that the user deleting this folder has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == folder.getActor().getId());

		this.folderRepository.delete(folder);
	}

	//Other methods

	public Collection<Folder> generateDefaultFolders(final Actor actor) {
		Assert.notNull(actor);

		final Collection<Folder> cf = new ArrayList<Folder>();

		final String[] names = new String[5];
		names[0] = "In box";
		names[1] = "Out box";
		names[2] = "Notification box";
		names[3] = "Trash box";
		names[4] = "Spam box";

		for (int i = 0; i <= 4; i++) {
			final Folder f = this.create(actor);
			f.setName(names[i]);
			f.setParent(null);
			f.setChildren(new ArrayList<Folder>());
			f.setSystem(true);

			final Folder save = this.save(f);
			cf.add(save);
		}

		return cf;
	}

	public Folder reconstruct(final Folder folder, final BindingResult binding) {
		Folder result;
		final Actor actor = this.actorService.findByPrincipal();

		if (folder.getId() == 0) {
			result = this.create(actor);
			result.setName(folder.getName());
			result.setParent(folder.getParent());
		} else {
			result = this.findOne(folder.getId());
			result.setName(folder.getName());
			result.setParent(folder.getParent());
		}

		this.validator.validate(result, binding);

		return result;
	}

	public Folder getFolderByName(final int id, final String name) {
		Assert.notNull(id);
		Assert.notNull(name);

		return this.folderRepository.getFolderByName(id, name);
	}

	public Folder getSystemFolderByName(final int id, final String name) {
		Assert.notNull(id);
		Assert.notNull(name);

		return this.folderRepository.getSystemFolderByName(id, name);
	}
}
