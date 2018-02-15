package uk.ac.ebi.uniprot.parser.ffwriter.line;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.AbstractMap;
import java.util.Locale;
import java.util.Map;

import org.junit.Test;

import uk.ac.ebi.uniprot.domain.uniprot.EntryAudit;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtEntryType;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;
import uk.ac.ebi.uniprot.ffwriter.line.FFLine;
import uk.ac.ebi.uniprot.ffwriter.line.impl.DTLineBuilder;

public class DTLineBuildTest {
	UniProtFactory factory = UniProtFactory.INSTANCE;
	DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy", Locale.ENGLISH);
	DTLineBuilder builder = new DTLineBuilder();

	@Test
	public void testSwissProt() throws Exception {
		LocalDate firstPublicDate = LocalDate.of(1986, 7, 21);
		LocalDate lastAnnotationUpdateDate = LocalDate.of(2007, 11, 13);
		LocalDate lastSequenceUpdateDate = LocalDate.of(2007, 1, 23);

		EntryAudit entryAudit = factory.createEntryAudit(firstPublicDate, lastAnnotationUpdateDate,
				lastSequenceUpdateDate, 106, 3);

		String dtLine = "DT   21-JUL-1986, integrated into UniProtKB/Swiss-Prot.\n"
				+ "DT   23-JAN-2007, sequence version 3.\n" + "DT   13-NOV-2007, entry version 106.";

		Map.Entry<EntryAudit, UniProtEntryType> data = new AbstractMap.SimpleEntry<>(entryAudit,
				UniProtEntryType.SWISSPROT);
		FFLine ffLine = builder.build(data);

		String resultString = ffLine.toString();

		assertEquals(dtLine, resultString);
	}

	@Test
	public void testTrembl() throws Exception {
		LocalDate firstPublicDate = LocalDate.of(2005, 5, 24);
		LocalDate lastAnnotationUpdateDate = LocalDate.of(2017, 12, 20);
		LocalDate lastSequenceUpdateDate = LocalDate.of(2005, 5, 24);

		EntryAudit entryAudit = factory.createEntryAudit(firstPublicDate, lastAnnotationUpdateDate,
				lastSequenceUpdateDate, 113, 1);

		String dtLine = "DT   24-MAY-2005, integrated into UniProtKB/TrEMBL.\n"
				+ "DT   24-MAY-2005, sequence version 1.\n" + "DT   20-DEC-2017, entry version 113.";

		Map.Entry<EntryAudit, UniProtEntryType> data = new AbstractMap.SimpleEntry<>(entryAudit,
				UniProtEntryType.TREMBL);
		FFLine ffLine = builder.build(data);

		String resultString = ffLine.toString();

		assertEquals(dtLine, resultString);
	}
}
