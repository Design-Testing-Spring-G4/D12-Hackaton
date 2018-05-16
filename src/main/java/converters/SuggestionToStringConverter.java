
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Suggestion;

@Component
@Transactional
public class SuggestionToStringConverter implements Converter<Suggestion, String> {

	@Override
	public String convert(final Suggestion s) {
		String result;

		if (s == null)
			result = null;
		else
			result = String.valueOf(s.getId());

		return result;
	}
}
