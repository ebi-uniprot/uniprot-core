package org.uniprot.core.uniprot.comment.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.createEvidences;

import org.junit.jupiter.api.Test;
import org.uniprot.core.CrossReference;
import org.uniprot.core.impl.CrossReferenceImpl;
import org.uniprot.core.uniprot.comment.Disease;
import org.uniprot.core.uniprot.comment.DiseaseDatabase;
import org.uniprot.core.uniprot.comment.builder.DiseaseBuilder;

class DiseaseImplTest {
    @Test
    void needDefaultConstructorForJsonDeserialization() {
        Disease obj = new DiseaseImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        CrossReference<DiseaseDatabase> reference =
                new CrossReferenceImpl<>(DiseaseDatabase.MIM, "referenceId");
        Disease impl = new DiseaseImpl("id", "acc", "act", "des", reference, createEvidences());
        Disease obj = DiseaseBuilder.from(impl).build();

        assertTrue(impl.hasDefinedDisease());
        assertTrue(impl.hasAcronym());
        assertTrue(impl.hasDescription());
        assertTrue(impl.hasDiseaseAccession());
        assertTrue(impl.hasDiseaseId());
        assertTrue(impl.hasReference());
        assertTrue(impl.hasEvidences());

        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
