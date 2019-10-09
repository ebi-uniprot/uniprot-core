package org.uniprot.core.uniprot.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.uniprot.core.gene.*;
import org.uniprot.core.uniprot.builder.*;

class GeneImplTest {

    private Gene gene =
            new GeneImpl(
                    new GeneNameBuilder().value("name").build(),
                    Collections.emptyList(),
                    Collections.emptyList(),
                    Collections.emptyList());

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        Gene obj = new GeneBuilder().from(gene).build();
        assertTrue(gene.equals(obj) && obj.equals(gene));
        assertEquals(gene.hashCode(), obj.hashCode());
    }

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        Gene obj = new GeneImpl();
        assertNotNull(obj);
        assertTrue(obj.getOrderedLocusNames().isEmpty());
        assertTrue(obj.getOrfNames().isEmpty());
        assertTrue(obj.getSynonyms().isEmpty());
    }

    @Test
    void toStringWithNameOnly() {
        assertEquals("Name=name;", gene.toString());
    }

    @Test
    void toStringWithNameAndSynonyms() {
        Gene obj =
                new GeneBuilder()
                        .from(gene)
                        .synonyms(
                                Collections.singletonList(
                                        new GeneNameSynonymBuilder().value("gs").build()))
                        .build();
        assertEquals("Name=name; Synonyms=gs;", obj.toString());
    }

    @Test
    void toStringWithNameAndOrderedLocusName() {
        Gene obj =
                new GeneBuilder()
                        .from(gene)
                        .orderedLocusNames(
                                Collections.singletonList(
                                        new OrderedLocusNameBuilder().value("oln").build()))
                        .build();
        assertEquals("Name=name; OrderedLocusNames=oln;", obj.toString());
    }

    @Test
    void toStringWithNameAndORFName() {
        Gene obj =
                new GeneBuilder()
                        .from(gene)
                        .orfNames(
                                Collections.singletonList(
                                        new ORFNameBuilder().value("orf").build()))
                        .build();
        assertEquals("Name=name; ORFNames=orf;", obj.toString());
    }

    @Test
    void needDefaultConstructorForJsonDeserialization_ORFNameImpl() {
        ORFName obj = new GeneImpl.ORFNameImpl();
        assertNotNull(obj);
    }

    @Test
    void needDefaultConstructorForJsonDeserialization_OrderedLocusNameImpl() {
        OrderedLocusName obj = new GeneImpl.OrderedLocusNameImpl();
        assertNotNull(obj);
    }

    @Test
    void needDefaultConstructorForJsonDeserialization_GeneNameSynonymImpl() {
        GeneNameSynonym obj = new GeneImpl.GeneNameSynonymImpl();
        assertNotNull(obj);
    }

    @Test
    void needDefaultConstructorForJsonDeserialization_GeneNameImpl() {
        GeneName obj = new GeneImpl.GeneNameImpl();
        assertNotNull(obj);
    }
}
