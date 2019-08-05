package org.uniprot.core.scorer.uniprotkb.comments;

import org.junit.Test;
import org.uniprot.core.uniprot.comment.CommentType;

public class TissueSpecificityScoredTest extends CommentScoreTestBase {

    @Test
    public void shouldSpScore30() throws Exception {
        String line = "CC   -!- TISSUE SPECIFICITY: In adult brain, highly expressed in putamen\n" +
                "CC       with no expression in cerebral cortex. Expressed in adult and\n" +
                "CC       fetal lung and fetal liver. Also expressed at high levels in some\n" +
                "CC       brain tumors including medulloblastomas and primitive\n" +
                "CC       neuroectodermal tumors.";
        verify(CommentType.TISSUE_SPECIFICITY, line, 3.0, true);
    }

    @Test
    public void shouldScore30() throws Exception {
        String line = "CC   -!- TISSUE SPECIFICITY: In adult brain, highly expressed in putamen\n" +
                "CC       with no expression in cerebral cortex. Expressed in adult and\n" +
                "CC       fetal lung and fetal liver. Also expressed at high levels in some\n" +
                "CC       brain tumors including medulloblastomas and primitive\n" +
                "CC       neuroectodermal tumors.";
        verify(CommentType.TISSUE_SPECIFICITY, line, 3.0, false);
    }

    @Test
    public void shouldWithEvScore30() throws Exception {
        String line = "CC   -!- TISSUE SPECIFICITY: In adult brain, highly expressed in putamen\n" +
                "CC       with no expression in cerebral cortex. Expressed in adult and\n" +
                "CC       fetal lung and fetal liver. Also expressed at high levels in some\n" +
                "CC       brain tumors including medulloblastomas and primitive\n" +
                "CC       neuroectodermal tumors. {ECO:0000256|HAMAP-Rule:MF_01146}.";
        verify(CommentType.TISSUE_SPECIFICITY, line, 3.0, false);

    }
}
