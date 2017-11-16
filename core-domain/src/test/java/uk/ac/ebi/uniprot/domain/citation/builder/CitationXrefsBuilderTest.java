package uk.ac.ebi.uniprot.domain.citation.builder;

import uk.ac.ebi.uniprot.domain.citation.CitationXref;
import uk.ac.ebi.uniprot.domain.citation.CitationXrefType;
import uk.ac.ebi.uniprot.domain.citation.CitationXrefs;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.Test;

import static org.junit.Assert.*;

public class CitationXrefsBuilderTest {

    @Test
    public void testCreateCitationXrefsPubmed() {
        List<CitationXref> xrefs = new ArrayList<>();
        CitationXref xref= CitationXrefsBuilder.createCitationXref(CitationXrefType.PUBMED, "somepID1");
        xrefs.add(xref);
        CitationXrefs citationXrefs = CitationXrefsBuilder.createCitationXrefs(xrefs);
        assertEquals(1, citationXrefs.getAllXrefs().size());
        Optional<CitationXref> pubmed = citationXrefs.hasXref(CitationXrefType.PUBMED);
        assertTrue(pubmed.isPresent());
        assertFalse(citationXrefs.hasXref(CitationXrefType.AGRICOLA).isPresent());
        assertFalse(citationXrefs.hasXref(CitationXrefType.DOI).isPresent());
        assertEquals(xref, pubmed.get());
    }
    @Test
    public void testCreateCitationXrefsAll() {
        List<CitationXref> xrefs = new ArrayList<>();
        CitationXref pubmed= CitationXrefsBuilder.createCitationXref(CitationXrefType.PUBMED, "somepID1");
        xrefs.add(pubmed);
        CitationXref agricola= CitationXrefsBuilder.createCitationXref(CitationXrefType.AGRICOLA, "someID1");
        CitationXref doi= CitationXrefsBuilder.createCitationXref(CitationXrefType.DOI, "someDoiID2");
        xrefs.add(agricola);
        xrefs.add(doi);
        CitationXrefs citationXrefs = CitationXrefsBuilder.createCitationXrefs(xrefs);
        assertEquals(3, citationXrefs.getAllXrefs().size());
        assertTrue(citationXrefs.hasXref(CitationXrefType.PUBMED).isPresent());
        assertTrue(citationXrefs.hasXref(CitationXrefType.AGRICOLA).isPresent());
        assertTrue(citationXrefs.hasXref(CitationXrefType.DOI).isPresent());
        assertEquals(pubmed, citationXrefs.hasXref(CitationXrefType.PUBMED).get());
        assertEquals(agricola, citationXrefs.hasXref(CitationXrefType.AGRICOLA).get());
        assertEquals(doi, citationXrefs.hasXref(CitationXrefType.DOI).get());
    }

    @Test
    public void testCreateArgicolaCitationXref() {
        CitationXref xref= CitationXrefsBuilder.createCitationXref(CitationXrefType.AGRICOLA, "someID1");
        verifyXref(xref, CitationXrefType.AGRICOLA, "someID1");
    }
    private void verifyXref(CitationXref xref, CitationXrefType type, String id){
        assertEquals(type, xref.getXrefType());
        assertEquals( id, xref.getId());
    }
    @Test
    public void testCreateDOICitationXref() {
        CitationXref xref= CitationXrefsBuilder.createCitationXref(CitationXrefType.DOI, "someDoiID2");
        verifyXref(xref, CitationXrefType.DOI, "someDoiID2");
    }
    @Test
    public void testCreatePubMedCitationXref() {
        CitationXref xref= CitationXrefsBuilder.createCitationXref(CitationXrefType.PUBMED, "somepID1");
        verifyXref(xref, CitationXrefType.PUBMED, "somepID1");
    }
}
