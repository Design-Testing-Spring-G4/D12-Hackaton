
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ConfigurationRepository;
import domain.Configuration;

@Service
@Transactional
public class ConfigurationService {

	//Managed repository

	@Autowired
	private ConfigurationRepository	configurationRepository;

	//Supporting services

	@Autowired
	private ActorService			actorService;


	//Simple CRUD methods

	public Configuration create() {

		final Configuration c = new Configuration();
		c.setBanner("http://creek-tours.com/wp-content/uploads/Kenya-Tanzania-Family-Safari-banner.jpg");
		c.setCountryCode("+34");
		c.setVat(21.00);
		c.setSpamWords("viagra,cialis,sex,jes extender");
		c.setWelcomeEN("Tanzanika is an adventure company that makes your explorer's dreams true.");
		c.setWelcomeES("Tanzanika es la empresa de aventuras que hará tus sueños de explorador realidad.");

		return c;
	}

	public Collection<Configuration> findAll() {
		return this.configurationRepository.findAll();
	}

	public Configuration findOne(final int id) {
		Assert.notNull(id);

		return this.configurationRepository.findOne(id);
	}

	public Configuration save(final Configuration configuration) {
		Assert.notNull(configuration);

		//Assertion that the user modifying this configuration has the correct privilege.
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Assert.isTrue(authentication.getAuthorities().toArray()[0].toString().equals("ADMIN"));

		final Configuration saved = this.configurationRepository.save(configuration);

		this.actorService.isSpam(saved.getBanner());
		this.actorService.isSpam(saved.getCountryCode());
		this.actorService.isSpam(saved.getWelcomeEN());
		this.actorService.isSpam(saved.getWelcomeES());

		return saved;
	}

	public void delete(final Configuration configuration) {
		Assert.notNull(configuration);

		//Assertion that the user modifying this configuration has the correct privilege.
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Assert.isTrue(authentication.getAuthorities().toArray()[0].toString().equals("ADMIN"));

		this.configurationRepository.delete(configuration);
	}

	//Other methods

	public String[] getAllWords() {
		final String spamWords = this.findAll().iterator().next().getSpamWords();
		final String[] splitWords = spamWords.split("\\,");
		return splitWords;
	}
}
