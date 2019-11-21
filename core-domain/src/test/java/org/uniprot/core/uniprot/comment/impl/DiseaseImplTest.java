package org.uniprot.core.uniprot.comment.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.createEvidences;

import org.junit.jupiter.api.Test;
import org.uniprot.core.DBCrossReference;
import org.uniprot.core.impl.DBCrossReferenceImpl;
import org.uniprot.core.uniprot.comment.Disease;
import org.uniprot.core.uniprot.comment.DiseaseReferenceType;
import org.uniprot.core.uniprot.comment.builder.DiseaseBuilder;

class DiseaseImplTest {
    @Test
    void needDefaultConstructorForJsonDeserialization() {
        Disease obj = new DiseaseImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        DBCrossReference<DiseaseReferenceType> reference =
                new DBCrossReferenceImpl<>(DiseaseReferenceType.MIM, "referenceId");
        Disease impl = new DiseaseImpl("id", "acc", "act", "des", reference, createEvidences());
        Disease obj = new DiseaseBuilder().from(impl).build();

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
