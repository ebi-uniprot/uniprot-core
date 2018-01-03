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
import uk.ac.ebi.uniprot.domain.uniprot.factory.CommentFactory;
import uk.ac.ebi.uniprot.domain.uniprot.factory.EvidenceFactory;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Test;

import static org.junit.Assert.*;

public class DiseaseCommentBuilderTest {

    @Test
    public void testSetDisease() {
        DiseaseBuilder builder = DiseaseCommentBuilder.newDiseaseBuilder();
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
        DiseaseCommentBuilder commentBuilder = DiseaseCommentBuilder.newInstance();
        
        DiseaseComment comment = 
                commentBuilder.setDisease(disease)
                .build();
       assertEquals(null, comment.getNote());
        assertEquals(CommentType.DISEASE, comment.getCommentType());
        assertEquals(disease, comment.getDisease());
    }

    @Test
    public void testSetNote() {
        DiseaseBuilder builder = DiseaseCommentBuilder.newDiseaseBuilder();
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
        CommentNote note = CommentFactory.INSTANCE.createCommentNote(createEvidenceValues());
        DiseaseCommentBuilder commentBuilder = DiseaseCommentBuilder.newInstance();
        
        DiseaseComment comment = 
                commentBuilder.setDisease(disease)
                .setNote(note)
                .build();
       assertEquals(note, comment.getNote());
        assertEquals(CommentType.DISEASE, comment.getCommentType());
        assertEquals(disease, comment.getDisease());
    }
    private List<Evidence> createEvidences() {
        List<Evidence> evidences = new ArrayList<>();
        evidences.add(EvidenceFactory.INSTANCE.createFromEvidenceLine("ECO:0000255|PROSITE-ProRule:PRU10028"));
        evidences.add(EvidenceFactory.INSTANCE.createFromEvidenceLine("ECO:0000256|PIRNR:PIRNR001361"));
        return evidences;
    }
    private List<EvidencedValue> createEvidenceValues() {
        List<EvidencedValue> evidencedValues = new ArrayList<>();
        evidencedValues.add(UniProtFactory.INSTANCE.createEvidencedValue("value1", Collections.emptyList()));
        evidencedValues.add(UniProtFactory.INSTANCE.createEvidencedValue("value2", Collections.emptyList()));
        return evidencedValues;
    }
}
