
package controllers.instructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CurriculumService;
import services.PersonalRecordService;
import controllers.AbstractController;
import domain.Curriculum;
import domain.PersonalRecord;

@Controller
@RequestMapping("personalRecord/instructor")
public class PersonalRecordInstructorController extends AbstractController {

	//Services

	@Autowired
	private PersonalRecordService	personalRecordService;

	@Autowired
	private CurriculumService		curriculumService;


	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int varId) {
		final ModelAndView result;
		final PersonalRecord personalRecord = this.personalRecordService.create(varId);
		result = this.createEditModelAndView(personalRecord);

		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int varId) {
		final ModelAndView result;
		final Curriculum curriculum = this.curriculumService.findOne(varId);
		final PersonalRecord personalRecord = curriculum.getPersonalRecord();
		Assert.notNull(personalRecord);
		result = this.createEditModelAndView(personalRecord);

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final PersonalRecord pr, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(pr);
		else
			try {
				final PersonalRecord personalRecord = this.personalRecordService.reconstruct(pr, binding);
				this.personalRecordService.save(personalRecord);
				result = new ModelAndView("redirect:/curriculum/instructor/display.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(pr, "personalRecord.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final PersonalRecord personalRecord, final BindingResult binding) {
		ModelAndView result;

		try {
			this.personalRecordService.delete(personalRecord);
			result = new ModelAndView("redirect:/curriculum/instructor/display.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(personalRecord, "personalRecord.commit.error");
		}
		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final PersonalRecord personalRecord) {
		ModelAndView result;

		result = this.createEditModelAndView(personalRecord, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final PersonalRecord personalRecord, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("personalRecord/edit");
		result.addObject("personalRecord", personalRecord);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "personalRecord/instructor/edit.do");

		return result;

	}
}
