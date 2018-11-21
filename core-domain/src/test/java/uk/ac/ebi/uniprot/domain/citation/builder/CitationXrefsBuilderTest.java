package uk.ac.ebi.uniprot.domain.citation.builder;


import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.citation.CitationXrefType;
import uk.ac.ebi.uniprot.domain.citation.CitationXrefs;
import uk.ac.ebi.uniprot.domain.citation.impl.CitationXrefsImpl;
import uk.ac.ebi.uniprot.domain.impl.DBCrossReferenceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.Test;

import static org.junit.Assert.*;

public class CitationXrefsBuilderTest {

    @Test
    public void testCreateCitationXrefsPubmed() {
    	  List<DBCrossReference<CitationXrefType>> xrefs = new ArrayList<>();
    	  DBCrossReference<CitationXrefType> xref= new DBCrossReferenceImpl<>(CitationXrefType.PUBMED, "somepID1");
        xrefs.add(xref);
        CitationXrefs citationXrefs = new CitationXrefsImpl(xrefs);
        assertEquals(1, citationXrefs.getXrefs().size());
        Optional<DBCrossReference<CitationXrefType>> pubmed = citationXrefs.getTyped(CitationXrefType.PUBMED);
        assertTrue(pubmed.isPresent());
        assertFalse(citationXrefs.getTyped(CitationXrefType.AGRICOLA).isPresent());
        assertFalse(citationXrefs.getTyped(CitationXrefType.DOI).isPresent());
        assertEquals(xref, pubmed.get());
        TestHelper.verifyJson(citationXrefs);
    }
    @Test
    public void testCreateCitationXrefsAll() {
        List<DBCrossReference<CitationXrefType>> xrefs = new ArrayList<>();
        DBCrossReference<CitationXrefType> pubmed= new DBCrossReferenceImpl<>(CitationXrefType.PUBMED, "somepID1");
        xrefs.add(pubmed);
        DBCrossReference<CitationXrefType> agricola= new DBCrossReferenceImpl<>(CitationXrefType.AGRICOLA, "someID1");
        DBCrossReference<CitationXrefType> doi= new DBCrossReferenceImpl<>(CitationXrefType.DOI, "someDoiID2");
        xrefs.add(agricola);
        xrefs.add(doi);
        CitationXrefs citationXrefs =new CitationXrefsImpl(xrefs);
        assertEquals(3, citationXrefs.getXrefs().size());
        assertTrue(citationXrefs.getTyped(CitationXrefType.PUBMED).isPresent());
        assertTrue(citationXrefs.getTyped(CitationXrefType.AGRICOLA).isPresent());
        assertTrue(citationXrefs.getTyped(CitationXrefType.DOI).isPresent());
        assertEquals(pubmed, citationXrefs.getTyped(CitationXrefType.PUBMED).get());
        assertEquals(agricola, citationXrefs.getTyped(CitationXrefType.AGRICOLA).get());
        assertEquals(doi, citationXrefs.getTyped(CitationXrefType.DOI).get());
        TestHelper.verifyJson(citationXrefs);
    }

    @Test
    public void testCreateArgicolaCitationXref() {
    	DBCrossReference<CitationXrefType> xref= new DBCrossReferenceImpl<>(CitationXrefType.AGRICOLA, "someID1");
        verifyXref(xref, CitationXrefType.AGRICOLA, "someID1");
    }
    private void verifyXref(DBCrossReference<CitationXrefType> xref, CitationXrefType type, String id){
        assertEquals(type, xref.getDatabaseType());
        assertEquals( id, xref.getId());
    }
    @Test
    public void testCreateDOICitationXref() {
    	DBCrossReference<CitationXrefType> xref= new DBCrossReferenceImpl<>(CitationXrefType.DOI, "someDoiID2");
        verifyXref(xref, CitationXrefType.DOI, "someDoiID2");
    }
    @Test
    public void testCreatePubMedCitationXref() {
    	DBCrossReference<CitationXrefType> xref= new DBCrossReferenceImpl<>(CitationXrefType.PUBMED, "somepID1");
        verifyXref(xref, CitationXrefType.PUBMED, "somepID1");
    }
}
