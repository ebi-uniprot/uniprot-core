package org.uniprot.core.flatfile.parser.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.impl.dt.DtLineConverter;
import org.uniprot.core.flatfile.parser.impl.dt.DtLineObject;
import org.uniprot.core.uniprot.EntryAudit;

class DtLineConverterTest {
    private DtLineConverter converter = new DtLineConverter();

    @Test
    void testConverter() {
        DtLineObject dtLine = new DtLineObject();

        DateTimeFormatter formatter =
                new DateTimeFormatterBuilder()
                        .parseCaseInsensitive()
                        .append(DateTimeFormatter.ofPattern("dd-MMM-yyyy"))
                        .toFormatter();

        // DateTimeFormatter.ofPattern("dd-MMM-uuuu");
        dtLine.integration_date = LocalDate.parse("28-Jun-2011", formatter);
        dtLine.seq_date = LocalDate.parse("19-JUL-2004", formatter);
        dtLine.seq_version = 1;
        dtLine.entry_date = LocalDate.parse("18-APR-2012", formatter);
        dtLine.entry_version = 24;
        EntryAudit enAudit = converter.convert(dtLine);
        assertEquals(1, enAudit.getSequenceVersion());
        assertEquals(24, enAudit.getEntryVersion());

        assertEquals(dtLine.integration_date, enAudit.getFirstPublicDate());
        assertEquals(dtLine.entry_date, enAudit.getLastAnnotationUpdateDate());
        assertEquals(dtLine.seq_date, enAudit.getLastSequenceUpdateDate());
    }
}
