package org.uniprot.core.flatfile.antlr;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.UniprotKBLineParser;
import org.uniprot.core.flatfile.parser.impl.DefaultUniprotKBLineParserFactory;
import org.uniprot.core.flatfile.parser.impl.dt.DtLineObject;

class DtLineParserTest {
    @Test
    void test() {
        String dtLines =
                "DT   28-JUN-2011, integrated into UniProtKB/Swiss-Prot.\n"
                        + "DT   19-JUL-2004, sequence version 1.\n"
                        + "DT   18-APR-2012, entry version 24.\n";
        UniprotKBLineParser<DtLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createDtLineParser();
        DtLineObject obj = parser.parse(dtLines);
        assertTrue(obj.isSiwssprot);
        assertEquals(1, obj.seq_version);
        assertEquals(24, obj.entry_version);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMM-yyyy");

        assertEquals("28-Jun-2011", formatter.format(obj.integration_date));
        assertEquals("19-Jul-2004", formatter.format(obj.seq_date));
        assertEquals("18-Apr-2012", formatter.format(obj.entry_date));
    }
}
