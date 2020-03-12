package org.uniprot.core.flatfile.antlr;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.UniprotkbLineParser;
import org.uniprot.core.flatfile.parser.impl.DefaultUniprotkbLineParserFactory;
import org.uniprot.core.flatfile.parser.impl.id.IdLineObject;

class IdLineParserTest {
    @Test
    void test() {
        String idlines = "ID   001R_FRG3G              Reviewed;         256 AA.\n";
        UniprotkbLineParser<IdLineObject> parser =
                new DefaultUniprotkbLineParserFactory().createIdLineParser();
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
        UniprotkbLineParser<IdLineObject> parser =
                new DefaultUniprotkbLineParserFactory().createIdLineParser();
        IdLineObject obj = parser.parse(idlines);
        verify(obj, "001R_FRG3G_123", true, 256);
    }

    @Test
    void test2() {
        String idlines = "ID   001R_FRG3G              Reviewed;         256 AA.\n";
        UniprotkbLineParser<IdLineObject> parser =
                new DefaultUniprotkbLineParserFactory().createIdLineParser();
        IdLineObject obj = parser.parse(idlines);
        verify(obj, "001R_FRG3G", true, 256);
    }
}
