package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.impl.DBCrossReferenceImpl;
import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Note;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Disease;
import uk.ac.ebi.uniprot.domain.uniprot.comment.DiseaseComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.DiseaseDescription;

import uk.ac.ebi.uniprot.domain.uniprot.comment.DiseaseReferenceType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.DiseaseBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.DiseaseCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.CommentFactory;
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
        DBCrossReference<DiseaseReferenceType> reference = new DBCrossReferenceImpl<>(referenceType, referenceId);
        String diseaseId = "someId";
        Disease disease =builder.diseaseId(diseaseId)
                .acronym("someAcron")
                .description(description)
                .reference(reference)           
                .build();
        DiseaseCommentBuilder commentBuilder = DiseaseCommentBuilder.newInstance();
        
        DiseaseComment comment = 
                commentBuilder.disease(disease)
                .build();
       assertEquals(null, comment.getNote());
        assertEquals(CommentType.DISEASE, comment.getCommentType());
        assertEquals(disease, comment.getDisease());
        TestHelper.verifyJson(comment);
    }

    @Test
    public void testSetNote() {
        DiseaseBuilder builder = DiseaseCommentBuilder.newDiseaseBuilder();
        String val ="some description";
        List<Evidence> evidences =  createEvidences();
        DiseaseDescription description = DiseaseBuilder.createDiseaseDescription(val,evidences);
        DiseaseReferenceType referenceType = DiseaseReferenceType.MIM;
        String referenceId = "3124";
        DBCrossReference<DiseaseReferenceType> reference = new DBCrossReferenceImpl<>(referenceType, referenceId);
        String diseaseId = "someId";
        Disease disease =builder.diseaseId(diseaseId)
                .acronym("someAcron")
                .description(description)
                .reference(reference)           
                .build();
        Note note = CommentFactory.INSTANCE.createNote(createEvidenceValues());
        DiseaseCommentBuilder commentBuilder = DiseaseCommentBuilder.newInstance();
        
        DiseaseComment comment = 
                commentBuilder.disease(disease)
                .note(note)
                .build();
       assertEquals(note, comment.getNote());
        assertEquals(CommentType.DISEASE, comment.getCommentType());
        assertEquals(disease, comment.getDisease());
        TestHelper.verifyJson(comment);
    }
    private List<Evidence> createEvidences() {
        List<Evidence> evidences = new ArrayList<>();
        evidences.add(UniProtFactory.INSTANCE.createEvidence("ECO:0000255|PROSITE-ProRule:PRU10028"));
        evidences.add(UniProtFactory.INSTANCE.createEvidence("ECO:0000256|PIRNR:PIRNR001361"));
        return evidences;
    }
    private List<EvidencedValue> createEvidenceValues() {
        List<EvidencedValue> evidencedValues = new ArrayList<>();
        evidencedValues.add(UniProtFactory.INSTANCE.createEvidencedValue("value1", Collections.emptyList()));
        evidencedValues.add(UniProtFactory.INSTANCE.createEvidencedValue("value2", Collections.emptyList()));
        return evidencedValues;
    }
}
