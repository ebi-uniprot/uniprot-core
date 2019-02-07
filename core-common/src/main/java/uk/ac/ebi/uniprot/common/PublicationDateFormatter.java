package uk.ac.ebi.uniprot.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Enumeration that contains a set of date formatters.
 *
 * The enumeration is used not only to format the date correctly, but also to validate the date
 */
public enum PublicationDateFormatter {
    YEAR(new SimpleDateFormat("yyyy", Locale.ENGLISH)),
    YEAR_DIGIT_MONTH(new SimpleDateFormat("yyyy-MM", Locale.ENGLISH)),
    THREE_LETTER_MONTH_YEAR(new SimpleDateFormat("MMM-yyyy", Locale.ENGLISH)),
    DAY_DIGITMONTH_YEAR(new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)),
    DAY_THREE_LETTER_MONTH_YEAR(new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH));

    private SimpleDateFormat dateFormat;

    PublicationDateFormatter(SimpleDateFormat dateFormat) {
        this.dateFormat = dateFormat;
        this.dateFormat.setLenient(false);
    }

    public Date convertStringToDate(String publicationDate) throws ParseException {
        try{
            return dateFormat.parse(publicationDate);
        }catch (ParseException e){
            System.err.println("failed to parse: " + publicationDate);
            throw e;
        }
    }

    public String convertDateToString(Date pubDate) {
        return dateFormat.format(pubDate);
    }

    public boolean isValidDate(String date) {
        try {
            dateFormat.parse(date);
        } catch (ParseException e) {
            return false;
        }

        return true;
    }
}
