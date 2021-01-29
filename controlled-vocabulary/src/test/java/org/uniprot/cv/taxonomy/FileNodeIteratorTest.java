package org.uniprot.cv.taxonomy;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileNodeIteratorTest {
    private static final String FIELD_SEPARATOR = "\t";
    private static final String NULL_PLACEHOLDER = "\\N";

    @Test
    void testFileNodeIterator() throws URISyntaxException {
        URL url = ClassLoader.getSystemClassLoader().getResource("taxonomy/taxonomy.dat");
        File taxonomyFile = new File(url.toURI());
        FileNodeIterator iterator =
                new FileNodeIterator(taxonomyFile, FIELD_SEPARATOR, NULL_PLACEHOLDER);
        Map<Integer, TaxonomicNode> resultMap = new HashMap<>();
        while (iterator.hasNext()) {
            TaxonomicNode node = iterator.next();
            verifyTaxonomicNode(node);
            resultMap.put(node.id(), node);
        }

        assertEquals(66, resultMap.size());
        TaxonomicNode bacteria = resultMap.get(2);
        assertNotNull(bacteria);
        assertEquals(2, bacteria.id());
        assertEquals("Bacteria", bacteria.scientificName());
        assertEquals("eubacteria", bacteria.commonName());
        assertNull(bacteria.synonymName());
        assertFalse(bacteria.hidden());

        TaxonomicNode avian = resultMap.get(269446);
        assertNotNull(avian);
        assertEquals(269446, avian.id());
        assertEquals("Avian leukosis virus RSA", avian.scientificName());
        assertEquals("RSV-SRA", avian.commonName());
        assertEquals("Rous sarcoma virus (strain Schmidt-Ruppin A)", avian.synonymName());
        assertTrue( avian.hidden());
    }

    @Test
    void testFileNodeIteratorWithNonExistingFile() {
        File taxonomyFile = new File("non/existing/file");

        IllegalStateException thrown =
                Assertions.assertThrows(
                        IllegalStateException.class,
                        () ->
                                new FileNodeIterator(
                                        taxonomyFile, FIELD_SEPARATOR, NULL_PLACEHOLDER));

        assertEquals(
                "An exception occurred whilst accessing the taxonomy file", thrown.getMessage());
    }

    @Test
    void testFileNodeIteratorNextAfterFullScan() throws URISyntaxException {
        URL url = ClassLoader.getSystemClassLoader().getResource("taxonomy/taxonomy.dat");
        File taxonomyFile = new File(url.toURI());
        FileNodeIterator iterator =
                new FileNodeIterator(taxonomyFile, FIELD_SEPARATOR, NULL_PLACEHOLDER);
        while (iterator.hasNext() && iterator.next() != null) ;

        // try to call next
        NoSuchElementException thrown =
                Assertions.assertThrows(NoSuchElementException.class, iterator::next);
        assertEquals("No elements left in iterator", thrown.getMessage());
    }

    private void verifyTaxonomicNode(TaxonomicNode node) {
        Assertions.assertNotNull(node);
        Assertions.assertTrue(node.id() > 0);
        Assertions.assertNotNull(node.scientificName());
    }
}
