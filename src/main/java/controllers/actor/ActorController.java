
package controllers.actor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import controllers.AbstractController;
import domain.Actor;

@Controller
@RequestMapping("actor")
public class ActorController extends AbstractController {

	//Services

	@Autowired
	private ActorService	actorService;


	//Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int varId) {
		final ModelAndView result;
		final Actor actor = this.actorService.findOne(varId);

		result = new ModelAndView("actor/display");
		result.addObject("actor", actor);
		result.addObject("requestURI", "actor/display.do");

		return result;
	}
}
