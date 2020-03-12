package org.uniprot.core.flatfile.antlr;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.UniprotkbLineParser;
import org.uniprot.core.flatfile.parser.impl.DefaultUniprotkbLineParserFactory;
import org.uniprot.core.flatfile.parser.impl.pe.PeLineObject;

class PeLineParserTest {
    @Test
    void test() {
        String peLines = "PE   1: Evidence at protein level;\n";
        UniprotkbLineParser<PeLineObject> parser =
                new DefaultUniprotkbLineParserFactory().createPeLineParser();
        PeLineObject obj = parser.parse(peLines);
        assertEquals(1, obj.level.intValue());
    }
}
