
package services;

import java.util.Collection;
import java.util.Date;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.CreditCard;
import domain.Reservation;
import domain.Status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ReservationServiceTest extends AbstractTest {

	//Service under test

	@Autowired
	private ReservationService	reservationService;

	@Autowired
	private MailMessageService	mailMessageService;


	//Test template

	protected void Template(final String username, final String username2, final Integer adults, final Integer children, final Date startDate, final Date endDate, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);

			//Creation

			final Reservation reservation = this.reservationService.create(this.getEntityId("resort1"));
			reservation.setAdults(adults);
			reservation.setChildren(children);
			reservation.setComments("");
			reservation.setStartDate(startDate);
			reservation.setEndDate(endDate);
			final Reservation saved = this.reservationService.save(reservation);

			this.unauthenticate();
			this.authenticate(username2);

			//Listing
			final Collection<Reservation> cl = this.reservationService.findAll();
			Assert.isTrue(cl.contains(saved));
			Assert.notNull(this.reservationService.findOne(saved.getId()));

			//Manager confirmation of reservation
			saved.setStatus(Status.DUE);
			final Reservation saved2 = this.reservationService.saveInternal(saved);

			//Send a notification of reservation status change
			this.mailMessageService.reservationStatusNotification(this.getEntityId(username), this.getEntityId(username2));

			this.unauthenticate();
			this.authenticate(username);

			//User provides credit card to complete the transaction.
			final CreditCard cc = new CreditCard();
			cc.setHolder("John White");
			cc.setBrand("VISA");
			cc.setNumber("4716665991180313");
			cc.setExpMonth(12);
			cc.setExpYear(2020);
			cc.setCvv(123);
			saved2.setCreditCard(cc);
			final Reservation saved3 = this.reservationService.save(saved2);

			//Deletion
			this.reservationService.delete(saved3);
			Assert.isNull(this.reservationService.findOne(saved3.getId()));

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
				"user1", "manager1", 2, 1, new Date(1544605200000L), new Date(1544691600000L), null
			},

			//Test #02: Attempt to create a reservation with negative adults and children. Expected false.
			{
				"user1", "manager1", -2, -1, new Date(1544605200000L), new Date(1544691600000L), ConstraintViolationException.class
			},

			//Test #03: Attempt to create a reservation with invalid authorization. Expected false.
			{
				"null", "manager1", 2, 1, new Date(1544605200000L), new Date(1544691600000L), IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.Template((String) testingData[i][0], (String) testingData[i][1], (Integer) testingData[i][2], (Integer) testingData[i][3], (Date) testingData[i][4], (Date) testingData[i][5], (Class<?>) testingData[i][6]);
	}
}
