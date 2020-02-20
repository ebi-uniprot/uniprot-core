package org.uniprot.core.cv.keyword.builder;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.keyword.KeywordGeneOntology;

class KeywordGeneOntologyImplTest {

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
        KeywordGeneOntology go = go(this.id, this.term);
        Assertions.assertEquals(this.id, go.getGoId());
        Assertions.assertEquals(this.term, go.getGoTerm());
    }

    @Test
    void testValueEqual() {
        KeywordGeneOntology go1 = go(this.id, this.term);
        KeywordGeneOntology go2 = go(this.id, this.term);
        Assertions.assertTrue(go1.equals(go2));
        Assertions.assertTrue(go1.hashCode() == go2.hashCode());
    }

    @Test
    void testRefEqual() {
        KeywordGeneOntology go1 = go(this.id, this.term);
        Assertions.assertTrue(go1.equals(go1));
        Assertions.assertTrue(go1.hashCode() == go1.hashCode());
    }

    @Test
    void testEqualWithNull() {
        KeywordGeneOntology go1 = go(this.id, this.term);
        Assertions.assertFalse(go1.equals(null));
    }

    public static KeywordGeneOntology go(String id, String term) {
        return new KeywordGeneOntologyBuilder().id(id).term(term).build();
    }
}
