
package controllers.manager;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActivityService;
import services.NoteService;
import controllers.AbstractController;
import domain.Activity;
import domain.Note;

@Controller
@RequestMapping("note/manager")
public class NoteManagerController extends AbstractController {

	//Services

	@Autowired
	private NoteService		noteService;

	@Autowired
	private ActivityService	activityService;


	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int varId) {
		final ModelAndView result;
		final Activity activity = this.activityService.findOne(varId);
		final Collection<Note> notes = activity.getNotes();

		result = new ModelAndView("note/list");
		result.addObject("notes", notes);
		result.addObject("requestURI", "note/manager/list.do");

		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int varId) {
		final ModelAndView result;
		final Note note = this.noteService.findOne(varId);
		Assert.notNull(note);
		result = this.createEditModelAndView(note);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Note n, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(n);
		else
			try {
				final Note note = this.noteService.reconstruct(n, binding);
				this.noteService.saveInternal(note);
				result = new ModelAndView("redirect:/activity/manager/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(n, "note.commit.error");
			}
		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final Note note) {
		ModelAndView result;

		result = this.createEditModelAndView(note, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Note note, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("note/edit");
		result.addObject("note", note);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "note/manager/edit.do");

		return result;
	}
}
