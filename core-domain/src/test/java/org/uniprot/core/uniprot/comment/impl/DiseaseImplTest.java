package org.uniprot.core.uniprot.comment.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.createEvidences;
import static org.uniprot.core.ObjectsForTests.crossReference;

import org.junit.jupiter.api.Test;
import org.uniprot.core.CrossReference;
import org.uniprot.core.uniprot.comment.Disease;
import org.uniprot.core.uniprot.comment.DiseaseDatabase;

class DiseaseImplTest {
    @Test
    void needDefaultConstructorForJsonDeserialization() {
        Disease obj = new DiseaseImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        CrossReference<DiseaseDatabase> reference =
                crossReference(DiseaseDatabase.MIM, "referenceId");
        Disease impl = new DiseaseImpl("id", "acc", "act", "des", reference, createEvidences());
        Disease obj = DiseaseBuilder.from(impl).build();

        assertTrue(impl.hasDefinedDisease());
        assertTrue(impl.hasAcronym());
        assertTrue(impl.hasDescription());
        assertTrue(impl.hasDiseaseAccession());
        assertTrue(impl.hasDiseaseId());
        assertTrue(impl.hasDiseaseCrossReference());
        assertTrue(impl.hasEvidences());

        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
