package org.uniprot.cv.keyword.impl;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uniprot.cv.keyword.GeneOntology;

public class GeneOntologyImplTest {

    private String random;
    private String id;
    private String term;

    @BeforeEach
    void setUp() {
        this.random = UUID.randomUUID().toString();
        this.id = "id-" + this.random;
        this.term = "term-" + this.random;
    }

    @Test
    void testCreateGO() {
        GeneOntology go = createGeneOntology(this.id, this.term);
        Assertions.assertEquals(this.id, go.getGoId());
        Assertions.assertEquals(this.term, go.getGoTerm());
    }

    @Test
    void testValueEqual() {
        GeneOntology go1 = createGeneOntology(this.id, this.term);
        GeneOntology go2 = createGeneOntology(this.id, this.term);
        Assertions.assertTrue(go1.equals(go2));
        Assertions.assertTrue(go1.hashCode() == go2.hashCode());
    }

    @Test
    void testRefEqual() {
        GeneOntology go1 = createGeneOntology(this.id, this.term);
        Assertions.assertTrue(go1.equals(go1));
        Assertions.assertTrue(go1.hashCode() == go1.hashCode());
    }

    @Test
    void testEqualWithNull() {
        GeneOntology go1 = createGeneOntology(this.id, this.term);
        Assertions.assertFalse(go1.equals(null));
    }

    public static GeneOntology createGeneOntology(String id, String term) {
        return GeneOntologyImpl.create(id, term);
    }
}
