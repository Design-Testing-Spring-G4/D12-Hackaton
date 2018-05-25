
package controllers.instructor;

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
import services.LessonService;
import controllers.AbstractController;
import domain.Instructor;
import domain.Lesson;

@Controller
@RequestMapping("lesson/instructor")
public class LessonInstructorController extends AbstractController {

	//Services

	@Autowired
	private ActorService	actorService;

	@Autowired
	private LessonService	lessonService;


	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Instructor instructor = (Instructor) this.actorService.findByPrincipal();
		final Collection<Lesson> lessons = this.lessonService.lessonsByInstructor(instructor.getId());

		result = new ModelAndView("lesson/list");
		result.addObject("lessons", lessons);
		result.addObject("requestURI", "lesson/instructor/list.do");

		return result;
	}

	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		final Lesson lesson = this.lessonService.create();
		result = this.createEditModelAndView(lesson);

		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int varId) {
		final ModelAndView result;
		final Lesson lesson = this.lessonService.findOne(varId);
		Assert.notNull(lesson);
		result = this.createEditModelAndView(lesson);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Lesson r, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(r);
		else
			try {
				final Lesson lesson = this.lessonService.reconstruct(r, binding);
				this.lessonService.save(lesson);
				result = new ModelAndView("redirect:/lesson/instructor/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(r, "lesson.commit.error");
			}
		return result;
	}

	//Deletion

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Lesson lesson, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(lesson);
		else
			try {
				this.lessonService.delete(lesson);
				result = new ModelAndView("redirect:/lesson/instructor/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(lesson, "lesson.commit.error");
			}
		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final Lesson lesson) {
		ModelAndView result;

		result = this.createEditModelAndView(lesson, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Lesson lesson, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("lesson/edit");
		result.addObject("lesson", lesson);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "lesson/instructor/edit.do");

		return result;
	}
}
