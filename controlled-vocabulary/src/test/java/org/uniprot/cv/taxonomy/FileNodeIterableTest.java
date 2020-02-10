package org.uniprot.cv.taxonomy;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FileNodeIterableTest {

    @Test
    void testIterable() throws URISyntaxException {
        URL url = ClassLoader.getSystemClassLoader().getResource("taxonomy/taxonomy.dat");
        File taxonomyFile = new File(url.toURI());
        FileNodeIterable iterable = new FileNodeIterable(taxonomyFile);
        int nodeCount = 0;
        for (TaxonomicNode node : iterable) {
            verifyTaxonomicNode(node);
            nodeCount++;
        }

        Assertions.assertEquals(66, nodeCount);
    }

    @Test
    void testIterableWithNullFile() {
        IllegalArgumentException thrown =
                Assertions.assertThrows(
                        IllegalArgumentException.class, () -> new FileNodeIterable(null));
        Assertions.assertEquals("Taxonomy file is null", thrown.getMessage());
    }

    @Test
    void testIterableWithEmptySeparator() throws URISyntaxException {
        URL url = ClassLoader.getSystemClassLoader().getResource("taxonomy/taxonomy.dat");
        File taxonomyFile = new File(url.toURI());
        IllegalArgumentException thrown =
                Assertions.assertThrows(
                        IllegalArgumentException.class,
                        () -> new FileNodeIterable(taxonomyFile, "", null));
        Assertions.assertTrue(thrown.getMessage().contains("Invalid field separator:"));
    }

    @Test
    void testIterableWithNullPlaceholder() throws URISyntaxException {
        URL url = ClassLoader.getSystemClassLoader().getResource("taxonomy/taxonomy.dat");
        File taxonomyFile = new File(url.toURI());
        IllegalArgumentException thrown =
                Assertions.assertThrows(
                        IllegalArgumentException.class,
                        () -> new FileNodeIterable(taxonomyFile, "\t", null));
        Assertions.assertTrue(thrown.getMessage().contains("Invalid null placeholde"));
    }

    private void verifyTaxonomicNode(TaxonomicNode node) {
        Assertions.assertNotNull(node);
        Assertions.assertNotNull(node.id());
    }
}
