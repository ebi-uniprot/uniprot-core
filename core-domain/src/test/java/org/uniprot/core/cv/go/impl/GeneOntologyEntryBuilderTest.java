package org.uniprot.core.cv.go.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.go.GeneOntologyEntry;
import org.uniprot.core.cv.go.GoAspect;

/**
 * @author jluo
 * @date: 12 Aug 2019
 */
class GeneOntologyEntryBuilderTest {

    @Test
    void testType() {
        GoAspect type = GoAspect.FUNCTION;
        GeneOntologyEntry goTerm = new GeneOntologyEntryBuilder().aspect(type).build();
        assertEquals(type, goTerm.getAspect());
    }

    @Test
    void testTypeId() {
        GoAspect type = GoAspect.COMPONENT;
        String id = "GO:0044444";
        GeneOntologyEntry goTerm = new GeneOntologyEntryBuilder().aspect(type).id(id).build();
        assertEquals(type, goTerm.getAspect());
        assertEquals(id, goTerm.getId());
    }

    @Test
    void testFrom() {
        GoAspect type = GoAspect.COMPONENT;
        String id = "GO:0044444";
        GeneOntologyEntry goTerm = new GeneOntologyEntryBuilder().aspect(type).id(id).build();

        GeneOntologyEntry goTerm2 = GeneOntologyEntryBuilder.from(goTerm).build();
        assertEquals(goTerm, goTerm2);
    }

    @Test
    void twoDifferentObjects_defaultBuild_equal() {
        GeneOntologyEntry goTerm1 = new GeneOntologyEntryBuilder().build();
        GeneOntologyEntry goTerm2 = new GeneOntologyEntryBuilder().build();
        assertTrue(goTerm1.equals(goTerm2) && goTerm2.equals(goTerm1));
        assertEquals(goTerm1.hashCode(), goTerm2.hashCode());
    }

    @Test
    void canAddSingleAn() {
        GeneOntologyEntry goTerm =
                new GeneOntologyEntryBuilder().ancestorsAdd(new GeneOntologyEntryImpl()).build();

        assertFalse(goTerm.getAncestors().isEmpty());
        assertEquals(1, goTerm.getAncestors().size());
    }
}
