
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Instructor;

@Component
@Transactional
public class InstructorToStringConverter implements Converter<Instructor, String> {

	@Override
	public String convert(final Instructor i) {
		String result;

		if (i == null)
			result = null;
		else
			result = String.valueOf(i.getId());

		return result;
	}
}
