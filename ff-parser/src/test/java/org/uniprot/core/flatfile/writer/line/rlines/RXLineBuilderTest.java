package org.uniprot.core.flatfile.writer.line.rlines;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.uniprot.core.CrossReference;
import org.uniprot.core.citation.Citation;
import org.uniprot.core.citation.CitationDatabase;
import org.uniprot.core.citation.impl.BookBuilder;
import org.uniprot.core.flatfile.parser.impl.rx.RXLineBuilder;
import org.uniprot.core.impl.CrossReferenceBuilder;

import java.util.ArrayList;
import java.util.List;

class RXLineBuilderTest {
    private RXLineBuilder builder = new RXLineBuilder();

    @Test
    void testPubmed() {
        Citation xrefs = buildCitationXref("15165820", null, null);
        List<String> lines = builder.buildLine(xrefs, true, true);
        assertEquals(1, lines.size());
        String expected = "RX   PubMed=15165820;";
        assertEquals(expected, lines.get(0));
    }

    @Test
    void testDOI() {
        Citation xrefs = buildCitationXref(null, "10.1016/j.virol.2004.02.019", null);
        List<String> lines = builder.buildLine(xrefs, true, true);
        assertEquals(1, lines.size());
        String expected = "RX   DOI=10.1016/j.virol.2004.02.019;";
        assertEquals(expected, lines.get(0));
    }

    @Test
    void testAgricola() {
        Citation xrefs = buildCitationXref(null, null, "asfsadgdasgdagd");
        List<String> lines = builder.buildLine(xrefs, true, true);
        assertEquals(1, lines.size());
        String expected = "RX   AGRICOLA=asfsadgdasgdagd;";
        assertEquals(expected, lines.get(0));
    }

    @Test
    void testPubmedDOI() {
        Citation xrefs = buildCitationXref("15165820", "10.1016/j.virol.2004.02.019", null);
        List<String> lines = builder.buildLine(xrefs, true, true);
        assertEquals(1, lines.size());
        String expected = "RX   PubMed=15165820; DOI=10.1016/j.virol.2004.02.019;";
        assertEquals(expected, lines.get(0));
    }

    @Test
    void testPubmedAgricola() {
        Citation xrefs = buildCitationXref("15165820", null, "asfsadgdasgdagd");
        List<String> lines = builder.buildLine(xrefs, true, true);
        assertEquals(1, lines.size());
        String expected = "RX   PubMed=15165820; AGRICOLA=asfsadgdasgdagd;";
        assertEquals(expected, lines.get(0));
    }

    @Test
    void testAll() {
        Citation xrefs =
                buildCitationXref("15165820", "10.1016/j.virol.2004.02.019", "asfsadgdasgdagd");
        List<String> lines = builder.buildLine(xrefs, true, true);
        assertEquals(1, lines.size());
        String expected =
                "RX   PubMed=15165820; AGRICOLA=asfsadgdasgdagd; DOI=10.1016/j.virol.2004.02.019;";
        assertEquals(expected, lines.get(0));
    }

    @Test
    void test2() {
        Citation xrefs =
                buildCitationXref("15165820", "10.1016/j.virol.2004.02.019", "asfsadgdasgdagd");
        List<String> lines = builder.buildLine(xrefs, false, true);
        assertEquals(1, lines.size());
        String expected =
                "PubMed=15165820; AGRICOLA=asfsadgdasgdagd; DOI=10.1016/j.virol.2004.02.019;";
        assertEquals(expected, lines.get(0));
    }

    private Citation buildCitationXref(String pubmed, String doi, String agricolaId) {

        List<CrossReference<CitationDatabase>> xrefs = new ArrayList<>();
        if (pubmed != null)
            xrefs.add(
                    new CrossReferenceBuilder<CitationDatabase>()
                            .database(CitationDatabase.PUBMED)
                            .id(pubmed)
                            .build());
        if (doi != null)
            xrefs.add(
                    new CrossReferenceBuilder<CitationDatabase>()
                            .database(CitationDatabase.DOI)
                            .id(doi)
                            .build());
        if (agricolaId != null)
            xrefs.add(
                    new CrossReferenceBuilder<CitationDatabase>()
                            .database(CitationDatabase.AGRICOLA)
                            .id(agricolaId)
                            .build());
        return new BookBuilder().citationCrossReferencesSet(xrefs).build();
    }
}
