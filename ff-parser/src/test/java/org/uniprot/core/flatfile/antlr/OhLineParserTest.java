package org.uniprot.core.flatfile.antlr;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.UniprotKBLineParser;
import org.uniprot.core.flatfile.parser.impl.DefaultUniprotKBLineParserFactory;
import org.uniprot.core.flatfile.parser.impl.oh.OhLineObject;

class OhLineParserTest {
    @Test
    void test() {
        String ohLines = "OH   NCBI_TaxID=9598; Pan troglodytes (Chimpanzee).\n";
        UniprotKBLineParser<OhLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createOhLineParser();
        OhLineObject obj = parser.parse(ohLines);

        assertEquals(1, obj.getHosts().size());
        verify(obj.getHosts().get(0), 9598, "Pan troglodytes (Chimpanzee)");
    }

    private void verify(OhLineObject.OhValue oh, int taxId, String hostName) {
        assertEquals(taxId, oh.getTax_id());
        assertEquals(hostName, oh.getHostname());
    }

    @Test
    void test2() {
        String ohLines =
                "OH   NCBI_TaxID=3662; Cucurbita moschata (Winter crookneck squash) (Cucurbita pepo var. moschata).\n";
        UniprotKBLineParser<OhLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createOhLineParser();
        OhLineObject obj = parser.parse(ohLines);

        assertEquals(1, obj.getHosts().size());
        verify(
                obj.getHosts().get(0),
                3662,
                "Cucurbita moschata (Winter crookneck squash) (Cucurbita pepo var. moschata)");
    }

    @Test
    void test3() {
        String ohLines =
                "OH   NCBI_TaxID=3662; Cucurbita moschata (Winter crookneck squash) (Cucurbita pepo var. moschata).\n"
                        + "OH   NCBI_TaxID=9598; Pan troglodytes (Chimpanzee).\n";
        UniprotKBLineParser<OhLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createOhLineParser();
        OhLineObject obj = parser.parse(ohLines);

        assertEquals(2, obj.getHosts().size());
        verify(
                obj.getHosts().get(0),
                3662,
                "Cucurbita moschata (Winter crookneck squash) (Cucurbita pepo var. moschata)");
        verify(obj.getHosts().get(1), 9598, "Pan troglodytes (Chimpanzee)");
    }
}
