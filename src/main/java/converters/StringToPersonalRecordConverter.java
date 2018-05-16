
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.PersonalRecordRepository;
import domain.PersonalRecord;

@Component
@Transactional
public class StringToPersonalRecordConverter implements Converter<String, PersonalRecord> {

	@Autowired
	PersonalRecordRepository	personalRecordRepository;


	@Override
	public PersonalRecord convert(final String s) {
		PersonalRecord result;
		int id;

		try {
			if (StringUtils.isEmpty(s))
				result = null;
			else {
				id = Integer.valueOf(s);
				result = this.personalRecordRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}
