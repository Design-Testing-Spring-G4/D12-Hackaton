
package converters;

import java.net.URLDecoder;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Location;

@Component
@Transactional
public class StringToLocationConverter implements Converter<String, Location> {

	@Override
	public Location convert(final String s) {
		Location result;
		String parts[];

		if (s == null || s == "")
			result = null;
		else
			try {
				parts = s.split("\\|");
				result = new Location();
				result.setName(URLDecoder.decode(parts[0], "UTF-8"));
				result.setGpsCoordinates(URLDecoder.decode(parts[1], "UTF-8"));
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			}

		return result;
	}
}
