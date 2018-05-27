
package controllers.actor;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.FolderService;
import controllers.AbstractController;
import domain.Actor;
import domain.Folder;

@Controller
@RequestMapping("folder")
public class FolderController extends AbstractController {

	//Services

	@Autowired
	private FolderService	folderService;

	@Autowired
	private ActorService	actorService;


	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<Folder> folders;

		folders = this.actorService.findByPrincipal().getFolders();

		result = new ModelAndView("folder/list");
		result.addObject("folders", folders);
		result.addObject("requestURI", "folder/list.do");

		return result;
	}

	@RequestMapping(value = "/childrenList", method = RequestMethod.GET)
	public ModelAndView childrenList(@RequestParam final int varId) {
		final ModelAndView result;
		Collection<Folder> folders;
		Folder folder;

		folder = this.folderService.findOne(varId);
		folders = folder.getChildren();

		result = new ModelAndView("folder/childrenList");
		result.addObject("folders", folders);
		result.addObject("requestURI", "folder/childrenList.do");

		return result;
	}

	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		final Actor actor = this.actorService.findByPrincipal();
		final Folder folder = this.folderService.create(actor);
		result = this.createEditModelAndView(folder);

		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int varId) {
		final ModelAndView result;
		Folder folder;

		folder = this.folderService.findOne(varId);
		Assert.notNull(folder);
		result = this.createEditModelAndView(folder);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Folder f, final BindingResult binding) {
		ModelAndView result;

		if (f.getName().isEmpty()) {
			binding.rejectValue("name", "org.hibernate.validator.constraints.NotEmpty.message");
			result = this.createEditModelAndView(f, "folder.commit.error");
		} else if (binding.hasErrors())
			result = this.createEditModelAndView(f);
		else
			try {
				final Folder folder = this.folderService.reconstruct(f, binding);
				final Folder saved = this.folderService.save(folder);

				if (f.getId() == 0) {
					final Actor actor = saved.getActor();
					actor.getFolders().add(saved);
					this.actorService.save(actor);
				}

				result = new ModelAndView("redirect:/folder/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(f, "folder.commit.error");
			}
		return result;
	}
	//Deletion

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Folder folder, final BindingResult binding) {
		ModelAndView result;
		final Folder validator = this.folderService.findOne(folder.getId());

		if (binding.hasErrors())
			result = this.createEditModelAndView(folder);
		else
			try {
				if (!validator.getChildren().isEmpty())
					result = this.createEditModelAndView(folder, "folder.delete.error");
				else {
					final Actor actor = this.actorService.findByPrincipal();
					actor.getFolders().remove(validator);
					this.actorService.save(actor);
					this.folderService.delete(validator);
					result = new ModelAndView("redirect:/folder/list.do");
				}
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(folder, "folder.commit.error");
			}
		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final Folder folder) {
		ModelAndView result;

		result = this.createEditModelAndView(folder, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Folder folder, final String messageCode) {
		ModelAndView result;
		final Actor actor = this.actorService.findByPrincipal();
		final Collection<Folder> folders = actor.getFolders();
		if (folders.contains(folder))
			folders.remove(folder);

		result = new ModelAndView("folder/edit");
		result.addObject("folder", folder);
		result.addObject("folders", folders);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "folder/edit.do");

		return result;

	}
}
