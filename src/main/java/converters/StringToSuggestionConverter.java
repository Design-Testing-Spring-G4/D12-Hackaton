
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.SuggestionRepository;
import domain.Suggestion;

@Component
@Transactional
public class StringToSuggestionConverter implements Converter<String, Suggestion> {

	@Autowired
	SuggestionRepository	suggestionRepository;


	@Override
	public Suggestion convert(final String s) {
		Suggestion result;
		int id;

		try {
			if (StringUtils.isEmpty(s))
				result = null;
			else {
				id = Integer.valueOf(s);
				result = this.suggestionRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}
