package org.uniprot.core.cv.go.builder;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.go.GeneOntologyEntry;

class GeneOntologyEntryImplTest {

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
        GeneOntologyEntry go = go(this.id, this.term);
        Assertions.assertEquals(this.id, go.getId());
        Assertions.assertEquals(this.term, go.getName());
    }

    @Test
    void testValueEqual() {
        GeneOntologyEntry go1 = go(this.id, this.term);
        GeneOntologyEntry go2 = go(this.id, this.term);
        Assertions.assertTrue(go1.equals(go2));
        Assertions.assertTrue(go1.hashCode() == go2.hashCode());
    }

    @Test
    void testRefEqual() {
        GeneOntologyEntry go1 = go(this.id, this.term);
        Assertions.assertTrue(go1.equals(go1));
        Assertions.assertTrue(go1.hashCode() == go1.hashCode());
    }

    @Test
    void testEqualWithNull() {
        GeneOntologyEntry go1 = go(this.id, this.term);
        Assertions.assertFalse(go1.equals(null));
    }

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        GeneOntologyEntry obj = new GeneOntologyEntryImpl();
        assertNotNull(obj);
    }

    private static GeneOntologyEntry go(String id, String term) {
        return new GeneOntologyEntryBuilder().id(id).name(term).build();
    }
}
