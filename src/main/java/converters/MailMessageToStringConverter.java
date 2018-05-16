
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.MailMessage;

@Component
@Transactional
public class MailMessageToStringConverter implements Converter<MailMessage, String> {

	@Override
	public String convert(final MailMessage m) {
		String result;

		if (m == null)
			result = null;
		else
			result = String.valueOf(m.getId());

		return result;
	}
}
