package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import org.junit.Test;
import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.impl.DBCrossReferenceImpl;
import uk.ac.ebi.uniprot.domain.uniprot.comment.*;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static uk.ac.ebi.uniprot.domain.uniprot.EvidenceHelper.createEvidenceValuesWithoutEvidences;
import static uk.ac.ebi.uniprot.domain.uniprot.EvidenceHelper.createEvidences;

public class DiseaseCommentBuilderTest {

    @Test
    public void testSetDisease() {
        DiseaseBuilder builder = new DiseaseBuilder();

        String description = "some description";
        List<Evidence> evidences = createEvidences();

        DiseaseReferenceType referenceType = DiseaseReferenceType.MIM;
        String referenceId = "3124";
        DBCrossReference<DiseaseReferenceType> reference = new DBCrossReferenceImpl<>(referenceType, referenceId);
        String diseaseId = "someId";
        Disease disease = builder.diseaseId(diseaseId)
                .acronym("someAcron")
                .evidences(evidences)
                .description(description)
                .reference(reference)
                .build();
        DiseaseCommentBuilder commentBuilder = new DiseaseCommentBuilder();

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
        DiseaseBuilder builder = new DiseaseBuilder();

        String description = "some description";
        List<Evidence> evidences = createEvidences();

        DiseaseReferenceType referenceType = DiseaseReferenceType.MIM;
        String referenceId = "3124";
        DBCrossReference<DiseaseReferenceType> reference = new DBCrossReferenceImpl<>(referenceType, referenceId);
        String diseaseId = "someId";
        Disease disease = builder.diseaseId(diseaseId)
                .acronym("someAcron")
                .evidences(evidences)
                .description(description)
                .reference(reference)
                .build();
        Note note = new NoteBuilder(createEvidenceValuesWithoutEvidences()).build();
        DiseaseCommentBuilder commentBuilder = new DiseaseCommentBuilder();

        DiseaseComment comment =
                commentBuilder.disease(disease)
                        .note(note)
                        .build();
        assertEquals(note, comment.getNote());
        assertEquals(CommentType.DISEASE, comment.getCommentType());
        assertEquals(disease, comment.getDisease());
        TestHelper.verifyJson(comment);
    }
}
