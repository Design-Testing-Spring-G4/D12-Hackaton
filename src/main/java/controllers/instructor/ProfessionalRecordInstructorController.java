
package controllers.instructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ProfessionalRecordService;
import controllers.AbstractController;
import domain.ProfessionalRecord;

@Controller
@RequestMapping("professionalRecord/instructor")
public class ProfessionalRecordInstructorController extends AbstractController {

	//Services

	@Autowired
	private ProfessionalRecordService	professionalRecordService;


	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int varId) {
		final ModelAndView result;
		final ProfessionalRecord professionalRecord = this.professionalRecordService.create(varId);
		result = this.createEditModelAndView(professionalRecord);

		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int varId) {
		final ModelAndView result;
		final ProfessionalRecord professionalRecord = this.professionalRecordService.findOne(varId);
		Assert.notNull(professionalRecord);
		result = this.createEditModelAndView(professionalRecord);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final ProfessionalRecord pr, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(pr);
		else
			try {
				final ProfessionalRecord professionalRecord = this.professionalRecordService.reconstruct(pr, binding);
				this.professionalRecordService.save(professionalRecord);
				result = new ModelAndView("redirect:/curriculum/instructor/display.do");
			} catch (final Throwable oops) {
				oops.printStackTrace();
				result = this.createEditModelAndView(pr, "professionalRecord.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final ProfessionalRecord professionalRecord, final BindingResult binding) {
		ModelAndView result;

		try {
			this.professionalRecordService.delete(professionalRecord);
			result = new ModelAndView("redirect:/curriculum/instructor/display.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(professionalRecord, "professionalRecord.commit.error");
		}
		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final ProfessionalRecord professionalRecord) {
		ModelAndView result;

		result = this.createEditModelAndView(professionalRecord, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final ProfessionalRecord professionalRecord, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("professionalRecord/edit");
		result.addObject("professionalRecord", professionalRecord);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "professionalRecord/instructor/edit.do");

		return result;

	}
}
