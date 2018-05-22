
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ManagerService;
import domain.Manager;
import forms.ActorRegisterForm;

@Controller
@RequestMapping("manager")
public class ManagerController extends AbstractController {

	//Services

	@Autowired
	private ManagerService	managerService;

	@Autowired
	private ActorService	actorService;


	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		final ActorRegisterForm arf = new ActorRegisterForm();

		result = this.createEditModelAndView(arf);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final ActorRegisterForm arf, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			arf.setAcceptedTerms(false);
			result = this.createEditModelAndView(arf);
		} else
			try {
				final Manager manager = this.managerService.reconstruct(arf, binding);
				this.actorService.hashPassword(manager);
				this.managerService.save(manager);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				System.out.println(oops.getMessage());
				result = this.createEditModelAndView(arf, "user.commit.error");
			}
		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final ActorRegisterForm arf) {
		ModelAndView result;

		result = this.createEditModelAndView(arf, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final ActorRegisterForm arf, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("user/edit");
		result.addObject("arf", arf);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "user/edit.do");

		return result;
	}
}
