package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import org.junit.Test;
import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.impl.DBCrossReferenceImpl;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Disease;
import uk.ac.ebi.uniprot.domain.uniprot.comment.DiseaseReferenceType;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.Evidence;

import java.util.List;

import static org.junit.Assert.*;
import static uk.ac.ebi.uniprot.domain.uniprot.comment.builder.BuilderTestHelper.createEvidences;

public class DiseaseBuilderTest {
    @Test
    public void testNewInstance() {
        DiseaseBuilder builder1 = new DiseaseBuilder();
        DiseaseBuilder builder2 = new DiseaseBuilder();
        assertNotNull(builder1);
        assertNotNull(builder2);
        assertFalse(builder1 == builder2);
    }

    @Test
    public void testSetDiseaseId() {
        DiseaseBuilder builder = new DiseaseBuilder();
        String diseaseId = "someId";
        Disease disease = builder.diseaseId(diseaseId)
                .build();
        assertEquals(diseaseId, disease.getDiseaseId());
        assertNull(disease.getDescription());
        assertNull(disease.getReference());
        assertNull(disease.getAcronym());
        TestHelper.verifyJson(disease);
    }

    @Test
    public void testSetAcronym() {
        DiseaseBuilder builder = new DiseaseBuilder();
        String diseaseId = "someId";
        Disease disease = builder.diseaseId(diseaseId)
                .acronym("someAcron")
                .build();
        assertEquals(diseaseId, disease.getDiseaseId());
        assertNull(disease.getDescription());
        assertNull(disease.getReference());
        assertEquals("someAcron", disease.getAcronym());
        TestHelper.verifyJson(disease);
    }

    @Test
    public void testSetDescription() {
        DiseaseBuilder builder = new DiseaseBuilder();

        String description ="some description";
        List<Evidence> evidences =  createEvidences();


        String diseaseId = "someId";
        Disease disease = builder.diseaseId(diseaseId)
                .acronym("someAcron")
                .description(new DiseaseDescriptionBuilder().value(description).evidences(evidences).build())
                .build();
        assertEquals(diseaseId, disease.getDiseaseId());
        assertEquals(description, disease.getDescription());
        assertNull(disease.getReference());
        assertEquals("someAcron", disease.getAcronym());
        TestHelper.verifyJson(disease);
    }

    @Test
    public void testSetReference() {
        DiseaseBuilder builder = new DiseaseBuilder();

        String description ="some description";
        List<Evidence> evidences =  createEvidences();

        String val = "some description";

        String referenceId = "3124";
        DBCrossReference<DiseaseReferenceType> reference = new DBCrossReferenceImpl<>(DiseaseReferenceType.MIM, referenceId);
        String diseaseId = "someId";
        Disease disease = builder.diseaseId(diseaseId)
                .acronym("someAcron")
                .description(new DiseaseDescriptionBuilder().value(description).evidences(evidences).build())
                .reference(reference)        
                .build();
        assertEquals(diseaseId, disease.getDiseaseId());
        assertEquals(description, disease.getDescription());
        assertEquals(reference, disease.getReference());
        assertEquals("someAcron", disease.getAcronym());
        TestHelper.verifyJson(disease);
    }

    @Test
    public void testCreateDiseaseReference() {
        DiseaseReferenceType referenceType = DiseaseReferenceType.MIM;
        String referenceId = "3124";
        DBCrossReference<DiseaseReferenceType> reference = new DBCrossReferenceImpl<>(referenceType, referenceId);

        assertEquals(referenceType, reference.getDatabaseType());
        assertEquals(referenceId, reference.getId());
    }
}
