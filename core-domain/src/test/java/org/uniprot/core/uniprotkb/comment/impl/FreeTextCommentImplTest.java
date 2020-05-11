package org.uniprot.core.uniprotkb.comment.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.createEvidenceValuesWithEvidences;
import static org.uniprot.core.ObjectsForTests.createEvidenceValuesWithoutEvidences;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.comment.CommentType;
import org.uniprot.core.uniprotkb.comment.FreeTextComment;
import org.uniprot.core.uniprotkb.evidence.EvidencedValue;

import java.util.List;

class FreeTextCommentImplTest {
    @Test
    void testNoEvidence() {
        List<EvidencedValue> texts = createEvidenceValuesWithoutEvidences();
        FreeTextCommentImpl comment = new FreeTextCommentImpl(CommentType.ALLERGEN, "", texts);
        assertEquals(CommentType.ALLERGEN, comment.getCommentType());
        assertEquals(texts, comment.getTexts());
        assertEquals("", comment.getMolecule());
    }

    @Test
    void testWithEvidence() {
        List<EvidencedValue> texts = createEvidenceValuesWithEvidences();
        FreeTextCommentImpl comment =
                new FreeTextCommentImpl(CommentType.BIOTECHNOLOGY, "isoform 2", texts);
        assertEquals(CommentType.BIOTECHNOLOGY, comment.getCommentType());
        assertEquals(texts, comment.getTexts());
        assertEquals("isoform 2", comment.getMolecule());
    }

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        FreeTextComment obj = new FreeTextCommentImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        FreeTextComment impl =
                new FreeTextCommentImpl(
                        CommentType.DISRUPTION_PHENOTYPE,
                        "molecule",
                        createEvidenceValuesWithoutEvidences());
        FreeTextComment obj = FreeTextCommentBuilder.from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
