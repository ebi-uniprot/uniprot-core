package org.uniprot.core.scorer.uniprotkb.comments;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.comment.CommentType;

class TissueSpecificityScoredTest extends CommentScoreTestBase {

    @Test
    void shouldSpScore30() {
        String line =
                "CC   -!- TISSUE SPECIFICITY: In adult brain, highly expressed in putamen\n"
                        + "CC       with no expression in cerebral cortex. Expressed in adult and\n"
                        + "CC       fetal lung and fetal liver. Also expressed at high levels in some\n"
                        + "CC       brain tumors including medulloblastomas and primitive\n"
                        + "CC       neuroectodermal tumors.";
        verify(CommentType.TISSUE_SPECIFICITY, line, 3.0, true);
    }

    @Test
    void shouldScore30() {
        String line =
                "CC   -!- TISSUE SPECIFICITY: In adult brain, highly expressed in putamen\n"
                        + "CC       with no expression in cerebral cortex. Expressed in adult and\n"
                        + "CC       fetal lung and fetal liver. Also expressed at high levels in some\n"
                        + "CC       brain tumors including medulloblastomas and primitive\n"
                        + "CC       neuroectodermal tumors.";
        verify(CommentType.TISSUE_SPECIFICITY, line, 3.0, false);
    }

    @Test
    void shouldWithEvScore30() {
        String line =
                "CC   -!- TISSUE SPECIFICITY: In adult brain, highly expressed in putamen\n"
                        + "CC       with no expression in cerebral cortex. Expressed in adult and\n"
                        + "CC       fetal lung and fetal liver. Also expressed at high levels in some\n"
                        + "CC       brain tumors including medulloblastomas and primitive\n"
                        + "CC       neuroectodermal tumors. {ECO:0000256|HAMAP-Rule:MF_01146}.";
        verify(CommentType.TISSUE_SPECIFICITY, line, 3.0, false);
    }
}
