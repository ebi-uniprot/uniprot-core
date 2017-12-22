package uk.ac.ebi.uniprot.domain.uniprot.comments.builder;

import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.comments.CommentNote;
import uk.ac.ebi.uniprot.domain.uniprot.comments.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comments.Disease;
import uk.ac.ebi.uniprot.domain.uniprot.comments.DiseaseComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.DiseaseDescription;
import uk.ac.ebi.uniprot.domain.uniprot.comments.DiseaseId;
import uk.ac.ebi.uniprot.domain.uniprot.comments.DiseaseReference;
import uk.ac.ebi.uniprot.domain.uniprot.comments.DiseaseReferenceType;
import uk.ac.ebi.uniprot.domain.uniprot.evidences.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.EvidenceFactory;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Test;

import static org.junit.Assert.*;

public class DiseaseBuilderTest {

    @Test
    public void testSetDiseaseId() {
        DiseaseBuilder builder = DiseaseBuilder.newInstance();
        DiseaseId diseaseId = DiseaseBuilder.createDiseaseId("someId");
        Disease disease =builder.setDiseaseId(diseaseId)
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
        Disease disease =builder.setDiseaseId(diseaseId)
                .setAcronym("someAcron")
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
        Disease disease =builder.setDiseaseId(diseaseId)
                .setAcronym("someAcron")
                .setDescription(description)
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
        Disease disease =builder.setDiseaseId(diseaseId)
                .setAcronym("someAcron")
                .setDescription(description)
                .setReference(reference)           
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

    @Test
    public void testCreateDiseaseComment() {
        DiseaseBuilder builder = DiseaseBuilder.newInstance();
        String val ="some description";
        List<Evidence> evidences =  createEvidences();
        DiseaseDescription description = DiseaseBuilder.createDiseaseDescription(val,evidences);
        DiseaseReferenceType referenceType = DiseaseReferenceType.MIM;
        String referenceId = "3124";
        DiseaseReference reference =DiseaseBuilder.createDiseaseReference(referenceType, referenceId);
        DiseaseId diseaseId = DiseaseBuilder.createDiseaseId("someId");
        Disease disease =builder.setDiseaseId(diseaseId)
                .setAcronym("someAcron")
                .setDescription(description)
                .setReference(reference)           
                .build();
        CommentNote note = CommentBuilderUtil.createCommentNote(createEvidenceValues());
        DiseaseComment comment = DiseaseBuilder.createDiseaseComment(disease, note);
       assertEquals(note, comment.getNote());
        assertEquals(CommentType.DISEASE, comment.getCommentType());
        assertEquals(disease, comment.getDisease());
    }
    private List<Evidence> createEvidences() {
        List<Evidence> evidences = new ArrayList<>();
        evidences.add(EvidenceFactory.from("ECO:0000255|PROSITE-ProRule:PRU10028"));
        evidences.add(EvidenceFactory.from("ECO:0000256|PIRNR:PIRNR001361"));
        return evidences;
    }
    private List<EvidencedValue> createEvidenceValues() {
        List<EvidencedValue> evidencedValues = new ArrayList<>();
        evidencedValues.add(UniProtFactory.createEvidencedValue("value1", Collections.emptyList()));
        evidencedValues.add(UniProtFactory.createEvidencedValue("value2", Collections.emptyList()));
        return evidencedValues;
    }
}
