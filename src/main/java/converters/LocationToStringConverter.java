
package converters;

import java.net.URLEncoder;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Location;

@Component
@Transactional
public class LocationToStringConverter implements Converter<Location, String> {

	@Override
	public String convert(final Location l) {
		String result;
		final StringBuilder builder;

		if (l == null)
			result = null;
		else
			try {
				builder = new StringBuilder();
				builder.append(URLEncoder.encode(l.getLocation(), "UTF-8"));
				builder.append("|");
				builder.append(URLEncoder.encode(l.getGpsCoordinates(), "UTF-8"));
				builder.append("|");
				result = builder.toString();
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			}

		return result;
	}
}
