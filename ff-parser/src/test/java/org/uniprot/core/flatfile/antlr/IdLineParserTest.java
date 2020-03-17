package org.uniprot.core.flatfile.antlr;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.UniprotKBLineParser;
import org.uniprot.core.flatfile.parser.impl.DefaultUniprotKBLineParserFactory;
import org.uniprot.core.flatfile.parser.impl.id.IdLineObject;

class IdLineParserTest {
    @Test
    void test() {
        String idlines = "ID   001R_FRG3G              Reviewed;         256 AA.\n";
        UniprotKBLineParser<IdLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createIdLineParser();
        IdLineObject obj = parser.parse(idlines);
        verify(obj, "001R_FRG3G", true, 256);
    }

    private void verify(IdLineObject obj, String id, boolean reviewed, int seqlen) {
        assertEquals(id, obj.getEntryName());
        assertEquals(reviewed, obj.getReviewed());
        assertEquals(seqlen, obj.getSequenceLength());
    }

    @Test
    void testIsoform() {
        String idlines = "ID   001R_FRG3G_123           Reviewed;         256 AA.\n";
        UniprotKBLineParser<IdLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createIdLineParser();
        IdLineObject obj = parser.parse(idlines);
        verify(obj, "001R_FRG3G_123", true, 256);
    }

    @Test
    void test2() {
        String idlines = "ID   001R_FRG3G              Reviewed;         256 AA.\n";
        UniprotKBLineParser<IdLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createIdLineParser();
        IdLineObject obj = parser.parse(idlines);
        verify(obj, "001R_FRG3G", true, 256);
    }
}
