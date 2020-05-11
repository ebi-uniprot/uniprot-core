package org.uniprot.core.cv.go.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.go.GeneOntologyEntry;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

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

    @Test
    void canCompareWithIds() {
        GeneOntologyEntry go1 = go("go1", null);
        GeneOntologyEntry go2 = go("go2", null);
        List<GeneOntologyEntry> geneOntologyEntries = Arrays.asList(go1, go2);
        Collections.sort(geneOntologyEntries);

        assertEquals(go2, geneOntologyEntries.get(0));
    }

    private static GeneOntologyEntry go(String id, String term) {
        return new GeneOntologyEntryBuilder().id(id).name(term).build();
    }
}
