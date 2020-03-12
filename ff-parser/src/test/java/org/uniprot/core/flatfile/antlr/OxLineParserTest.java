package org.uniprot.core.flatfile.antlr;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.UniprotkbLineParser;
import org.uniprot.core.flatfile.parser.impl.DefaultUniprotkbLineParserFactory;
import org.uniprot.core.flatfile.parser.impl.ox.OxLineObject;

class OxLineParserTest {
    @Test
    void test() {
        String oxLines = "OX   NCBI_TaxID=562;\n";
        UniprotkbLineParser<OxLineObject> parser =
                new DefaultUniprotkbLineParserFactory().createOxLineParser();
        OxLineObject obj = parser.parse(oxLines);
        assertEquals(562, obj.taxonomy_id);
        assertNull(obj.getEvidenceInfo().getEvidences().get(562));
    }

    @Test
    void testWithEvidence() {
        String oxLines = "OX   NCBI_TaxID=1085379 {ECO:0000313|EMBL:EOP66756.1};\n";

        UniprotkbLineParser<OxLineObject> parser =
                new DefaultUniprotkbLineParserFactory().createOxLineParser();
        OxLineObject obj = parser.parse(oxLines);
        assertEquals(1085379, obj.taxonomy_id);
        assertEquals(
                Arrays.asList(new String[] {"ECO:0000313|EMBL:EOP66756.1"}),
                obj.getEvidenceInfo().getEvidences().get(1085379));
    }

    @Test
    void testWithEvidenceMultiLine() {
        String oxLines =
                "OX   NCBI_TaxID=1085379 {ECO:0000313|EMBL:EOP66756.1,\n"
                        + "OX   ECO:0000313|EMBL:CBL02507.1};\n";
        UniprotkbLineParser<OxLineObject> parser =
                new DefaultUniprotkbLineParserFactory().createOxLineParser();
        OxLineObject obj = parser.parse(oxLines);
        assertEquals(1085379, obj.taxonomy_id);
        assertEquals(
                Arrays.asList(
                        new String[] {
                            "ECO:0000313|EMBL:EOP66756.1", "ECO:0000313|EMBL:CBL02507.1"
                        }),
                obj.getEvidenceInfo().getEvidences().get(1085379));
    }
}
