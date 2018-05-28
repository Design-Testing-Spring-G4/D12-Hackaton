
package controllers.instructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.EducationRecordService;
import controllers.AbstractController;
import domain.EducationRecord;

@Controller
@RequestMapping("educationRecord/instructor")
public class EducationRecordInstructorController extends AbstractController {

	//Services

	@Autowired
	private EducationRecordService	educationRecordService;


	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int varId) {
		final ModelAndView result;
		final EducationRecord educationRecord = this.educationRecordService.create(varId);
		result = this.createEditModelAndView(educationRecord);

		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int varId) {
		final ModelAndView result;
		EducationRecord educationRecord;

		educationRecord = this.educationRecordService.findOne(varId);
		Assert.notNull(educationRecord);
		result = this.createEditModelAndView(educationRecord);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final EducationRecord er, final BindingResult binding) {
		ModelAndView result;

		if (er.getDiploma().isEmpty())
			binding.rejectValue("diploma", "org.hibernate.validator.constraints.NotEmpty.message");
		if (er.getInstitution().isEmpty())
			binding.rejectValue("institution", "org.hibernate.validator.constraints.NotEmpty.message");
		if (binding.hasErrors())
			result = this.createEditModelAndView(er);
		else
			try {
				final EducationRecord educationRecord = this.educationRecordService.save(er);
				this.educationRecordService.save(educationRecord);
				result = new ModelAndView("redirect:/curriculum/instructor/display.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(er, "educationRecord.commit.error");
			}
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final EducationRecord educationRecord, final BindingResult binding) {
		ModelAndView result;

		try {
			this.educationRecordService.delete(educationRecord);
			result = new ModelAndView("redirect:/curriculum/instructor/display.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(educationRecord, "educationRecord.commit.error");
		}
		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final EducationRecord educationRecord) {
		ModelAndView result;

		result = this.createEditModelAndView(educationRecord, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final EducationRecord educationRecord, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("educationRecord/edit");
		result.addObject("educationRecord", educationRecord);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "educationRecord/instructor/edit.do");

		return result;

	}
}
