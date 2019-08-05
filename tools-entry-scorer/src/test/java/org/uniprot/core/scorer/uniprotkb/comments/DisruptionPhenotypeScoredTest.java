package org.uniprot.core.scorer.uniprotkb.comments;

import org.junit.Test;
import org.uniprot.core.uniprot.comment.CommentType;

public class DisruptionPhenotypeScoredTest extends CommentScoreTestBase {
    @Test
    public void shouldSpScore90() throws Exception {
        String line = "CC   -!- DISRUPTION PHENOTYPE: Cells are sensitive to replication stress\n" +
                "CC       hydroxyurea (HU) and also exhibits weak sensitivity to ultraviolet\n" +
                "CC       (UV) and methylmethane sulfonate (MMS).";
        verify(CommentType.DISRUPTION_PHENOTYPE, line, 9.0, true);
    }

    @Test
    public void shouldScore90() throws Exception {
        String line = "CC   -!- DISRUPTION PHENOTYPE: Cells are sensitive to replication stress\n" +
                "CC       hydroxyurea (HU) and also exhibits weak sensitivity to ultraviolet\n" +
                "CC       (UV) and methylmethane sulfonate (MMS).";
        verify(CommentType.DISRUPTION_PHENOTYPE, line, 9.0, false);
    }

    @Test
    public void shouldWithEvScore90() throws Exception {
        String line = "CC   -!- DISRUPTION PHENOTYPE: Cells are sensitive to replication stress\n" +
                "CC       hydroxyurea (HU) and also exhibits weak sensitivity to ultraviolet\n" +
                "CC       (UV) and methylmethane sulfonate (MMS). {ECO:0000256|HAMAP-Rule:MF_01146}.";
        verify(CommentType.DISRUPTION_PHENOTYPE, line, 9.0, false);
    }
}
