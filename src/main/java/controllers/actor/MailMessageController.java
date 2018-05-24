
package controllers.actor;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.FolderService;
import services.MailMessageService;
import controllers.AbstractController;
import domain.Actor;
import domain.Folder;
import domain.MailMessage;

@Controller
@RequestMapping("mailMessage")
public class MailMessageController extends AbstractController {

	//Services

	@Autowired
	private MailMessageService	mailMessageService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private FolderService		folderService;

	//Ancillary attributes

	private MailMessage			currentMsg;


	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int varId) {
		final ModelAndView result;
		Collection<MailMessage> mailMessages;

		mailMessages = this.mailMessageService.mailMessagesFromFolder(varId);

		result = new ModelAndView("mailMessage/list");
		result.addObject("mailMessages", mailMessages);
		result.addObject("requestURI", "mailMessage/list.do");

		return result;
	}

	//Displaying

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int varId) {
		final ModelAndView result;
		final MailMessage mailMessage = this.mailMessageService.findOne(varId);

		result = new ModelAndView("mailMessage/display");
		result.addObject("mailMessage", mailMessage);
		result.addObject("requestURI", "mailMessage/display.do");

		return result;
	}

	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		final ModelAndView result;
		MailMessage mailMessage;

		mailMessage = this.mailMessageService.create();
		result = this.createCreateModelAndView(mailMessage);

		return result;
	}

	//Sending

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final MailMessage mm, final BindingResult binding) {
		ModelAndView result;

		try {
			final MailMessage mailMessage = this.mailMessageService.reconstruct(mm, binding);
			final Folder folder = this.folderService.getSystemFolderByName(mailMessage.getSender().getId(), "out box");
			mailMessage.setFolder(folder);
			final MailMessage saved = this.mailMessageService.save(mailMessage);

			final MailMessage sent = this.mailMessageService.send(saved);
			this.mailMessageService.save(sent);
			result = new ModelAndView("redirect:/folder/list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/folder/list.do");
		}

		return result;
	}

	//Moving

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int varId) {
		final ModelAndView result;
		final MailMessage mailMessage = this.mailMessageService.findOne(varId);
		this.setCurrentMsg(mailMessage);

		result = this.createEditModelAndView(mailMessage);

		return result;
	}

	@RequestMapping(value = "/move", method = RequestMethod.GET)
	public ModelAndView move(@RequestParam final int varId) {
		ModelAndView result;
		final Folder folder = this.folderService.findOne(varId);

		try {
			final MailMessage currentMsg = this.getCurrentMsg();
			currentMsg.setFolder(folder);
			this.mailMessageService.save(currentMsg);
			result = new ModelAndView("redirect:/folder/list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/folder/list.do");
		}

		return result;
	}

	//Deleting

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int varId) {
		ModelAndView result;
		final MailMessage mailMessage = this.mailMessageService.findOne(varId);
		final Folder trash = this.folderService.getSystemFolderByName(this.actorService.findByPrincipal().getId(), "trash box");

		try {
			if (mailMessage.getFolder().equals(trash) && mailMessage.getFolder().isSystem())
				this.mailMessageService.delete(mailMessage);
			else {
				mailMessage.setFolder(trash);
				this.mailMessageService.save(mailMessage);
			}

			result = new ModelAndView("redirect:/folder/list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/folder/list.do");
		}
		return result;
	}

	//Ancillary methods

	protected ModelAndView createCreateModelAndView(final MailMessage mailMessage) {
		ModelAndView result;

		result = this.createCreateModelAndView(mailMessage, null);

		return result;
	}

	protected ModelAndView createCreateModelAndView(final MailMessage mailMessage, final String messageCode) {
		ModelAndView result;
		Collection<Actor> receivers;

		receivers = this.actorService.findAll();

		result = new ModelAndView("mailMessage/create");
		result.addObject("receivers", receivers);
		result.addObject("mailMessage", mailMessage);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "mailMessage/create.do");
		return result;

	}

	protected ModelAndView createEditModelAndView(final MailMessage mailMessage) {
		ModelAndView result;

		result = this.createEditModelAndView(mailMessage, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final MailMessage mailMessage, final String messageCode) {
		ModelAndView result;
		final Collection<Folder> folders = this.actorService.findByPrincipal().getFolders();

		result = new ModelAndView("mailMessage/edit");
		result.addObject("mailMessage", mailMessage);
		result.addObject("folders", folders);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "mailMessage/edit.do");
		return result;

	}

	public MailMessage getCurrentMsg() {
		return this.currentMsg;
	}

	public void setCurrentMsg(final MailMessage currentMsg) {
		this.currentMsg = currentMsg;
	}
}
