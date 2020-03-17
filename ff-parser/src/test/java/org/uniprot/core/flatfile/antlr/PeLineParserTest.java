package org.uniprot.core.flatfile.antlr;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.UniprotKBLineParser;
import org.uniprot.core.flatfile.parser.impl.DefaultUniprotKBLineParserFactory;
import org.uniprot.core.flatfile.parser.impl.pe.PeLineObject;

class PeLineParserTest {
    @Test
    void test() {
        String peLines = "PE   1: Evidence at protein level;\n";
        UniprotKBLineParser<PeLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createPeLineParser();
        PeLineObject obj = parser.parse(peLines);
        assertEquals(1, obj.level.intValue());
    }
}
