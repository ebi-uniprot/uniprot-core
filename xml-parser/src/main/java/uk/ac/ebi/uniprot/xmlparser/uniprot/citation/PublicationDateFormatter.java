package uk.ac.ebi.uniprot.xmlparser.uniprot.citation;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;


public enum PublicationDateFormatter {
	 YEAR(new SimpleDateFormatThreadSafe("yyyy", Locale.ENGLISH)),
	    YEAR_DIGIT_MONTH(new SimpleDateFormatThreadSafe("yyyy-MM", Locale.ENGLISH)),
	    THREE_LETTER_MONTH_YEAR(new SimpleDateFormatThreadSafe("MMM-yyyy", Locale.ENGLISH)),
	    DAY_DIGITMONTH_YEAR(new SimpleDateFormatThreadSafe("yyyy-MM-dd", Locale.ENGLISH)),
	    DAY_THREE_LETTER_MONTH_YEAR(new SimpleDateFormatThreadSafe("dd-MMM-yyyy", Locale.ENGLISH));

	    private SimpleDateFormatThreadSafe dateFormat;

	    private PublicationDateFormatter(SimpleDateFormatThreadSafe dateFormat) {
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
