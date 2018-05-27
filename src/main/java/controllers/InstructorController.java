
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.InstructorService;
import domain.Instructor;
import forms.ActorRegisterForm;

@Controller
@RequestMapping("instructor")
public class InstructorController extends AbstractController {

	//Services

	@Autowired
	private InstructorService	instructorService;

	@Autowired
	private ActorService		actorService;


	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Instructor> instructors = this.instructorService.findAll();

		result = new ModelAndView("instructor/list");
		result.addObject("instructors", instructors);
		result.addObject("requestURI", "instructor/list.do");

		return result;
	}

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
				final Instructor instructor = this.instructorService.reconstruct(arf, binding);
				this.actorService.hashPassword(instructor);
				this.instructorService.save(instructor);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				oops.printStackTrace();
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

		result = new ModelAndView("instructor/edit");
		result.addObject("arf", arf);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "instructor/edit.do");

		return result;
	}
}
