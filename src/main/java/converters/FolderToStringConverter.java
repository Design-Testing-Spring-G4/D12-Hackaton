
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Folder;

@Component
@Transactional
public class FolderToStringConverter implements Converter<Folder, String> {

	@Override
	public String convert(final Folder f) {
		String result;

		if (f == null)
			result = null;
		else
			result = String.valueOf(f.getId());

		return result;
	}
}
