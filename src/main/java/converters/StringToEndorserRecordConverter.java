
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.EndorserRecordRepository;
import domain.EndorserRecord;

@Component
@Transactional
public class StringToEndorserRecordConverter implements Converter<String, EndorserRecord> {

	@Autowired
	EndorserRecordRepository	endorserRecordRepository;


	@Override
	public EndorserRecord convert(final String s) {
		EndorserRecord result;
		int id;

		try {
			if (StringUtils.isEmpty(s))
				result = null;
			else {
				id = Integer.valueOf(s);
				result = this.endorserRecordRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}
