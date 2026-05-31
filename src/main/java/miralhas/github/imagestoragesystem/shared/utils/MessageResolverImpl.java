package miralhas.github.imagestoragesystem.shared.utils;

import lombok.RequiredArgsConstructor;
import miralhas.github.imagestoragesystem.shared.interfaces.MessageResolver;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageResolverImpl implements MessageResolver {
	private final MessageSource messageSource;

	@Override
	public String get(String code, Object... args) {
		return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
	}

	@Override
	public String get(String code) {
		return messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
	}
}
