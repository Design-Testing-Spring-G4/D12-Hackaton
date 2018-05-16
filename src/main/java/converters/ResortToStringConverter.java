
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Resort;

@Component
@Transactional
public class ResortToStringConverter implements Converter<Resort, String> {

	@Override
	public String convert(final Resort r) {
		String result;

		if (r == null)
			result = null;
		else
			result = String.valueOf(r.getId());

		return result;
	}
}
