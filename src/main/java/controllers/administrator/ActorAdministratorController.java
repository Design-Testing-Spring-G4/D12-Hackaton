
package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import security.UserAccount;
import services.ActorService;
import controllers.AbstractController;
import domain.Actor;

@Controller
@RequestMapping("actor/administrator")
public class ActorAdministratorController extends AbstractController {

	//Services

	@Autowired
	private ActorService	actorService;


	//Listing suspicious actors

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Actor> actors = this.actorService.findSuspiciousActors();

		result = new ModelAndView("actor/list");
		result.addObject("participants", actors);
		result.addObject("requestURI", "actor/administrator/list.do");

		return result;
	}

	//Ban and unban actors

	@RequestMapping(value = "/ban", method = RequestMethod.GET)
	public ModelAndView ban(@RequestParam final int varId) {
		final ModelAndView result;
		final Actor actor = this.actorService.findOne(varId);
		final UserAccount userAccount = actor.getUserAccount();

		if (!userAccount.getAuthorities().isEmpty())
			userAccount.removeAuthority(userAccount.getAuthorities().iterator().next());
		else if (actor.getClass().toString().contains("User"))
			actor.getUserAccount().addAuthority((Authority) Authority.listAuthorities().toArray()[1]);
		else if (actor.getClass().toString().contains("Manager"))
			actor.getUserAccount().addAuthority((Authority) Authority.listAuthorities().toArray()[2]);
		else if (actor.getClass().toString().contains("Instructor"))
			actor.getUserAccount().addAuthority((Authority) Authority.listAuthorities().toArray()[3]);
		else if (actor.getClass().toString().contains("Auditor"))
			actor.getUserAccount().addAuthority((Authority) Authority.listAuthorities().toArray()[4]);
		else if (actor.getClass().toString().contains("Sponsor"))
			actor.getUserAccount().addAuthority((Authority) Authority.listAuthorities().toArray()[5]);
		this.actorService.save(actor);

		result = new ModelAndView("redirect:/actor/administrator/list.do");

		return result;
	}
}
