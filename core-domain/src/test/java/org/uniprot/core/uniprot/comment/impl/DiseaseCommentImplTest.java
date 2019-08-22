package org.uniprot.core.uniprot.comment.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.DBCrossReference;
import org.uniprot.core.TestHelper;
import org.uniprot.core.impl.DBCrossReferenceImpl;
import org.uniprot.core.uniprot.comment.*;
import org.uniprot.core.uniprot.comment.builder.DiseaseBuilder;
import org.uniprot.core.uniprot.comment.impl.DiseaseCommentImpl;
import org.uniprot.core.uniprot.evidence.Evidence;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.uniprot.core.uniprot.EvidenceHelper.createEvidences;
import static org.uniprot.core.uniprot.comment.impl.ImplTestHelper.createNote;


class DiseaseCommentImplTest {

    @Test
    void testDiseaseCommentImpl() {
        String description = "some description";
        List<Evidence> evidences = createEvidences();

        DiseaseReferenceType referenceType = DiseaseReferenceType.MIM;
        String referenceId = "3124";
        DBCrossReference<DiseaseReferenceType> reference = new DBCrossReferenceImpl<>(referenceType, referenceId);
        String diseaseId = "someId";
        String diseaseAc = "I-00742";
        Disease disease = new DiseaseBuilder()
                .diseaseId(diseaseId)
                .diseaseAc(diseaseAc)
                .acronym("someAcron")
                .evidences(evidences)
                .description(description)
                .reference(reference).build();
        Note note = createNote();

        DiseaseComment comment = new DiseaseCommentImpl(disease, note);
        assertEquals(note, comment.getNote());
        assertEquals(CommentType.DISEASE, comment.getCommentType());
        assertEquals(disease, comment.getDisease());
        TestHelper.verifyJson(comment);
    }
}