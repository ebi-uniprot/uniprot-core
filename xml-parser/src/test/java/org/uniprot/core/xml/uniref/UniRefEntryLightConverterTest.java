package org.uniprot.core.xml.uniref;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.xml.uniref.UniRefEntryConverterTest.*;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniref.UniRefEntryLight;
import org.uniprot.core.uniref.UniRefType;
import org.uniprot.core.xml.XmlChainIterator;
import org.uniprot.core.xml.jaxb.uniref.Entry;

/**
 * @author lgonzales
 * @since 06/07/2020
 */
class UniRefEntryLightConverterTest {

    @Test
    void testFromXml() throws Exception {
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
        assertEquals("UniRef50_Q9EPS7", entry.getId().getValue());
        assertEquals("Cluster: Pheromone receptor V3R6", entry.getName());
        assertEquals("2014-11-26", entry.getUpdated().toString());
        assertEquals(UniRefType.UniRef50, entry.getEntryType());
        assertEquals("Muroidea", entry.getCommonTaxon());
        assertEquals(337687, entry.getCommonTaxonId());
        // assertTrue(entry.hasMemberUniParcIDs());
        assertTrue(entry.getMembers().contains("Q8R2B4"));
        assertTrue(entry.getMembers().contains("UPI0000DBE4A9"));

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
    void testInvalidId() throws Exception {
        Entry xmlEntry = new Entry();
        xmlEntry.setId("INVALID");
        UniRefEntryLightConverter converter = new UniRefEntryLightConverter();
        assertThrows(IllegalArgumentException.class, () -> converter.fromXml(xmlEntry));
    }

    @Test
    void testToXml() {
        UniRefEntryLightConverter converter = new UniRefEntryLightConverter();
        assertThrows(UnsupportedOperationException.class, () -> converter.toXml(null));
    }
}
