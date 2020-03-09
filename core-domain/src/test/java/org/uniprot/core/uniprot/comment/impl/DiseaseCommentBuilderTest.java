package org.uniprot.core.uniprot.comment.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.CrossReference;
import org.uniprot.core.uniprot.comment.*;
import org.uniprot.core.uniprot.evidence.Evidence;

class DiseaseCommentBuilderTest {

    @Test
    void testSetDisease() {
        DiseaseBuilder builder = new DiseaseBuilder();

        String description = "some description";
        List<Evidence> evidences = createEvidences();

        DiseaseDatabase referenceType = DiseaseDatabase.MIM;
        String referenceId = "3124";
        CrossReference<DiseaseDatabase> reference = crossReference(referenceType, referenceId);
        String diseaseId = "someId";
        Disease disease =
                builder.diseaseId(diseaseId)
                        .acronym("someAcron")
                        .evidencesSet(evidences)
                        .description(description)
                        .diseaseCrossReference(reference)
                        .build();
        DiseaseCommentBuilder commentBuilder = new DiseaseCommentBuilder();

        DiseaseComment comment = commentBuilder.disease(disease).build();
        assertEquals(null, comment.getNote());
        assertEquals(CommentType.DISEASE, comment.getCommentType());
        assertEquals(disease, comment.getDisease());
    }

    @Test
    void testSetNote() {
        DiseaseBuilder builder = new DiseaseBuilder();

        String description = "some description";
        List<Evidence> evidences = createEvidences();

        DiseaseDatabase referenceType = DiseaseDatabase.MIM;
        String referenceId = "3124";
        CrossReference<DiseaseDatabase> reference = crossReference(referenceType, referenceId);
        String diseaseId = "someId";
        Disease disease =
                builder.diseaseId(diseaseId)
                        .acronym("someAcron")
                        .evidencesSet(evidences)
                        .description(description)
                        .diseaseCrossReference(reference)
                        .build();
        Note note = new NoteBuilder(createEvidenceValuesWithoutEvidences()).build();
        DiseaseCommentBuilder commentBuilder = new DiseaseCommentBuilder();

        DiseaseComment comment = commentBuilder.disease(disease).note(note).build();
        assertEquals(note, comment.getNote());
        assertEquals(CommentType.DISEASE, comment.getCommentType());
        assertEquals(disease, comment.getDisease());
    }

    @Test
    void canCreateBuilderFromInstance() {
        DiseaseComment obj = new DiseaseCommentBuilder().build();
        DiseaseCommentBuilder builder = DiseaseCommentBuilder.from(obj);
        assertNotNull(builder);
    }

    @Test
    void defaultBuild_objsAreEqual() {
        DiseaseComment obj = new DiseaseCommentBuilder().build();
        DiseaseComment obj2 = new DiseaseCommentBuilder().build();
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }
}
