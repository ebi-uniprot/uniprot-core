package org.uniprot.core.cv.taxonomy.impl;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.taxonomy.FileNodeIterable;
import org.uniprot.core.cv.taxonomy.TaxonomicNode;

public class TaxonomyMapRepoTest {

    private FileNodeIterable fileNodeIterable;

    @BeforeEach
    void setUp() throws URISyntaxException {
        URL url = ClassLoader.getSystemClassLoader().getResource("taxonomy/taxonomy.dat");
        File taxonomyFile = new File(url.toURI());
        this.fileNodeIterable = new FileNodeIterable(taxonomyFile);
    }

    @Test
    void testRetrieveNodeUsingTaxId() {
        TaxonomyMapRepo repo = new TaxonomyMapRepo(this.fileNodeIterable);
        Optional<TaxonomicNode> node = repo.retrieveNodeUsingTaxID(2759);
        Assertions.assertTrue(node.isPresent());
        Assertions.assertEquals(2759, node.get().id());
        TaxonomicNode parent = node.get().parent();
        Assertions.assertNotNull(parent);
        Assertions.assertEquals(1, parent.id());
        Assertions.assertEquals("Eukaryota", node.get().scientificName());
        Assertions.assertEquals("eucaryotes", node.get().commonName());
        Assertions.assertEquals("9EUKA", node.get().mnemonic());
        Assertions.assertNull(node.get().synonymName());
    }

    @Test
    void testRetrieveNodeUsingTaxIdWithoutParent() {
        TaxonomyMapRepo repo = new TaxonomyMapRepo(this.fileNodeIterable);
        Optional<TaxonomicNode> node = repo.retrieveNodeUsingTaxID(1);
        Assertions.assertTrue(node.isPresent());
        Assertions.assertEquals(1, node.get().id());
        TaxonomicNode parent = node.get().parent();
        Assertions.assertNull(parent);
        Assertions.assertFalse(node.get().hasParent());
        Assertions.assertEquals("root", node.get().scientificName());
        Assertions.assertNull(node.get().commonName());
        Assertions.assertEquals("9ZZZZ", node.get().mnemonic());
        Assertions.assertNull(node.get().synonymName());
    }
}
