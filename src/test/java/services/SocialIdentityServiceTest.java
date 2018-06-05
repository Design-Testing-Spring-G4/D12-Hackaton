
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
import domain.SocialIdentity;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class SocialIdentityServiceTest extends AbstractTest {

	//Service under test

	@Autowired
	private SocialIdentityService	socialIdentityService;


	//Test template

	protected void Template(final String username, final String network, final String profile, final String nick, final String nick2, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);

			//Creation
			final SocialIdentity socialIdentity = this.socialIdentityService.create();
			socialIdentity.setNetwork(network);
			socialIdentity.setNick(nick);
			socialIdentity.setPhoto("");
			socialIdentity.setProfile(profile);
			final SocialIdentity saved = this.socialIdentityService.save(socialIdentity);

			//Listing
			final Collection<SocialIdentity> cl = this.socialIdentityService.findAll();
			Assert.isTrue(cl.contains(saved));
			Assert.notNull(this.socialIdentityService.findOne(saved.getId()));

			//Edition
			saved.setNick(nick2);
			final SocialIdentity saved2 = this.socialIdentityService.save(saved);

			//Deletion
			this.socialIdentityService.delete(saved2);
			Assert.isNull(this.socialIdentityService.findOne(saved2.getId()));

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
				"user1", "testNetwork", "http://www.profile.com", "testNick", "editNick", null
			},

			//Test #02: Attempt to create a social identity without proper credentials. Expected false.
			{
				"null", "testNetwork", "http://www.profile.com", "testNick", "editNick", IllegalArgumentException.class
			},

			//Test #03: Attempt to create a social identity with blank fields. Expected false.
			{
				"user1", "", "", "", "editNick", ConstraintViolationException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.Template((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (Class<?>) testingData[i][5]);
	}
}
