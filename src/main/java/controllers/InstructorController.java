
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.InstructorService;
import domain.Instructor;

@Controller
@RequestMapping("instructor")
public class InstructorController extends AbstractController {

	//Services

	@Autowired
	private InstructorService	instructorService;


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
}
