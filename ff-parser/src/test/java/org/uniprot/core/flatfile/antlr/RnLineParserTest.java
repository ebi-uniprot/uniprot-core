package org.uniprot.core.flatfile.antlr;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.UniprotkbLineParser;
import org.uniprot.core.flatfile.parser.impl.DefaultUniprotkbLineParserFactory;
import org.uniprot.core.flatfile.parser.impl.rn.RnLineObject;

class RnLineParserTest {
    @Test
    void test() {
        String rnLines =
                "RN   [2] {ECO:0000313|EMBL:BAG16761.1, ECO:0000269|PubMed:10433554,\n"
                        + "RN   ECO:0000303|Ref.6}\n";
        UniprotkbLineParser<RnLineObject> parser =
                new DefaultUniprotkbLineParserFactory().createRnLineParser();
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
