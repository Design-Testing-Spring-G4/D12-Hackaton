
package controllers.administrator;

import java.util.Arrays;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.MailMessageService;
import controllers.AbstractController;
import domain.Actor;
import domain.MailMessage;
import domain.Priority;

@Controller
@RequestMapping("mailMessage/administrator")
public class MailMessageAdministratorController extends AbstractController {

	//Services

	@Autowired
	private MailMessageService	mailMessageService;

	@Autowired
	private ActorService		actorService;


	//Create notification

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		MailMessage mailMessage;

		mailMessage = this.mailMessageService.create();
		result = this.createEditModelAndView(mailMessage);

		return result;
	}

	//Broadcast notification

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "broadcast")
	public ModelAndView broadcast(@Valid final MailMessage mailMessage, final BindingResult binding) {
		ModelAndView result;

		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Assert.isTrue(authentication.getAuthorities().toArray()[0].toString().equals("ADMIN"));

		try {
			this.mailMessageService.broadcastNotification(mailMessage);
			result = new ModelAndView("redirect:/folder/list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/folder/list.do");
		}

		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final MailMessage mailMessage) {
		ModelAndView result;

		result = this.createEditModelAndView(mailMessage, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final MailMessage mailMessage, final String messageCode) {
		ModelAndView result;
		Collection<Actor> receivers;

		receivers = this.actorService.findAll();

		result = new ModelAndView("mailMessage/create");
		result.addObject("mailMessage", mailMessage);
		result.addObject("receivers", receivers);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "mailMessage/administrator/create.do");

		return result;

	}

	@ModelAttribute("priorities")
	public Collection<Priority> getPriorities() {
		return Arrays.asList(Priority.values());
	}
}
