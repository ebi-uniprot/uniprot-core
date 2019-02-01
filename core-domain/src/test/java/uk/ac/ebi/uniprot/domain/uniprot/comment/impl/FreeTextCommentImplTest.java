package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidencedValue;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static uk.ac.ebi.uniprot.domain.uniprot.EvidenceHelper.createEvidenceValuesWithEvidences;
import static uk.ac.ebi.uniprot.domain.uniprot.EvidenceHelper.createEvidenceValuesWithoutEvidences;

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
