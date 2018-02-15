package uk.ac.ebi.uniprot.parser.ffwriter.line;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.junit.Test;

public class UtilTest {
	@Test
	public void testDateTimeFormatter() {
		  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy",Locale.ENGLISH);
		  LocalDate date = LocalDate.now();
		  System.out.println(formatter.format(date));
		  System.out.println(formatter.format(date).toUpperCase());
	}
}
