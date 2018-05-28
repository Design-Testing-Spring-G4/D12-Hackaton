
package controllers.instructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.EndorserRecordService;
import controllers.AbstractController;
import domain.EndorserRecord;

@Controller
@RequestMapping("endorserRecord/instructor")
public class EndorserRecordInstructorController extends AbstractController {

	//Services

	@Autowired
	private EndorserRecordService	endorserRecordService;


	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int varId) {
		final ModelAndView result;
		final EndorserRecord endorserRecord = this.endorserRecordService.create(varId);
		result = this.createEditModelAndView(endorserRecord);

		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int varId) {
		final ModelAndView result;
		EndorserRecord endorserRecord;

		endorserRecord = this.endorserRecordService.findOne(varId);
		Assert.notNull(endorserRecord);
		result = this.createEditModelAndView(endorserRecord);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final EndorserRecord er, final BindingResult binding) {
		ModelAndView result;

		if (er.getName().isEmpty())
			binding.rejectValue("name", "org.hibernate.validator.constraints.NotEmpty.message");
		if (er.getProfile().isEmpty())
			binding.rejectValue("profile", "org.hibernate.validator.constraints.NotEmpty.message");
		if (er.getEmail().isEmpty())
			binding.rejectValue("email", "org.hibernate.validator.constraints.NotEmpty.message");
		if (binding.hasErrors())
			result = this.createEditModelAndView(er);
		else
			try {
				final EndorserRecord endorserRecord = this.endorserRecordService.reconstruct(er, binding);
				this.endorserRecordService.save(endorserRecord);
				result = new ModelAndView("redirect:/curriculum/instructor/display.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(er, "endorserRecord.commit.error");
			}
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final EndorserRecord endorserRecord, final BindingResult binding) {
		ModelAndView result;

		try {
			this.endorserRecordService.delete(endorserRecord);
			result = new ModelAndView("redirect:/curriculum/instructor/display.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(endorserRecord, "endorserRecord.commit.error");
		}
		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final EndorserRecord endorserRecord) {
		ModelAndView result;

		result = this.createEditModelAndView(endorserRecord, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final EndorserRecord endorserRecord, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("endorserRecord/edit");
		result.addObject("endorserRecord", endorserRecord);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "endorserRecord/instructor/edit.do");

		return result;

	}
}
