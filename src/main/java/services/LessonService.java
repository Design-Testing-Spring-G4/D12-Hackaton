
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.LessonRepository;
import domain.Instructor;
import domain.Lesson;
import domain.Note;

@Service
@Transactional
public class LessonService {

	//Managed repository

	@Autowired
	private LessonRepository	lessonRepository;

	//Supporting services

	@Autowired
	private ActorService		actorService;

	@Autowired
	private Validator			validator;


	//Simple CRUD methods

	public Lesson create() {

		final Lesson lesson = new Lesson();

		final Instructor instructor = (Instructor) this.actorService.findByPrincipal();
		lesson.setInstructor(instructor);
		lesson.setNotes(new ArrayList<Note>());

		return lesson;
	}

	public Lesson findOne(final int id) {
		Assert.notNull(id);

		return this.lessonRepository.findOne(id);
	}

	public Collection<Lesson> findAll() {
		return this.lessonRepository.findAll();
	}

	public Lesson save(final Lesson lesson) {
		Assert.notNull(lesson);

		//Assertion that the user modifying this miscellaneous record has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == lesson.getInstructor().getId());

		final Lesson saved = this.lessonRepository.save(lesson);

		this.actorService.isSpam(saved.getDescription());
		this.actorService.isSpam(saved.getName());

		return saved;
	}

	//Save for internal operations such as a cross-authorized requests.
	public Lesson saveInternal(final Lesson lesson) {
		Assert.notNull(lesson);

		//Assertion that the user modifying this lesson has the correct privilege.
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Assert.isTrue(authentication.getAuthorities().toArray()[0].toString().equals("AUDITOR"));

		final Lesson saved = this.lessonRepository.save(lesson);
		return saved;
	}

	public void delete(final Lesson lesson) {
		Assert.notNull(lesson);

		//Assertion that the user deleting this lesson has the correct privilege.
		final Lesson validator = this.findOne(lesson.getId());
		Assert.isTrue(this.actorService.findByPrincipal().getId() == validator.getInstructor().getId());

		this.lessonRepository.delete(lesson);
	}

	//Other methods

	public Lesson reconstruct(final Lesson lesson, final BindingResult binding) {
		Lesson result;

		if (lesson.getId() == 0) {
			result = this.create();
			result.setDescription(lesson.getDescription());
			result.setName(lesson.getName());
			result.setPrice(lesson.getPrice());
			result.setSchedule(lesson.getSchedule());
		} else {
			result = this.findOne(lesson.getId());
			result.setDescription(lesson.getDescription());
			result.setName(lesson.getName());
			result.setPrice(lesson.getPrice());
			result.setSchedule(lesson.getSchedule());
		}

		this.validator.validate(result, binding);

		return result;
	}

	public Double[] minMaxAvgStddevNotesPerLesson() {
		return this.lessonRepository.minMaxAvgStddevNotesPerLesson();
	}

	public Collection<Lesson> lessonsByInstructor(final int id) {
		return this.lessonRepository.lessonsByInstructor(id);
	}
}
