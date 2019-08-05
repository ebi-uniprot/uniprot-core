package org.uniprot.core.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.Date;
import java.util.Locale;

/**
 * Enumeration that contains a set of date formatters.
 * <p>
 * The enumeration is used not only to format the date correctly, but also to validate the date
 */
public enum PublicationDateFormatter {
    YEAR(formatFor("yyyy")
                 .parseDefaulting(ChronoField.MONTH_OF_YEAR, 1)
                 .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
                 .toFormatter(Locale.ENGLISH)),
    YEAR_DIGIT_MONTH(formatFor("yyyy-MM")
                             .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
                             .toFormatter(Locale.ENGLISH)),
    THREE_LETTER_MONTH_YEAR(formatFor("MMM-yyyy")
                                    .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
                                    .toFormatter(Locale.ENGLISH)),
    DAY_DIGITMONTH_YEAR(formatFor("yyyy-MM-dd")
                                .toFormatter(Locale.ENGLISH)),
    DAY_THREE_LETTER_MONTH_YEAR(formatFor("dd-MMM-yyyy")
                                        .toFormatter(Locale.ENGLISH));

    private DateTimeFormatter dateFormat;

    PublicationDateFormatter(DateTimeFormatter dateFormat) {
        this.dateFormat = dateFormat;
    }

    public Date convertStringToDate(String publicationDate) {
        try {
            LocalDate localDate = LocalDate.parse(publicationDate, dateFormat);
            return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        } catch (DateTimeParseException e) {
            System.err.println("failed to parse: " + publicationDate);
            throw e;
        }
    }

    public boolean isValidDate(String date) {
        try {
            LocalDate.parse(date, dateFormat);
        } catch (DateTimeParseException e) {
            return false;
        }

        return true;
    }

    private static DateTimeFormatterBuilder formatFor(String pattern) {
        return new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern(pattern);
    }
}