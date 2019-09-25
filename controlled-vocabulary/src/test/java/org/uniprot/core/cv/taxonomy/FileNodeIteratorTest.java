package org.uniprot.core.cv.taxonomy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.NoSuchElementException;

public class FileNodeIteratorTest {
    private static final String FIELD_SEPARATOR = "\t";
    private static final String NULL_PLACEHOLDER = "\\N";

    @Test
    void testFileNodeIterator() throws URISyntaxException {
        URL url = ClassLoader.getSystemClassLoader().getResource("taxonomy/taxonomy.dat");
        File taxonomyFile = new File(url.toURI());
        FileNodeIterator iterator = new FileNodeIterator(taxonomyFile, FIELD_SEPARATOR, NULL_PLACEHOLDER);
        int nodeCount = 0;
        while (iterator.hasNext()){
            TaxonomicNode node = iterator.next();
            verifyTaxonomicNode(node);
            nodeCount++;
        }

        Assertions.assertEquals(66, nodeCount);
    }

    @Test
    void testFileNodeIteratorWithNonExistingFile() {
        File taxonomyFile = new File("non/existing/file");

        IllegalStateException thrown = Assertions.assertThrows(IllegalStateException.class,
                () -> new FileNodeIterator(taxonomyFile, FIELD_SEPARATOR, NULL_PLACEHOLDER));

        Assertions.assertEquals("An exception occurred whilst accessing the taxonomy file", thrown.getMessage());
    }

    @Test
    void testFileNodeIteratorNextAfterFullScan() throws URISyntaxException {
        URL url = ClassLoader.getSystemClassLoader().getResource("taxonomy/taxonomy.dat");
        File taxonomyFile = new File(url.toURI());
        FileNodeIterator iterator = new FileNodeIterator(taxonomyFile, FIELD_SEPARATOR, NULL_PLACEHOLDER);
        while (iterator.hasNext() && iterator.next() != null);

        // try to call next
        NoSuchElementException thrown = Assertions.assertThrows(NoSuchElementException.class, () -> iterator.next());
        Assertions.assertEquals("No elements left in iterator", thrown.getMessage());

    }

    private void verifyTaxonomicNode(TaxonomicNode node) {
        Assertions.assertNotNull(node);
        Assertions.assertNotNull(node.id());
    }

}
