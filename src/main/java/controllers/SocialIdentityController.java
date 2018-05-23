
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import domain.Actor;
import domain.SocialIdentity;

@Controller
@RequestMapping("socialIdentity")
public class SocialIdentityController extends AbstractController {

	//Services

	@Autowired
	private ActorService	actorService;


	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int varId) {
		final ModelAndView result;
		final Actor actor = this.actorService.findOne(varId);
		final Collection<SocialIdentity> socialIdentities = actor.getSocialIdentities();

		result = new ModelAndView("socialIdentity/list");
		result.addObject("socialIdentities", socialIdentities);
		result.addObject("varId", varId);
		result.addObject("requestURI", "socialIdentity/list.do");

		return result;
	}
}
