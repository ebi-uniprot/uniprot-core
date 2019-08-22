package org.uniprot.core.flatfile.writer.line;

import org.junit.Test;
import org.uniprot.core.flatfile.parser.impl.dt.DTLineBuilder;
import org.uniprot.core.flatfile.writer.FFLine;
import org.uniprot.core.uniprot.EntryAudit;
import org.uniprot.core.uniprot.UniProtEntryType;
import org.uniprot.core.uniprot.builder.EntryAuditBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.AbstractMap;
import java.util.Locale;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class DTLineBuildTest {
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy", Locale.ENGLISH);
    DTLineBuilder builder = new DTLineBuilder();

    @Test
    public void testSwissProt() throws Exception {
        LocalDate firstPublicDate = LocalDate.of(1986, 7, 21);
        LocalDate lastAnnotationUpdateDate = LocalDate.of(2007, 11, 13);
        LocalDate lastSequenceUpdateDate = LocalDate.of(2007, 1, 23);

        EntryAudit entryAudit = new EntryAuditBuilder()
                .firstPublic(firstPublicDate)
                .lastAnnotationUpdate(lastAnnotationUpdateDate)
                .lastSequenceUpdate(lastSequenceUpdateDate)
                .entryVersion(106)
                .sequenceVersion(3)
                .build();

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

        EntryAudit entryAudit = new EntryAuditBuilder()
                .firstPublic(firstPublicDate)
                .lastAnnotationUpdate(lastAnnotationUpdateDate)
                .lastSequenceUpdate(lastSequenceUpdateDate)
                .entryVersion(113)
                .sequenceVersion(1)
                .build();

        String dtLine = "DT   24-MAY-2005, integrated into UniProtKB/TrEMBL.\n"
                + "DT   24-MAY-2005, sequence version 1.\n" + "DT   20-DEC-2017, entry version 113.";

        Map.Entry<EntryAudit, UniProtEntryType> data = new AbstractMap.SimpleEntry<>(entryAudit,
                                                                                     UniProtEntryType.TREMBL);
        FFLine ffLine = builder.build(data);

        String resultString = ffLine.toString();

        assertEquals(dtLine, resultString);
    }
}