package org.uniprot.core.uniprot.comment.builder;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.createEvidences;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.comment.IsoformName;

class IsoformNameBuilderTest {
    @Test
    void canCreateBuilderFromInstance() {
        IsoformName obj = new IsoformNameBuilder().build();
        IsoformNameBuilder builder = IsoformNameBuilder.from(obj);
        assertNotNull(builder);
    }

    @Test
    void canAddValue() {
        IsoformName obj = new IsoformNameBuilder().value("val").build();
        assertEquals("val", obj.getValue());
    }

    @Test
    void canAddEvidences() {
        IsoformName obj = new IsoformNameBuilder().evidencesSet(createEvidences()).build();
        assertEquals(createEvidences(), obj.getEvidences());
    }

    @Test
    void defaultBuild_objsAreEqual() {
        IsoformName obj = new IsoformNameBuilder().build();
        IsoformName obj2 = new IsoformNameBuilder().build();
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }
}
