package org.uniprot.core.uniprotkb.comment.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.createEvidences;
import static org.uniprot.core.ObjectsForTests.crossReference;

import org.junit.jupiter.api.Test;
import org.uniprot.core.CrossReference;
import org.uniprot.core.uniprotkb.comment.Cofactor;
import org.uniprot.core.uniprotkb.comment.CofactorDatabase;
import org.uniprot.core.uniprotkb.evidence.Evidence;

import java.util.Collections;
import java.util.List;

class CofactorImplTest {
    private final CrossReference<CofactorDatabase> reference =
            crossReference(CofactorDatabase.CHEBI, "ChEBI:213");

    @Test
    void testCofactorImpl() {
        String name = "Some cofactor";
        List<Evidence> evidences = createEvidences();
        Cofactor cofactor =
                new CofactorBuilder()
                        .name(name)
                        .cofactorCrossReference(reference)
                        .evidencesSet(createEvidences())
                        .build();
        assertEquals(name, cofactor.getName());
        assertEquals(reference, cofactor.getCofactorCrossReference());
        assertEquals(evidences, cofactor.getEvidences());
    }

    @Test
    void testCofactorImplNoEvidence() {
        String name = "Some cofactor";
        List<Evidence> evidences = Collections.emptyList();
        Cofactor cofactor =
                new CofactorBuilder()
                        .name(name)
                        .cofactorCrossReference(reference)
                        .evidencesSet(evidences)
                        .build();
        assertEquals(name, cofactor.getName());
        assertEquals(reference, cofactor.getCofactorCrossReference());
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
        assertTrue(impl.hasCofactorCrossReference());

        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
