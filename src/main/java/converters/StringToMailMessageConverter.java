
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.MailMessageRepository;
import domain.MailMessage;

@Component
@Transactional
public class StringToMailMessageConverter implements Converter<String, MailMessage> {

	@Autowired
	MailMessageRepository	mailMessageRepository;


	@Override
	public MailMessage convert(final String s) {
		MailMessage result;
		int id;

		try {
			if (StringUtils.isEmpty(s))
				result = null;
			else {
				id = Integer.valueOf(s);
				result = this.mailMessageRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}
