
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.InstructorService;
import services.LessonService;
import domain.Lesson;

@Controller
@RequestMapping("lesson")
public class LessonController extends AbstractController {

	//Services

	@Autowired
	private LessonService		lessonService;

	@Autowired
	private InstructorService	instructorService;


	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int varId) {
		ModelAndView result;

		if (this.instructorService.findOne(varId) == null)
			result = new ModelAndView("redirect:/welcome/index.do");
		else {
			final Collection<Lesson> lessons = this.lessonService.lessonsByInstructor(varId);

			result = new ModelAndView("lesson/list");
			result.addObject("lessons", lessons);
			result.addObject("requestURI", "lesson/list.do");
		}

		return result;
	}
}
