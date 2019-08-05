package uk.ac.ebi.uniprot.flatfile.parser.ffwriter.line.rlines;

import org.junit.Test;
import org.uniprot.core.DBCrossReference;
import org.uniprot.core.builder.DBCrossReferenceBuilder;
import org.uniprot.core.citation.Citation;
import org.uniprot.core.citation.CitationXrefType;
import org.uniprot.core.citation.builder.BookBuilder;

import uk.ac.ebi.uniprot.flatfile.parser.impl.rx.RXLineBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class RXLineBuilderTest {
    RXLineBuilder builder = new RXLineBuilder();

    @Test
    public void testPubmed() {
        Citation xrefs = buildCitationXref("15165820", null, null);
        List<String> lines = builder.buildLine(xrefs, true, true);
        assertEquals(1, lines.size());
        String expected = "RX   PubMed=15165820;";
        assertEquals(expected, lines.get(0));
    }

    @Test
    public void testDOI() {
        Citation xrefs = buildCitationXref(null, "10.1016/j.virol.2004.02.019", null);
        List<String> lines = builder.buildLine(xrefs, true, true);
        assertEquals(1, lines.size());
        String expected = "RX   DOI=10.1016/j.virol.2004.02.019;";
        assertEquals(expected, lines.get(0));
    }

    @Test
    public void testAgricola() {
        Citation xrefs = buildCitationXref(null, null, "asfsadgdasgdagd");
        List<String> lines = builder.buildLine(xrefs, true, true);
        assertEquals(1, lines.size());
        String expected = "RX   AGRICOLA=asfsadgdasgdagd;";
        assertEquals(expected, lines.get(0));
    }

    @Test
    public void testPubmedDOI() {
        Citation xrefs = buildCitationXref("15165820", "10.1016/j.virol.2004.02.019", null);
        List<String> lines = builder.buildLine(xrefs, true, true);
        assertEquals(1, lines.size());
        String expected = "RX   PubMed=15165820; DOI=10.1016/j.virol.2004.02.019;";
        assertEquals(expected, lines.get(0));
    }

    @Test
    public void testPubmedAgricola() {
        Citation xrefs = buildCitationXref("15165820", null, "asfsadgdasgdagd");
        List<String> lines = builder.buildLine(xrefs, true, true);
        assertEquals(1, lines.size());
        String expected = "RX   PubMed=15165820; AGRICOLA=asfsadgdasgdagd;";
        assertEquals(expected, lines.get(0));
    }

    @Test
    public void testAll() {
        Citation xrefs = buildCitationXref("15165820", "10.1016/j.virol.2004.02.019", "asfsadgdasgdagd");
        List<String> lines = builder.buildLine(xrefs, true, true);
        assertEquals(2, lines.size());
        String expected = "RX   DOI=10.1016/j.virol.2004.02.019;";
        assertEquals(expected, lines.get(1));
    }

    @Test
    public void test2() {
        Citation xrefs = buildCitationXref("15165820", "10.1016/j.virol.2004.02.019", "asfsadgdasgdagd");
        List<String> lines = builder.buildLine(xrefs, false, true);
        assertEquals(1, lines.size());
        String expected = "PubMed=15165820; AGRICOLA=asfsadgdasgdagd; DOI=10.1016/j.virol.2004.02.019;";
        assertEquals(expected, lines.get(0));
    }

    private Citation buildCitationXref(String pubmed, String doi, String agricolaId) {

        List<DBCrossReference<CitationXrefType>> xrefs = new ArrayList<>();
        if (pubmed != null)
            xrefs.add(new DBCrossReferenceBuilder<CitationXrefType>().databaseType(CitationXrefType.PUBMED).id(pubmed)
                              .build());
        if (doi != null)
            xrefs.add(new DBCrossReferenceBuilder<CitationXrefType>().databaseType(CitationXrefType.DOI).id(doi)
                              .build());
        if (agricolaId != null)
            xrefs.add(new DBCrossReferenceBuilder<CitationXrefType>().databaseType(CitationXrefType.AGRICOLA)
                              .id(agricolaId)
                              .build());
        return new BookBuilder().citationXrefs(xrefs).build();

    }
}
