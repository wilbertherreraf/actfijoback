package gob.gamo.activosf.app.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static java.lang.String.format;
import java.util.Locale;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SlugifyTest {
    private static final String ASSERT_EQUALS_MESSAGE_FORMAT = "[%s] \"%s\" equals \"%s\"";
    private static final Locale DEFAULT_LOCALE = Locale.ENGLISH;

    @Test
    /* default */ void givenStringWhenUnderscoreIsUsedThenSlugify() {
        final Slugify slugify = Slugify.builder()
                .lowerCase(false)
                .underscoreSeparator(true)
                .transliterator(true)
                .locale(DEFAULT_LOCALE)
                .build();

        final String expected = "Foo_bar ä ó ó";
        final String actual = slugify.slugify("123");
        log.info("resp {} {}", expected, actual);
        assertEquals(expected, actual, format(ASSERT_EQUALS_MESSAGE_FORMAT, DEFAULT_LOCALE, expected, actual));
    }
}
