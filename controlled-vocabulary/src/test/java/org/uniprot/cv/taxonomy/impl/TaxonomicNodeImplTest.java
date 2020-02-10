package org.uniprot.cv.taxonomy.impl;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uniprot.cv.taxonomy.TaxonomicNode;

public class TaxonomicNodeImplTest {
    private String random;
    private int id;
    private String scientificName;
    private String commonName;
    private String synonymName;
    private String mnemonic;
    private TaxonomicNode parent;

    @BeforeEach
    void setUp() {
        this.random = UUID.randomUUID().toString();
        this.id = ThreadLocalRandom.current().nextInt();
        this.scientificName = "sn-" + this.random;
        this.commonName = "cn-" + this.random;
        this.synonymName = "syn-" + this.random;
        this.mnemonic = "mn-" + this.random;
        this.parent =
                new TaxonomicNodeImpl.Builder(this.id - 1, this.scientificName + "parent").build();
    }

    @Test
    void testCreateObject() {
        TaxonomicNode node =
                createTaxonomicNode(
                        this.id,
                        this.scientificName,
                        this.commonName,
                        this.synonymName,
                        this.mnemonic,
                        null);
        Assertions.assertNotNull(node);
        Assertions.assertEquals(this.id, node.id());
        Assertions.assertEquals(this.scientificName, node.scientificName());
        Assertions.assertEquals(this.commonName, node.commonName());
        Assertions.assertEquals(this.synonymName, node.synonymName());
        Assertions.assertEquals(this.mnemonic, node.mnemonic());
        Assertions.assertNull(node.parent());
        Assertions.assertFalse(node.hasParent());
    }

    @Test
    void testCreateObjectWithParent() {
        TaxonomicNode node =
                createTaxonomicNode(
                        this.id,
                        this.scientificName,
                        this.commonName,
                        this.synonymName,
                        this.mnemonic,
                        this.parent);
        Assertions.assertNotNull(node);
        Assertions.assertEquals(this.id, node.id());
        Assertions.assertEquals(this.scientificName, node.scientificName());
        Assertions.assertEquals(this.commonName, node.commonName());
        Assertions.assertEquals(this.synonymName, node.synonymName());
        Assertions.assertEquals(this.mnemonic, node.mnemonic());
        Assertions.assertNotNull(node.parent());
        Assertions.assertTrue(node.hasParent());
    }

    @Test
    void testToString() {
        TaxonomicNode node =
                createTaxonomicNode(
                        this.id,
                        this.scientificName,
                        this.commonName,
                        this.synonymName,
                        this.mnemonic,
                        this.parent);
        String str =
                "TaxonomicNodeImpl{"
                        + "id="
                        + this.id
                        + ", scientificName='"
                        + this.scientificName
                        + '\''
                        + ", commonName='"
                        + this.commonName
                        + '\''
                        + ", synonymName='"
                        + this.synonymName
                        + '\''
                        + ", parent="
                        + this.parent
                        + '}';
        Assertions.assertEquals(str, node.toString());
    }

    @Test
    void testValueEqual() {
        TaxonomicNode n1 = createTaxonomicNode();
        TaxonomicNode n2 = createTaxonomicNode();
        Assertions.assertTrue(n1.equals(n2));
        Assertions.assertTrue(n1.hashCode() == n2.hashCode());
    }

    @Test
    void testRefEqual() {
        TaxonomicNode n1 = createTaxonomicNode();
        Assertions.assertTrue(n1.equals(n1));
        Assertions.assertTrue(n1.hashCode() == n1.hashCode());
    }

    @Test
    void testEqualWithNull() {
        TaxonomicNode n1 = createTaxonomicNode();
        Assertions.assertFalse(n1.equals(null));
    }

    @Test
    void testValueNotEqual() {
        TaxonomicNode n1 = createTaxonomicNode();
        this.parent = null;
        TaxonomicNode n2 = createTaxonomicNode();
        Assertions.assertFalse(n1.equals(n2));
    }

    private TaxonomicNode createTaxonomicNode() {
        return createTaxonomicNode(
                this.id,
                this.scientificName,
                this.commonName,
                this.synonymName,
                this.mnemonic,
                this.parent);
    }

    public static TaxonomicNode createTaxonomicNode(
            int id, String sn, String cn, String sy, String mn, TaxonomicNode parent) {
        TaxonomicNodeImpl.Builder builder = new TaxonomicNodeImpl.Builder(id, sn);
        builder.withCommonName(cn).withSynonymName(sy).withMnemonic(mn).childOf(parent);
        return builder.build();
    }
}
