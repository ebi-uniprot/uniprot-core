package org.uniprot.core.flatfile.antlr;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.UniprotKBLineParser;
import org.uniprot.core.flatfile.parser.impl.DefaultUniprotKBLineParserFactory;
import org.uniprot.core.flatfile.parser.impl.rn.RnLineObject;

import java.util.Arrays;
import java.util.List;

class RnLineParserTest {
    @Test
    void test() {
        String rnLines =
                "RN   [2] {ECO:0000313|EMBL:BAG16761.1, ECO:0000269|PubMed:10433554,\n"
                        + "RN   ECO:0000303|Ref.6}\n";
        UniprotKBLineParser<RnLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createRnLineParser();
        RnLineObject obj = parser.parse(rnLines);
        assertEquals(2, obj.number);
        List<String> evidences =
                Arrays.asList(
                        new String[] {
                            "ECO:0000313|EMBL:BAG16761.1",
                            "ECO:0000269|PubMed:10433554",
                            "ECO:0000303|Ref.6"
                        });
        assertEquals(evidences, obj.getEvidenceInfo().getEvidences().get(2));
    }
}
