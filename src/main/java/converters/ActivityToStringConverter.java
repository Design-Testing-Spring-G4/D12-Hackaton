
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Activity;

@Component
@Transactional
public class ActivityToStringConverter implements Converter<Activity, String> {

	@Override
	public String convert(final Activity a) {
		String result;

		if (a == null)
			result = null;
		else
			result = String.valueOf(a.getId());

		return result;
	}
}
