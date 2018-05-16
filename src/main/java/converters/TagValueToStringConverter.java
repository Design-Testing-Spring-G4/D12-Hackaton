
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.TagValue;

@Component
@Transactional
public class TagValueToStringConverter implements Converter<TagValue, String> {

	@Override
	public String convert(final TagValue t) {
		String result;

		if (t == null)
			result = null;
		else
			result = String.valueOf(t.getId());

		return result;
	}
}
