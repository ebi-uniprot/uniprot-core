package org.uniprot.core.uniprot.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.createEvidences;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.uniprot.core.gene.*;

class GeneImplTest {

    private Gene gene =
            new GeneImpl(
                    new GeneNameBuilder().value("name").build(),
                    Collections.emptyList(),
                    Collections.emptyList(),
                    Collections.emptyList());

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        Gene obj = GeneBuilder.from(gene).build();
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
                GeneBuilder.from(gene)
                        .synonymsSet(
                                Collections.singletonList(
                                        new GeneNameSynonymBuilder().value("gs").build()))
                        .build();
        assertEquals("Name=name; Synonyms=gs;", obj.toString());
    }

    @Test
    void toStringWithNameAndOrderedLocusName() {
        Gene obj =
                GeneBuilder.from(gene)
                        .orderedLocusNamesSet(
                                Collections.singletonList(
                                        new OrderedLocusNameBuilder().value("oln").build()))
                        .build();
        assertEquals("Name=name; OrderedLocusNames=oln;", obj.toString());
    }

    @Test
    void toStringWithNameAndORFName() {
        Gene obj =
                GeneBuilder.from(gene)
                        .orfNamesSet(
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
    void builderFrom_constructorImp_shouldCreate_equalObject_ORFNameImpl() {
        ORFName impl = new GeneImpl.ORFNameImpl("val", createEvidences());
        ORFName obj = ORFNameBuilder.from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }

    @Test
    void needDefaultConstructorForJsonDeserialization_OrderedLocusNameImpl() {
        OrderedLocusName obj = new GeneImpl.OrderedLocusNameImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject_OrderedLocusNameImpl() {
        OrderedLocusName impl = new GeneImpl.OrderedLocusNameImpl("val", createEvidences());
        OrderedLocusName obj = OrderedLocusNameBuilder.from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }

    @Test
    void needDefaultConstructorForJsonDeserialization_GeneNameSynonymImpl() {
        GeneNameSynonym obj = new GeneImpl.GeneNameSynonymImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject_GeneNameSynonymImpl() {
        GeneNameSynonym impl = new GeneImpl.GeneNameSynonymImpl("val", createEvidences());
        GeneNameSynonym obj = GeneNameSynonymBuilder.from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }

    @Test
    void needDefaultConstructorForJsonDeserialization_GeneNameImpl() {
        GeneName obj = new GeneImpl.GeneNameImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject_GeneNameImpl() {
        GeneName gene = new GeneImpl.GeneNameImpl("val", createEvidences());
        GeneName obj = GeneNameBuilder.from(gene).build();
        assertTrue(gene.equals(obj) && obj.equals(gene));
        assertEquals(gene.hashCode(), obj.hashCode());
    }
}
