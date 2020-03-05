package org.uniprot.core.uniprot.comment.builder;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.createEvidence;
import static org.uniprot.core.ObjectsForTests.createEvidences;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.CrossReference;
import org.uniprot.core.impl.CrossReferenceImpl;
import org.uniprot.core.uniprot.comment.Disease;
import org.uniprot.core.uniprot.comment.DiseaseDatabase;
import org.uniprot.core.uniprot.evidence.Evidence;

class DiseaseBuilderTest {
    @Test
    void testNewInstance() {
        DiseaseBuilder builder1 = new DiseaseBuilder();
        DiseaseBuilder builder2 = new DiseaseBuilder();
        assertNotNull(builder1);
        assertNotNull(builder2);
        assertFalse(builder1 == builder2);
    }

    @Test
    void testSetDiseaseId() {
        DiseaseBuilder builder = new DiseaseBuilder();
        String diseaseId = "someId";
        Disease disease = builder.diseaseId(diseaseId).build();
        assertEquals(diseaseId, disease.getDiseaseId());
        assertNull(disease.getDescription());
        assertNull(disease.getReference());
        assertNull(disease.getAcronym());
    }

    @Test
    void testSetAcronym() {
        DiseaseBuilder builder = new DiseaseBuilder();
        String diseaseId = "someId";
        Disease disease = builder.diseaseId(diseaseId).acronym("someAcron").build();
        assertEquals(diseaseId, disease.getDiseaseId());
        assertNull(disease.getDescription());
        assertNull(disease.getReference());
        assertEquals("someAcron", disease.getAcronym());
    }

    @Test
    void testSetDescription() {
        DiseaseBuilder builder = new DiseaseBuilder();

        List<Evidence> evidences = createEvidences();

        String diseaseId = "someId";
        String diseaseDescription = "some description";
        Disease disease =
                builder.diseaseId(diseaseId)
                        .acronym("someAcron")
                        .description(diseaseDescription)
                        .evidencesSet(evidences)
                        .build();
        assertEquals(diseaseId, disease.getDiseaseId());
        assertEquals(diseaseDescription, disease.getDescription());
        assertNull(disease.getReference());
        assertEquals("someAcron", disease.getAcronym());
    }

    @Test
    void testSetReference() {
        DiseaseBuilder builder = new DiseaseBuilder();

        List<Evidence> evidences = createEvidences();

        String referenceId = "3124";
        CrossReference<DiseaseDatabase> reference =
                new CrossReferenceImpl<>(DiseaseDatabase.MIM, referenceId);
        String diseaseId = "someId";
        String diseaseDescription = "some description";
        Disease disease =
                builder.diseaseId(diseaseId)
                        .acronym("someAcron")
                        .description(diseaseDescription)
                        .evidencesSet(evidences)
                        .reference(reference)
                        .build();
        assertEquals(diseaseId, disease.getDiseaseId());
        assertEquals(diseaseDescription, disease.getDescription());
        assertEquals(reference, disease.getReference());
        assertEquals("someAcron", disease.getAcronym());
    }

    @Test
    void testCreateDiseaseReference() {
        DiseaseDatabase referenceType = DiseaseDatabase.MIM;
        String referenceId = "3124";
        CrossReference<DiseaseDatabase> reference =
                new CrossReferenceImpl<>(referenceType, referenceId);

        assertEquals(referenceType, reference.getDatabase());
        assertEquals(referenceId, reference.getId());
    }

    @Test
    void canAddSingleEvidence() {
        Disease obj = new DiseaseBuilder().evidencesAdd(createEvidence()).build();
        assertNotNull(obj.getEvidences());
        assertFalse(obj.getEvidences().isEmpty());
        assertTrue(obj.hasEvidences());
    }

    @Test
    void nullEvidence_willBeIgnore() {
        Disease obj = new DiseaseBuilder().evidencesAdd(null).build();
        assertNotNull(obj.getEvidences());
        assertTrue(obj.getEvidences().isEmpty());
        assertFalse(obj.hasEvidences());
    }

    @Test
    void canCreateBuilderFromInstance() {
        Disease obj = new DiseaseBuilder().build();
        DiseaseBuilder builder = DiseaseBuilder.from(obj);
        assertNotNull(builder);
    }

    @Test
    void defaultBuild_objsAreEqual() {
        Disease obj = new DiseaseBuilder().build();
        Disease obj2 = new DiseaseBuilder().build();
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }
}
