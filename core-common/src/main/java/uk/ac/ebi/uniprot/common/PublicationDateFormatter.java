package uk.ac.ebi.uniprot.common;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Locale;

/**
 * Enumeration that contains a set of date formatters.
 * <p>
 * The enumeration is used not only to format the date correctly, but also to validate the date
 */
public enum PublicationDateFormatter {
    YEAR(DateTimeFormatter.ofPattern("yyyy", Locale.ENGLISH)),
    YEAR_DIGIT_MONTH(DateTimeFormatter.ofPattern("yyyy-MM", Locale.ENGLISH)),
    THREE_LETTER_MONTH_YEAR(DateTimeFormatter.ofPattern("MMM-yyyy", Locale.ENGLISH)),
    DAY_DIGITMONTH_YEAR(DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH)),
    DAY_THREE_LETTER_MONTH_YEAR(DateTimeFormatter.ofPattern("dd-MMM-yyyy", Locale.ENGLISH));

    private DateTimeFormatter dateFormat;

    PublicationDateFormatter(DateTimeFormatter dateFormat) {
        this.dateFormat = dateFormat;
    }

    public Date convertStringToDate(String publicationDate) throws ParseException {
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
}