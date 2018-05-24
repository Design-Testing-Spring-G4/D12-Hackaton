
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
import services.SocialIdentityService;
import controllers.AbstractController;
import domain.Actor;
import domain.SocialIdentity;

@Controller
@RequestMapping("socialIdentity/actor")
public class SocialIdentityActorController extends AbstractController {

	//Services

	@Autowired
	private ActorService			actorService;

	@Autowired
	private SocialIdentityService	socialIdentityService;


	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Actor actor = this.actorService.findByPrincipal();
		final Collection<SocialIdentity> socialIdentities = actor.getSocialIdentities();

		result = new ModelAndView("socialIdentity/list");
		result.addObject("socialIdentities", socialIdentities);
		result.addObject("requestURI", "socialIdentity/actor/list.do");

		return result;
	}

	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		final SocialIdentity socialIdentity = this.socialIdentityService.create();
		result = this.createEditModelAndView(socialIdentity);

		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int varId) {
		final ModelAndView result;
		SocialIdentity socialIdentity;

		socialIdentity = this.socialIdentityService.findOne(varId);
		Assert.notNull(socialIdentity);
		result = this.createEditModelAndView(socialIdentity);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final SocialIdentity si, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(si);
		else
			try {
				final SocialIdentity socialIdentity = this.socialIdentityService.reconstruct(si, binding);
				this.socialIdentityService.save(socialIdentity);
				result = new ModelAndView("redirect:/socialIdentity/actor/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(si, "socialIdentity.commit.error");
			}
		return result;
	}

	//Deletion

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final SocialIdentity socialIdentity, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(socialIdentity);
		else
			try {
				this.socialIdentityService.delete(socialIdentity);
				result = new ModelAndView("redirect:/socialIdentity/actor/list.do");
			} catch (final Throwable oops) {
				oops.printStackTrace();
				result = this.createEditModelAndView(socialIdentity, "socialIdentity.commit.error");
			}
		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final SocialIdentity socialIdentity) {
		ModelAndView result;

		result = this.createEditModelAndView(socialIdentity, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final SocialIdentity socialIdentity, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("socialIdentity/edit");
		result.addObject("socialIdentity", socialIdentity);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "socialIdentity/actor/edit.do");

		return result;
	}
}
