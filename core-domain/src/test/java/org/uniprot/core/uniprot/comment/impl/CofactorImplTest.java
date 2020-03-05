package org.uniprot.core.uniprot.comment.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.createEvidences;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.CrossReference;
import org.uniprot.core.impl.CrossReferenceImpl;
import org.uniprot.core.uniprot.comment.Cofactor;
import org.uniprot.core.uniprot.comment.CofactorDatabase;
import org.uniprot.core.uniprot.comment.builder.CofactorBuilder;
import org.uniprot.core.uniprot.evidence.Evidence;

class CofactorImplTest {
    private final CrossReference<CofactorDatabase> reference =
            new CrossReferenceImpl<>(CofactorDatabase.CHEBI, "ChEBI:213");

    @Test
    void testCofactorImpl() {
        String name = "Some cofactor";
        List<Evidence> evidences = createEvidences();
        Cofactor cofactor =
                new CofactorBuilder()
                        .name(name)
                        .reference(reference)
                        .evidencesSet(createEvidences())
                        .build();
        assertEquals(name, cofactor.getName());
        assertEquals(reference, cofactor.getCofactorReference());
        assertEquals(evidences, cofactor.getEvidences());
    }

    @Test
    void testCofactorImplNoEvidence() {
        String name = "Some cofactor";
        List<Evidence> evidences = Collections.emptyList();
        Cofactor cofactor =
                new CofactorBuilder()
                        .name(name)
                        .reference(reference)
                        .evidencesSet(evidences)
                        .build();
        assertEquals(name, cofactor.getName());
        assertEquals(reference, cofactor.getCofactorReference());
        assertEquals(evidences, cofactor.getEvidences());
    }

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        Cofactor obj = new CofactorImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        Cofactor impl = new CofactorImpl("abc", reference, createEvidences());
        Cofactor obj = CofactorBuilder.from(impl).build();

        assertTrue(impl.hasName());
        assertTrue(impl.hasCofactorReference());

        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
