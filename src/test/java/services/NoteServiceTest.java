
package services;

import java.util.Collection;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Note;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class NoteServiceTest extends AbstractTest {

	//Service under test

	@Autowired
	private NoteService	noteService;


	//Test template

	protected void Template(final String username, final String username2, final String remark, final String reply, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);

			//Creation
			final Note note = this.noteService.create();
			note.setRemark(remark);
			final Note saved = this.noteService.save(note);

			this.unauthenticate();
			this.authenticate(username2);

			//Listing
			final Collection<Note> cl = this.noteService.findAll();
			Assert.isTrue(cl.contains(saved));
			Assert.notNull(this.noteService.findOne(saved.getId()));

			//Edition
			saved.setReply(reply);
			final Note saved2 = this.noteService.saveInternal(saved);

			this.unauthenticate();
			this.authenticate(username);

			//Deletion
			this.noteService.delete(saved2);
			Assert.isNull(this.noteService.findOne(saved2.getId()));

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();

		}

		this.checkExceptions(expected, caught);
	}

	//Driver for multiple tests under the same template.

	@Test
	public void Driver() {

		final Object testingData[][] = {

			//Test #01: Correct execution of test. Expected true.
			{
				"auditor1", "manager1", "testRemark", "testReply", null
			},

			//Test #02: Attempt to save a note with a null reply. Expected false.
			{
				"auditor1", "manager1", "<h1>hack</h1>", "testReply", ConstraintViolationException.class
			},

			//Test #03: Attempt to create a note without remark. Expected false.
			{
				"auditor1", "manager1", "", "testReply", ConstraintViolationException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.Template((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
	}
}
