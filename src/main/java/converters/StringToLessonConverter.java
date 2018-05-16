
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.LessonRepository;
import domain.Lesson;

@Component
@Transactional
public class StringToLessonConverter implements Converter<String, Lesson> {

	@Autowired
	LessonRepository	lessonRepository;


	@Override
	public Lesson convert(final String s) {
		Lesson result;
		int id;

		try {
			if (StringUtils.isEmpty(s))
				result = null;
			else {
				id = Integer.valueOf(s);
				result = this.lessonRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}
