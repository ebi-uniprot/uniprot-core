package org.uniprot.core.uniprot.comment.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.TestHelper;
import org.uniprot.core.uniprot.comment.CommentType;
import org.uniprot.core.uniprot.evidence.EvidencedValue;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.uniprot.core.uniprot.EvidenceHelper.createEvidenceValuesWithEvidences;
import static org.uniprot.core.uniprot.EvidenceHelper.createEvidenceValuesWithoutEvidences;

class FreeTextCommentImplTest {
    @Test
    void testNoEvidence() {
        List<EvidencedValue> texts = createEvidenceValuesWithoutEvidences();
        FreeTextCommentImpl comment = new FreeTextCommentImpl(CommentType.ALLERGEN, texts);
        assertEquals(CommentType.ALLERGEN, comment.getCommentType());
        assertEquals(texts, comment.getTexts());
        TestHelper.verifyJson(comment);
    }

    @Test
    void testWithEvidence() {
        List<EvidencedValue> texts = createEvidenceValuesWithEvidences();
        FreeTextCommentImpl comment = new FreeTextCommentImpl(CommentType.BIOTECHNOLOGY, texts);
        assertEquals(CommentType.BIOTECHNOLOGY, comment.getCommentType());
        assertEquals(texts, comment.getTexts());
        TestHelper.verifyJson(comment);
    }
}
