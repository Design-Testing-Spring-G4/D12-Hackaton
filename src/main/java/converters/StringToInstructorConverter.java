
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.InstructorRepository;
import domain.Instructor;

@Component
@Transactional
public class StringToInstructorConverter implements Converter<String, Instructor> {

	@Autowired
	InstructorRepository	instructorRepository;


	@Override
	public Instructor convert(final String s) {
		Instructor result;
		int id;

		try {
			if (StringUtils.isEmpty(s))
				result = null;
			else {
				id = Integer.valueOf(s);
				result = this.instructorRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}
