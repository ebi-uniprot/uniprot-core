package org.uniprot.core.uniprot.comment.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.uniprot.core.ObjectsForTests.createEvidenceValuesWithEvidences;
import static org.uniprot.core.ObjectsForTests.createEvidenceValuesWithoutEvidences;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.comment.CommentType;
import org.uniprot.core.uniprot.evidence.EvidencedValue;

class FreeTextCommentImplTest {
    @Test
    void testNoEvidence() {
        List<EvidencedValue> texts = createEvidenceValuesWithoutEvidences();
        FreeTextCommentImpl comment = new FreeTextCommentImpl(CommentType.ALLERGEN, texts);
        assertEquals(CommentType.ALLERGEN, comment.getCommentType());
        assertEquals(texts, comment.getTexts());
    }

    @Test
    void testWithEvidence() {
        List<EvidencedValue> texts = createEvidenceValuesWithEvidences();
        FreeTextCommentImpl comment = new FreeTextCommentImpl(CommentType.BIOTECHNOLOGY, texts);
        assertEquals(CommentType.BIOTECHNOLOGY, comment.getCommentType());
        assertEquals(texts, comment.getTexts());
    }
}
