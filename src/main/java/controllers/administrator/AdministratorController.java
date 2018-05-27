/*
 * AdministratorController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.administrator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActivityService;
import services.CompetitionService;
import services.InstructorService;
import services.LegalTextService;
import services.LessonService;
import services.ManagerService;
import services.ReservationService;
import services.ResortService;
import services.SponsorService;
import services.UserService;
import controllers.AbstractController;
import domain.Competition;
import domain.LegalTextTable;
import domain.Resort;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	//Services

	@Autowired
	private LegalTextService	legalTextService;

	@Autowired
	private ManagerService		managerService;

	@Autowired
	private ResortService		resortService;

	@Autowired
	private ReservationService	reservationService;

	@Autowired
	private ActivityService		activityService;

	@Autowired
	private LessonService		lessonService;

	@Autowired
	private InstructorService	instructorService;

	@Autowired
	private UserService			userService;

	@Autowired
	private SponsorService		sponsorService;

	@Autowired
	private CompetitionService	competitionService;


	//Dashboard

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView dashboard() {
		ModelAndView result;

		final Collection<String> resortsWithAboveAverageReservations = new ArrayList<String>();
		for (final Resort r : this.resortService.resortsWithAboveAverageReservations())
			resortsWithAboveAverageReservations.add(r.getName());

		final Collection<String> topFiveCompetitionsPrizePool = new ArrayList<String>();
		for (final Competition c : this.competitionService.topFiveCompetitionsPrizePool())
			topFiveCompetitionsPrizePool.add(c.getTitle());

		final Collection<String> topFiveCompetitionsMaxParticipants = new ArrayList<String>();
		for (final Competition d : this.competitionService.topFiveCompetitionsMaxParticipants())
			topFiveCompetitionsMaxParticipants.add(d.getTitle());

		final Map<String, Long> legalTextTable = new HashMap<String, Long>();
		for (final LegalTextTable ltt : this.legalTextService.legalTextTable())
			legalTextTable.put(ltt.getText().getTitle(), ltt.getCount());

		result = new ModelAndView("administrator/dashboard");

		result.addObject("avgMinMaxStddevReservationsPerResort", Arrays.toString(this.resortService.avgMinMaxStddevReservationsPerResort()));
		result.addObject("avgMinMaxStddevResortsPerManager", Arrays.toString(this.managerService.avgMinMaxStddevResortsPerManager()));
		result.addObject("avgMinMaxStddevPricePerReservation", Arrays.toString(this.reservationService.avgMinMaxStddevPricePerReservation()));
		result.addObject("avgMinMaxStddevActivitiesPerResort", Arrays.toString(this.resortService.avgMinMaxStddevActivitiesPerResort()));
		result.addObject("ratioEntertainmentActivities", this.activityService.ratioEntertainmentActivities());
		result.addObject("ratioSportActivitiesWithInstructor", this.activityService.ratioSportActivitiesWithInstructor());
		result.addObject("ratioSportActivitiesWithoutInstructor", this.activityService.ratioSportActivitiesWithoutInstructor());
		result.addObject("ratioSportActivities", this.activityService.ratioSportActivities());
		result.addObject("ratioTourismActivities", this.activityService.ratioTourismActivities());
		result.addObject("ratioPendingReservations", this.reservationService.ratioPendingReservations());
		result.addObject("ratioDueReservations", this.reservationService.ratioDueReservations());
		result.addObject("ratioAcceptedReservations", this.reservationService.ratioAcceptedReservations());
		result.addObject("ratioRejectedReservations", this.reservationService.ratioRejectedReservations());
		result.addObject("resortsWithAboveAverageReservations", resortsWithAboveAverageReservations);
		result.addObject("ratioFullResorts", this.resortService.ratioFullResorts());
		result.addObject("legalTextTable", legalTextTable);
		result.addObject("minMaxAvgStddevNotesPerActivity", Arrays.toString(this.activityService.minMaxAvgStddevNotesPerActivity()));
		result.addObject("minMaxAvgStddevNotesPerLesson", Arrays.toString(this.lessonService.minMaxAvgStddevNotesPerLesson()));
		result.addObject("minMaxAvgStddevAuditsPerResort", Arrays.toString(this.resortService.minMaxAvgStddevAuditsPerResort()));
		result.addObject("ratioResortsWithAudit", this.resortService.ratioResortsWithAudit());
		result.addObject("ratioInstructorsWithCurriculum", this.instructorService.ratioInstructorsWithCurriculum());
		result.addObject("ratioInstructorsEndorsed", this.instructorService.ratioInstructorsEndorsed());
		result.addObject("ratioSuspiciousInstructors", this.instructorService.ratioSuspiciousInstructors());
		result.addObject("ratioSuspiciousManagers", this.managerService.ratioSuspiciousManagers());
		result.addObject("ratioSuspiciousUsers", this.userService.ratioSuspiciousUsers());
		result.addObject("minMaxAvgStdddevCompetitionsPerSponsor", Arrays.toString(this.sponsorService.minMaxAvgStdddevCompetitionsPerSponsor()));
		result.addObject("topFiveCompetitionsPrizePool", topFiveCompetitionsPrizePool);
		result.addObject("topFiveCompetitionsMaxParticipants", topFiveCompetitionsMaxParticipants);

		result.addObject("requestURI", "administrator/dashboard.do");

		return result;
	}
}
