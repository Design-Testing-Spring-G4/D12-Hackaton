/*
 * WelcomeController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CompetitionService;
import services.ConfigurationService;
import domain.Competition;
import domain.Configuration;

@Controller
@RequestMapping("/welcome")
public class WelcomeController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public WelcomeController() {
		super();
	}


	// Supporting services

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private CompetitionService		competitionService;


	// Index ------------------------------------------------------------------		

	@RequestMapping(value = "/index")
	public ModelAndView index(@RequestParam(required = false, defaultValue = "John Doe") final String name) {
		ModelAndView result;
		SimpleDateFormat formatter;
		String moment;

		formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		moment = formatter.format(new Date());

		final Configuration configuration = this.configurationService.findAll().iterator().next();
		final Collection<Competition> competitions = this.competitionService.competitionsWithBanner();
		final Random rnd = new Random();
		final int i = rnd.nextInt(competitions.size());
		final Competition competition = (Competition) competitions.toArray()[i];

		result = new ModelAndView("welcome/index");
		result.addObject("name", name);
		result.addObject("moment", moment);
		result.addObject("competition", competition);
		result.addObject("configuration", configuration);

		return result;
	}

	@RequestMapping(value = "/cookies")
	public ModelAndView cookies() {
		ModelAndView result;

		result = new ModelAndView("legislation/cookies");

		return result;
	}

	@RequestMapping(value = "/terms")
	public ModelAndView terms() {
		ModelAndView result;

		result = new ModelAndView("legislation/terms");

		return result;
	}

	@RequestMapping(value = "/contact")
	public ModelAndView contact() {
		ModelAndView result;

		result = new ModelAndView("legislation/contact");

		return result;
	}
}
