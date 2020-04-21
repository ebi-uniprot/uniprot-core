package org.uniprot.core.uniprotkb.comment.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.uniprot.core.CrossReference;
import org.uniprot.core.uniprotkb.comment.*;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.evidence.EvidencedValue;
import org.uniprot.core.uniprotkb.evidence.impl.EvidenceBuilderTest;
import org.uniprot.core.uniprotkb.evidence.impl.EvidencedValueBuilder;

public class DiseaseCommentBuilderTest {

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

    public static Comment createObject() {
        DiseaseCommentBuilder builder = new DiseaseCommentBuilder();
        Disease disease = DiseaseBuilderTest.createObject();
        builder.disease(disease);
        String random = UUID.randomUUID().toString();
        String molecule = "mol-" + random;
        builder.molecule(molecule);
        List<EvidencedValue> evidencedValues = new ArrayList<>();
        evidencedValues.add(
                new EvidencedValueBuilder("value 1", EvidenceBuilderTest.createObjects(3)).build());
        evidencedValues.add(
                new EvidencedValueBuilder("value2", EvidenceBuilderTest.createObjects(2)).build());
        Note note = new NoteBuilder(evidencedValues).build();
        builder.note(note);
        return builder.build();
    }
}
