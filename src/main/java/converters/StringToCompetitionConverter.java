
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.CompetitionRepository;
import domain.Competition;

@Component
@Transactional
public class StringToCompetitionConverter implements Converter<String, Competition> {

	@Autowired
	CompetitionRepository	competitionRepository;


	@Override
	public Competition convert(final String s) {
		Competition result;
		int id;

		try {
			if (StringUtils.isEmpty(s))
				result = null;
			else {
				id = Integer.valueOf(s);
				result = this.competitionRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}
