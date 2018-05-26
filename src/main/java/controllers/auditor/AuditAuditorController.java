
package controllers.auditor;

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
import services.AuditService;
import controllers.AbstractController;
import domain.Audit;
import domain.Auditor;

@Controller
@RequestMapping("audit/auditor")
public class AuditAuditorController extends AbstractController {

	//Services

	@Autowired
	private AuditService	auditService;

	@Autowired
	private ActorService	actorService;


	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Audit> audits = ((Auditor) this.actorService.findByPrincipal()).getAudits();

		result = new ModelAndView("audit/list");
		result.addObject("audits", audits);
		result.addObject("requestURI", "audit/auditor/list.do");

		return result;
	}

	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int varId) {
		final ModelAndView result;
		final Audit audit = this.auditService.create(varId);
		result = this.createEditModelAndView(audit);

		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int varId) {
		final ModelAndView result;
		final Audit audit = this.auditService.findOne(varId);
		Assert.notNull(audit);
		if (audit.isFinalMode())
			result = new ModelAndView("redirect:/audit/auditor/list.do");
		else
			result = this.createEditModelAndView(audit);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Audit a, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(a);
		else
			try {
				final Audit audit = this.auditService.reconstruct(a, binding);
				this.auditService.save(audit);
				result = new ModelAndView("redirect:/audit/auditor/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(a, "audit.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Audit audit, final BindingResult binding) {
		ModelAndView result;

		if (audit.isFinalMode())
			result = this.createEditModelAndView(audit, "audit.delete.error");
		else if (binding.hasErrors())
			result = this.createEditModelAndView(audit);
		else
			try {
				this.auditService.delete(audit);
				result = new ModelAndView("redirect:/audit/auditor/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(audit, "audit.commit.error");
			}

		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final Audit audit) {
		ModelAndView result;

		result = this.createEditModelAndView(audit, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Audit audit, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("audit/edit");
		result.addObject("audit", audit);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "audit/auditor/edit.do");

		return result;

	}
}
