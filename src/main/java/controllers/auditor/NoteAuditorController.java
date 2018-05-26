
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

import services.ActivityService;
import services.ActorService;
import services.LessonService;
import services.NoteService;
import controllers.AbstractController;
import domain.Activity;
import domain.Auditor;
import domain.Lesson;
import domain.Note;

@Controller
@RequestMapping("note/auditor")
public class NoteAuditorController extends AbstractController {

	//Services

	@Autowired
	private NoteService		noteService;

	@Autowired
	private ActorService	actorService;

	@Autowired
	private ActivityService	activityService;

	@Autowired
	private LessonService	lessonService;


	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Auditor auditor = (Auditor) this.actorService.findByPrincipal();
		final Collection<Note> notes = auditor.getNotes();

		result = new ModelAndView("note/list");
		result.addObject("notes", notes);
		result.addObject("requestURI", "note/auditor/list.do");

		return result;
	}


	//Creation

	private Integer	entId;
	private Integer	saveId;


	public Integer getEntId() {
		return this.entId;
	}

	public void setEntId(final Integer entId) {
		this.entId = entId;
	}

	public Integer getSaveId() {
		return this.saveId;
	}

	public void setSaveId(final Integer saveId) {
		this.saveId = saveId;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int varId, final int varId2) {
		final ModelAndView result;
		final Note note = this.noteService.create();
		Assert.notNull(note);
		result = this.createEditModelAndView(note);
		this.setEntId(varId);
		this.setSaveId(varId2);

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
				final Note saved = this.noteService.save(note);
				if (this.getSaveId() == 0) {
					final Activity a = this.activityService.findOne(this.getEntId());
					a.getNotes().add(saved);
					this.activityService.saveInternal(a);
				} else {
					final Lesson l = this.lessonService.findOne(this.getEntId());
					l.getNotes().add(saved);
					this.lessonService.saveInternal(l);
				}
				result = new ModelAndView("redirect:/note/auditor/list.do");
			} catch (final Throwable oops) {
				oops.printStackTrace();
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
		result.addObject("requestURI", "note/auditor/edit.do");

		return result;
	}
}
