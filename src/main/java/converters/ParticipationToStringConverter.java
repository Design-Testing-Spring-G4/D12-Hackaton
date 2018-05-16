
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Participation;

@Component
@Transactional
public class ParticipationToStringConverter implements Converter<Participation, String> {

	@Override
	public String convert(final Participation p) {
		String result;

		if (p == null)
			result = null;
		else
			result = String.valueOf(p.getId());

		return result;
	}
}
