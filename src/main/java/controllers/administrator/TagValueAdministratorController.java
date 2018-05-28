
package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ResortService;
import services.TagService;
import services.TagValueService;
import controllers.AbstractController;
import domain.Resort;
import domain.Tag;
import domain.TagValue;

@Controller
@RequestMapping("tagValue/administrator")
public class TagValueAdministratorController extends AbstractController {

	//Services

	@Autowired
	private TagValueService	tagValueService;

	@Autowired
	private TagService		tagService;

	@Autowired
	private ResortService	resortService;

	//Ancillary attributes

	private Tag				currentTag;


	public Tag getCurrentTag() {
		return this.currentTag;
	}

	public void setCurrentTag(final Tag currentTag) {
		this.currentTag = currentTag;
	}

	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int varId) {
		final ModelAndView result;
		final Tag tag = this.tagService.findOne(varId);
		this.setCurrentTag(tag);
		final Collection<TagValue> tagValues = tag.getTagValues();

		result = new ModelAndView("tagValue/list");
		result.addObject("tagValues", tagValues);
		result.addObject("requestURI", "tagValue/administrator/list.do");

		return result;
	}

	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		TagValue tagValue;
		tagValue = this.tagValueService.create(this.getCurrentTag().getId());
		result = this.createEditModelAndView(tagValue, null);

		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int varId) {
		final ModelAndView result;
		final TagValue tagValue = this.tagValueService.findOne(varId);
		result = this.createEditModelAndView(tagValue);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final TagValue tv, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(tv);
		else
			try {
				final TagValue tagValue = this.tagValueService.reconstruct(tv, this.getCurrentTag().getId(), binding);
				this.tagValueService.save(tagValue);
				result = new ModelAndView("redirect:/tag/administrator/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(tv, "tagValue.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final TagValue tagValue, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(tagValue);
		else
			try {
				final TagValue tv = this.tagValueService.findOne(tagValue.getId());
				this.tagValueService.delete(tv);
				result = new ModelAndView("redirect:/tag/administrator/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(tagValue, "tagValue.commit.error");
			}
		return result;
	}


	//Managing

	private Integer	resId;


	public Integer getResId() {
		return this.resId;
	}

	public void setResId(final Integer resId) {
		this.resId = resId;
	}

	@RequestMapping(value = "/manage", method = RequestMethod.GET)
	public ModelAndView manage(@RequestParam final int varId) {
		final ModelAndView result;
		final Collection<TagValue> tags = this.tagValueService.findAll();
		this.setResId(varId);
		final Resort resort = this.resortService.findOne(varId);
		final Collection<TagValue> values = resort.getTags();

		result = new ModelAndView("tagValue/manage");
		result.addObject("tags", tags);
		result.addObject("values", values);
		result.addObject("requestURI", "tagValue/administrator/manage.do");

		return result;
	}

	//Setting the resort's tags

	@RequestMapping(value = "/set", method = RequestMethod.GET)
	public ModelAndView set(@RequestParam final int varId) {
		ModelAndView result;
		final TagValue tag = this.tagValueService.findOne(varId);
		final int id = this.getResId();
		final Resort resort = this.resortService.findOne(id);

		try {
			final Collection<Resort> resorts = tag.getResorts();
			if (!resorts.contains(resort)) {
				resorts.add(resort);
				tag.setResorts(resorts);
				final TagValue saved = this.tagValueService.saveInternal(tag);
				resort.getTags().add(saved);
				this.resortService.saveInternal(resort);
			} else {
				resorts.remove(resort);
				tag.setResorts(resorts);
				final TagValue saved = this.tagValueService.saveInternal(tag);
				resort.getTags().remove(saved);
				this.resortService.saveInternal(resort);
			}

			result = this.manage(id);
		} catch (final Throwable oops) {
			result = this.manage(id);
		}

		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final TagValue tagValue) {
		ModelAndView result;

		result = this.createEditModelAndView(tagValue, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final TagValue tagValue, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("tagValue/edit");
		result.addObject("tagValue", tagValue);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "tagValue/administrator/edit.do");

		return result;

	}
}
