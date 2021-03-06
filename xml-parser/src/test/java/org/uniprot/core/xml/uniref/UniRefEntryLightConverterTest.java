package org.uniprot.core.xml.uniref;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.xml.uniref.UniRefEntryConverterTest.UNIREF_ROOT_ELEMENT;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.go.GeneOntologyEntry;
import org.uniprot.core.cv.go.GoAspect;
import org.uniprot.core.uniref.RepresentativeMember;
import org.uniprot.core.uniref.UniRefEntryLight;
import org.uniprot.core.uniref.UniRefType;
import org.uniprot.core.xml.XmlChainIterator;
import org.uniprot.core.xml.jaxb.uniref.DbReferenceType;
import org.uniprot.core.xml.jaxb.uniref.Entry;
import org.uniprot.core.xml.jaxb.uniref.MemberType;

/**
 * @author lgonzales
 * @since 06/07/2020
 */
class UniRefEntryLightConverterTest {

    @Test
    void testFromXml() {
        UniRefEntryLightConverter converter = new UniRefEntryLightConverter();
        String file = "/uniref/50_Q9EPS7_Q95604.xml";
        InputStream is = UniRefEntryLightConverterTest.class.getResourceAsStream(file);

        assertNotNull(is);

        List<InputStream> iss = Collections.singletonList(is);

        XmlChainIterator<Entry, Entry> chainingIterators =
                new XmlChainIterator<>(
                        iss.iterator(), Entry.class, UNIREF_ROOT_ELEMENT, Function.identity());
        assertNotNull(chainingIterators);
        assertTrue(chainingIterators.hasNext());
        Entry xmlEntry = chainingIterators.next();
        assertNotNull(xmlEntry);
        UniRefEntryLight entry = converter.fromXml(xmlEntry);
        assertNotNull(entry);
        assertNotNull(entry.getId());

        assertNotNull(entry.getRepresentativeMember());
        RepresentativeMember repMember = entry.getRepresentativeMember();
        assertEquals("Q9EPS7_MOUSE", repMember.getMemberId());
        assertEquals("Q9EPS7", repMember.getUniProtAccessions().get(0).getValue());
        assertEquals("Pheromone receptor V3R6", repMember.getProteinName());
        assertEquals("Mus musculus (Mouse)", repMember.getOrganismName());

        assertEquals("UniRef50_Q9EPS7", entry.getId().getValue());
        assertEquals("Cluster: Pheromone receptor V3R6", entry.getName());
        assertEquals("2014-11-26", entry.getUpdated().toString());
        assertEquals(UniRefType.UniRef50, entry.getEntryType());
        assertEquals("Muroidea", entry.getCommonTaxon().getScientificName());
        assertEquals(337687, entry.getCommonTaxon().getTaxonId());
        assertEquals("F6MB03_MOUSE,F6MB03", entry.getSeedId());

        assertEquals(3, entry.getGoTerms().size());
        GeneOntologyEntry goTerm = entry.getGoTerms().get(0);
        assertEquals(GoAspect.FUNCTION, goTerm.getAspect());
        assertEquals("GO:0048306", goTerm.getId());

        assertTrue(entry.getMembers().contains("Q8R2B4,1"));
        assertTrue(entry.getMembers().contains("UPI0000DBE4A9,3"));

        assertEquals(entry.getMemberCount(), entry.getMembers().size());
        assertTrue(chainingIterators.hasNext());
        xmlEntry = chainingIterators.next();
        assertNotNull(xmlEntry);
        entry = converter.fromXml(xmlEntry);
        assertNotNull(entry);
        assertNotNull(entry);
        assertNotNull(entry.getId());
        assertEquals("UniRef50_Q95604", entry.getId().getValue());
        assertFalse(chainingIterators.hasNext());
    }

    @Test
    void testFromXmlWithUniParcRepresentativeAndSeed() {
        UniRefEntryLightConverter converter = new UniRefEntryLightConverter();
        String file = "/uniref/UniRef100_UPI0009BFC4AC.xml";
        InputStream is = UniRefEntryLightConverterTest.class.getResourceAsStream(file);

        assertNotNull(is);

        List<InputStream> iss = Collections.singletonList(is);

        XmlChainIterator<Entry, Entry> chainingIterators =
                new XmlChainIterator<>(
                        iss.iterator(), Entry.class, UNIREF_ROOT_ELEMENT, Function.identity());
        assertNotNull(chainingIterators);
        assertTrue(chainingIterators.hasNext());
        Entry xmlEntry = chainingIterators.next();
        assertNotNull(xmlEntry);
        UniRefEntryLight entry = converter.fromXml(xmlEntry);
        assertNotNull(entry);
        assertNotNull(entry.getId());
        assertEquals("UniRef100_UPI0009BFC4AC", entry.getId().getValue());

        assertNotNull(entry.getRepresentativeMember());
        RepresentativeMember repMember = entry.getRepresentativeMember();
        assertEquals("UPI0009BFC4AC", repMember.getMemberId());
        assertEquals(
                "NAD-dependent epimerase/dehydratase family protein", repMember.getProteinName());
        assertEquals("Streptomyces viridosporus", repMember.getOrganismName());

        assertEquals(
                "Cluster: NAD-dependent epimerase/dehydratase family protein", entry.getName());
        assertEquals("2018-09-12", entry.getUpdated().toString());
        assertEquals(UniRefType.UniRef100, entry.getEntryType());
        assertEquals("Streptomyces viridosporus", entry.getCommonTaxon().getScientificName());
        assertEquals(67581, entry.getCommonTaxon().getTaxonId());
        assertEquals("UPI0009BFC4AC", entry.getSeedId());

        assertTrue(entry.getGoTerms().isEmpty());

        assertTrue(entry.getMembers().contains("UPI0009BFC4AC,3"));

        assertEquals(entry.getMemberCount(), entry.getMembers().size());
        assertFalse(chainingIterators.hasNext());
    }

    @Test
    void testFromXmlInvalidId() {
        Entry xmlEntry = new Entry();
        xmlEntry.setId("INVALID");
        MemberType repMember = new MemberType();
        DbReferenceType dbReference = new DbReferenceType();
        dbReference.setId("123");
        repMember.setDbReference(dbReference);
        repMember.setSequence(new MemberType.Sequence());
        xmlEntry.setRepresentativeMember(repMember);

        UniRefEntryLightConverter converter = new UniRefEntryLightConverter();
        assertThrows(IllegalArgumentException.class, () -> converter.fromXml(xmlEntry));
    }

    @Test
    void testToXml() {
        UniRefEntryLightConverter converter = new UniRefEntryLightConverter();
        assertThrows(UnsupportedOperationException.class, () -> converter.toXml(null));
    }
}
