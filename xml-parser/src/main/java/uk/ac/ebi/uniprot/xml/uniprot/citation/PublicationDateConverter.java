package uk.ac.ebi.uniprot.xml.uniprot.citation;

import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.uniprot.core.citation.PublicationDate;
import org.uniprot.core.citation.builder.PublicationDateBuilder;

import uk.ac.ebi.uniprot.xml.Converter;

import java.text.ParseException;
import java.util.Date;
import java.util.regex.Pattern;

public class PublicationDateConverter implements Converter<String, PublicationDate> {
	private static final Logger logger = LoggerFactory.getLogger(PublicationDateConverter.class);

	private static final Pattern HYPHEN = Pattern.compile("-");

	@Override
	public PublicationDate fromXml(String xmlDate) {
		if (!Strings.isNullOrEmpty(xmlDate)) {
			try {
				if (PublicationDateFormatter.DAY_DIGITMONTH_YEAR.isValidDate(xmlDate)) {
					Date date = PublicationDateFormatter.DAY_DIGITMONTH_YEAR.convertStringToDate(xmlDate);
					String newDate = PublicationDateFormatter.DAY_THREE_LETTER_MONTH_YEAR.convertDateToString(date);
					return new PublicationDateBuilder(newDate.toUpperCase()).build();
				} else if (PublicationDateFormatter.YEAR_DIGIT_MONTH.isValidDate(xmlDate)) {
					Date date = PublicationDateFormatter.YEAR_DIGIT_MONTH.convertStringToDate(xmlDate);
					String newDate = PublicationDateFormatter.THREE_LETTER_MONTH_YEAR.convertDateToString(date);
					return new PublicationDateBuilder(newDate.toUpperCase()).build();
				}
			} catch (ParseException e) {
				logger.error("Error in citation. Origin of error [" + e.getMessage() + ']');
			}

			return new PublicationDateBuilder(xmlDate.toUpperCase()).build();
		} else {
			return new PublicationDateBuilder("0").build();
		}
	}

	@Override
	public String toXml(PublicationDate uniObj) {
		
		if ( ( uniObj !=null) && !Strings.isNullOrEmpty(uniObj.getValue())) {
			String pubDate = uniObj.getValue();

			try {
				if (!"0".equals(pubDate.trim())) {
					int numOfDateValues = HYPHEN.split(pubDate).length;

					if (numOfDateValues == 3) {
						if (PublicationDateFormatter.DAY_THREE_LETTER_MONTH_YEAR.isValidDate(pubDate)) {
							Date date = PublicationDateFormatter.DAY_THREE_LETTER_MONTH_YEAR
									.convertStringToDate(pubDate);
							return PublicationDateFormatter.DAY_DIGITMONTH_YEAR.convertDateToString(date);
						} else if (PublicationDateFormatter.DAY_DIGITMONTH_YEAR.isValidDate(pubDate)) {
							Date date = PublicationDateFormatter.DAY_DIGITMONTH_YEAR.convertStringToDate(pubDate);
							return PublicationDateFormatter.DAY_DIGITMONTH_YEAR.convertDateToString(date);
						}
					} else if (numOfDateValues == 2) {
						if (PublicationDateFormatter.YEAR_DIGIT_MONTH.isValidDate(pubDate)) {
							Date date = PublicationDateFormatter.YEAR_DIGIT_MONTH.convertStringToDate(pubDate);
							return PublicationDateFormatter.YEAR_DIGIT_MONTH.convertDateToString(date);
						} else if (PublicationDateFormatter.THREE_LETTER_MONTH_YEAR.isValidDate(pubDate)) {
							Date date = PublicationDateFormatter.THREE_LETTER_MONTH_YEAR.convertStringToDate(pubDate);
							return PublicationDateFormatter.YEAR_DIGIT_MONTH.convertDateToString(date);
						}
					} else {
						Date date = PublicationDateFormatter.YEAR.convertStringToDate(pubDate);
						return PublicationDateFormatter.YEAR.convertDateToString(date);
					}
				}
			} catch (ParseException e) {
				logger.error("Error in citation. Origin of error [" + e.getMessage() + ']');
			}
		}
		return null;
	}

}
