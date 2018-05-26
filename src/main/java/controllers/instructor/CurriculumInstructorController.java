
package controllers.instructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import controllers.AbstractController;
import domain.Curriculum;
import domain.Instructor;

@Controller
@RequestMapping("curriculum/instructor")
public class CurriculumInstructorController extends AbstractController {

	//Services

	@Autowired
	private ActorService	actorService;


	//Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView result;
		Curriculum curriculum;

		curriculum = ((Instructor) this.actorService.findByPrincipal()).getCurriculum();

		result = new ModelAndView("curriculum/display");
		result.addObject("curriculum", curriculum);
		result.addObject("requestURI", "curriculum/display.do");

		return result;
	}

}
