
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

	//Supporting services

	@Autowired
	private ActorService			actorService;


	//Simple CRUD methods

	public Configuration create() {

		final Configuration c = new Configuration();
		c.setBanner("https://www.publicdomainpictures.net/pictures/20000/velka/june-lake-valley-california-usa-26291294194420kDo.jpg");
		c.setCountryCode("+34");
		c.setVat(21.00);
		c.setSpamWords("viagra,cialis,sex,love");
		c.setWelcomeEN("Welcome to the place to plan your ideal holidays.");
		c.setWelcomeES("Bienvenido al lugar donde planear tus vacaciones ideales.");

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

		final Configuration saved = this.configurationRepository.save(configuration);

		this.actorService.isSpam(saved.getBanner());
		this.actorService.isSpam(saved.getCountryCode());
		this.actorService.isSpam(saved.getWelcomeEN());
		this.actorService.isSpam(saved.getWelcomeES());

		return saved;
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
