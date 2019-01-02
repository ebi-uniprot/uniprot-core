package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import org.junit.Test;
import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.impl.DBCrossReferenceImpl;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Disease;
import uk.ac.ebi.uniprot.domain.uniprot.comment.DiseaseReferenceType;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DiseaseBuilderTest {
    @Test
    public void testNewInstance() {
        DiseaseBuilder builder1 = DiseaseBuilder.newInstance();
        DiseaseBuilder builder2 = DiseaseBuilder.newInstance();
        assertNotNull(builder1);
        assertNotNull(builder2);
        assertFalse(builder1 == builder2);
    }

    @Test
    public void testSetDiseaseId() {
        DiseaseBuilder builder = DiseaseBuilder.newInstance();
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
        DiseaseBuilder builder = DiseaseBuilder.newInstance();
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
        DiseaseBuilder builder = DiseaseBuilder.newInstance();

        String description ="some description";
        List<Evidence> evidences =  createEvidences();


        String diseaseId = "someId";
        Disease disease = builder.diseaseId(diseaseId)
                .acronym("someAcron")
                .description(description)
                .evidences(evidences)
                .build();
        assertEquals(diseaseId, disease.getDiseaseId());
        assertEquals(description, disease.getDescription());
        assertNull(disease.getReference());
        assertEquals("someAcron", disease.getAcronym());
        TestHelper.verifyJson(disease);
    }

    @Test
    public void testSetReference() {
        DiseaseBuilder builder = DiseaseBuilder.newInstance();

        String description ="some description";
        List<Evidence> evidences =  createEvidences();

        String val = "some description";

        String referenceId = "3124";
        DBCrossReference<DiseaseReferenceType> reference = new DBCrossReferenceImpl<>(DiseaseReferenceType.MIM, referenceId);
        String diseaseId = "someId";
        Disease disease = builder.diseaseId(diseaseId)
                .acronym("someAcron")
                .description(description)
                .reference(reference)        
                .evidences(evidences)
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



    private List<Evidence> createEvidences() {
        List<Evidence> evidences = new ArrayList<>();
        evidences.add(UniProtFactory.INSTANCE.createEvidence("ECO:0000255|PROSITE-ProRule:PRU10028"));
        evidences.add(UniProtFactory.INSTANCE.createEvidence("ECO:0000256|PIRNR:PIRNR001361"));
        return evidences;
    }

}
