
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Competition;

@Component
@Transactional
public class CompetitionToStringConverter implements Converter<Competition, String> {

	@Override
	public String convert(final Competition c) {
		String result;

		if (c == null)
			result = null;
		else
			result = String.valueOf(c.getId());

		return result;
	}
}
