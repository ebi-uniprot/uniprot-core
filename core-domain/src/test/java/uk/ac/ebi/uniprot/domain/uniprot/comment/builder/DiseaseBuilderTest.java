package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import uk.ac.ebi.uniprot.domain.uniprot.comment.Disease;
import uk.ac.ebi.uniprot.domain.uniprot.comment.DiseaseDescription;
import uk.ac.ebi.uniprot.domain.uniprot.comment.DiseaseId;
import uk.ac.ebi.uniprot.domain.uniprot.comment.DiseaseReference;
import uk.ac.ebi.uniprot.domain.uniprot.comment.DiseaseReferenceType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.DiseaseBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;

public class DiseaseBuilderTest {
    @Test
    public void testNewInstance() {
        DiseaseBuilder builder1 = DiseaseBuilder.newInstance();
        DiseaseBuilder builder2 = DiseaseBuilder.newInstance();
        assertNotNull(builder1);
        assertNotNull(builder2);
        assertFalse(builder1== builder2);
    }
    
    @Test
    public void testSetDiseaseId() {
        DiseaseBuilder builder = DiseaseBuilder.newInstance();
        DiseaseId diseaseId = DiseaseBuilder.createDiseaseId("someId");
        Disease disease =builder.diseaseId(diseaseId)
                .build();
        assertEquals(diseaseId, disease.getDiseaseId());
        assertNull(disease.getDescription());
        assertNull(disease.getReference());
        assertNull(disease.getAcronym());
    }

    @Test
    public void testSetAcronym() {
        DiseaseBuilder builder = DiseaseBuilder.newInstance();
        DiseaseId diseaseId = DiseaseBuilder.createDiseaseId("someId");
        Disease disease =builder.diseaseId(diseaseId)
                .acronym("someAcron")
                .build();
        assertEquals(diseaseId, disease.getDiseaseId());
        assertNull(disease.getDescription());
        assertNull(disease.getReference());
        assertEquals("someAcron", disease.getAcronym());
    }

    @Test
    public void testSetDescription() {
        DiseaseBuilder builder = DiseaseBuilder.newInstance();
        String val ="some description";
        List<Evidence> evidences =  createEvidences();
        DiseaseDescription description = DiseaseBuilder.createDiseaseDescription(val,evidences);
        DiseaseId diseaseId = DiseaseBuilder.createDiseaseId("someId");
        Disease disease =builder.diseaseId(diseaseId)
                .acronym("someAcron")
                .description(description)
                .build();
        assertEquals(diseaseId, disease.getDiseaseId());
        assertEquals(description, disease.getDescription());
        assertNull(disease.getReference());
        assertEquals("someAcron", disease.getAcronym());
    }

    @Test
    public void testSetReference() {
        DiseaseBuilder builder = DiseaseBuilder.newInstance();
        String val ="some description";
        List<Evidence> evidences =  createEvidences();
        DiseaseDescription description = DiseaseBuilder.createDiseaseDescription(val,evidences);
        DiseaseReferenceType referenceType = DiseaseReferenceType.MIM;
        String referenceId = "3124";
        DiseaseReference reference =DiseaseBuilder.createDiseaseReference(referenceType, referenceId);
        DiseaseId diseaseId = DiseaseBuilder.createDiseaseId("someId");
        Disease disease =builder.diseaseId(diseaseId)
                .acronym("someAcron")
                .description(description)
                .reference(reference)           
                .build();
        assertEquals(diseaseId, disease.getDiseaseId());
        assertEquals(description, disease.getDescription());
        assertEquals(reference, disease.getReference());
        assertEquals("someAcron", disease.getAcronym());
    }

    @Test
    public void testCreateDiseaseId() {
        DiseaseId diseaseId = DiseaseBuilder.createDiseaseId("someId");
        assertEquals("someId", diseaseId.getValue());
    }

    @Test
    public void testCreateDiseaseReference() {
        DiseaseReferenceType referenceType = DiseaseReferenceType.MIM;
        String referenceId = "3124";
        DiseaseReference reference =DiseaseBuilder.createDiseaseReference(referenceType, referenceId);
        assertEquals(referenceType, reference.getDiseaseReferenceType());
        assertEquals(referenceId, reference.getDiseaseReferenceId());
    }

    @Test
    public void testCreateDiseaseDescription() {
        String val ="some description";
        List<Evidence> evidences =  createEvidences();
        DiseaseDescription description = DiseaseBuilder.createDiseaseDescription(val,evidences);
        assertEquals(val, description.getValue());
        assertEquals(evidences, description.getEvidences());
    }

    private List<Evidence> createEvidences() {
        List<Evidence> evidences = new ArrayList<>();
        evidences.add(UniProtFactory.INSTANCE.createEvidence("ECO:0000255|PROSITE-ProRule:PRU10028"));
        evidences.add(UniProtFactory.INSTANCE.createEvidence("ECO:0000256|PIRNR:PIRNR001361"));
        return evidences;
    }
  
}
