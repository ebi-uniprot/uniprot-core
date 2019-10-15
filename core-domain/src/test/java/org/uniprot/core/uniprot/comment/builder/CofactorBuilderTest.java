package org.uniprot.core.uniprot.comment.builder;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.createEvidence;
import static org.uniprot.core.ObjectsForTests.createEvidences;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.comment.Cofactor;

class CofactorBuilderTest {
    @Test
    void canCreateBuilderFromInstance() {
        Cofactor obj = new CofactorBuilder().build();
        CofactorBuilder builder = new CofactorBuilder().from(obj);
        assertNotNull(builder);
    }

    @Test
    void defaultBuild_objsAreEqual() {
        Cofactor obj = new CofactorBuilder().build();
        Cofactor obj2 = new CofactorBuilder().build();
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }

    @Test
    void canAddSingleEvidence() {
        Cofactor obj = new CofactorBuilder().addEvidence(createEvidence()).build();
        assertNotNull(obj.getEvidences());
        assertFalse(obj.getEvidences().isEmpty());
        assertTrue(obj.hasEvidences());
    }

    @Test
    void nullEvidence_willBeIgnore() {
        Cofactor obj = new CofactorBuilder().addEvidence(null).build();
        assertNotNull(obj.getEvidences());
        assertTrue(obj.getEvidences().isEmpty());
        assertFalse(obj.hasEvidences());
    }

    @Test
    void evidences_willConvertUnModifiable_toModifiable() {
        Cofactor obj =
                new CofactorBuilder()
                        .evidences(Collections.emptyList())
                        .addEvidence(createEvidence())
                        .build();
        assertNotNull(obj.getEvidences());
        assertFalse(obj.getEvidences().isEmpty());
        assertTrue(obj.hasEvidences());
    }

    @Test
    void canAddListEvidences() {
        Cofactor obj = new CofactorBuilder().evidences(createEvidences()).build();
        assertNotNull(obj.getEvidences());
        assertFalse(obj.getEvidences().isEmpty());
        assertTrue(obj.hasEvidences());
    }
}
