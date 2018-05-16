
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
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

		return this.configurationRepository.save(configuration);
	}

	public void delete(final Configuration configuration) {
		Assert.notNull(configuration);

		this.configurationRepository.delete(configuration);
	}

	//Other methods

	public String[] getAllWords() {
		final String spamWords = this.findAll().iterator().next().getSpamWords();
		final String[] splitWords = spamWords.split("\\,");
		return splitWords;
	}
}
