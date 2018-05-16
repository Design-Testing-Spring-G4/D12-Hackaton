
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.ParticipationRepository;
import domain.Participation;

@Component
@Transactional
public class StringToParticipationConverter implements Converter<String, Participation> {

	@Autowired
	ParticipationRepository	participationRepository;


	@Override
	public Participation convert(final String s) {
		Participation result;
		int id;

		try {
			if (StringUtils.isEmpty(s))
				result = null;
			else {
				id = Integer.valueOf(s);
				result = this.participationRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}
