
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.TagValueRepository;
import domain.TagValue;

@Component
@Transactional
public class StringToTagValueConverter implements Converter<String, TagValue> {

	@Autowired
	TagValueRepository	tagValueRepository;


	@Override
	public TagValue convert(final String s) {
		TagValue result;
		int id;

		try {
			if (StringUtils.isEmpty(s))
				result = null;
			else {
				id = Integer.valueOf(s);
				result = this.tagValueRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}
