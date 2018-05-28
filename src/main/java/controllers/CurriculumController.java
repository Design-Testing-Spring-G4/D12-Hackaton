
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.InstructorService;
import domain.Curriculum;
import domain.Instructor;

@Controller
@RequestMapping("curriculum")
public class CurriculumController extends AbstractController {

	//Services

	@Autowired
	private InstructorService	instructorService;


	//Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int varId) {
		final ModelAndView result;
		Instructor instructor = null;

		if (this.instructorService.findOne(varId) == null)
			result = new ModelAndView("redirect:/welcome/index.do");
		else {
			instructor = this.instructorService.findOne(varId);
			final Curriculum curriculum = instructor.getCurriculum();
			if (instructor.getCurriculum() == null)
				result = new ModelAndView("redirect:/instructor/list.do");
			else {
				result = new ModelAndView("curriculum/display");
				result.addObject("curriculum", curriculum);
				result.addObject("varId", varId);
				result.addObject("requestURI", "curriculum/display.do");
			}
		}

		return result;
	}
}
