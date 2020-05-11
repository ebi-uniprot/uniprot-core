package org.uniprot.core.scorer.uniprotkb;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.UniprotKBLineParser;
import org.uniprot.core.flatfile.parser.UniprotKBLineParserFactory;
import org.uniprot.core.flatfile.parser.impl.DefaultUniprotKBLineParserFactory;
import org.uniprot.core.flatfile.parser.impl.kw.KwLineConverter;
import org.uniprot.core.flatfile.parser.impl.kw.KwLineObject;
import org.uniprot.core.uniprotkb.Keyword;

/** Tests if the KW line is parsed correctly for SwissProt and TrEMBL entries. */
class KeywordScoredTest {
    private static final UniprotKBLineParserFactory PARSER_FACTORY =
            new DefaultUniprotKBLineParserFactory();

    @DisplayName(
            "Inserts a single KW line into the entry. The keywords given "
                    + "in this line is reflectd by the score.")
    @Test
    void shouldKeyword2Score0() {
        String lines = "KW   ATP-binding; Cell cycle; Cell division; Kinase; Mitosis.\n";
        int score = 0;
        for (Keyword kw : parseLines(lines)) score += new KeywordScored(kw).score();
        // should be 5 as its 5 kw's
        assertEquals(0, score);
    }

    @DisplayName(
            "Inserts multiple KW line into the entry. The keywords given "
                    + "in this line is reflectd by the score.")
    @Test
    void shouldKeyword3Score0() {
        String lines =
                "KW   ATP-binding; Cell cycle; Cell division; Kinase; Mitosis;\n"
                        + "KW   Phosphorylation; Serine/threonine-protein kinase; Transferase.\n";
        int score = 0;
        for (Keyword kw : parseLines(lines)) score += new KeywordScored(kw).score();
        // should be 8
        assertEquals(0, score);
    }

    private List<Keyword> parseLines(String lines) {
        UniprotKBLineParser<KwLineObject> parser = PARSER_FACTORY.createKwLineParser();
        KwLineObject obj = parser.parse(lines);
        KwLineConverter converter = new KwLineConverter(new HashMap<>());
        return converter.convert(obj);
    }
}
