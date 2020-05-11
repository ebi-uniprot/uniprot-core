package org.uniprot.core.scorer.uniprotkb.comments;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.comment.CommentType;

class DisruptionPhenotypeScoredTest extends CommentScoreTestBase {
    @Test
    void shouldSpScore90() {
        String line =
                "CC   -!- DISRUPTION PHENOTYPE: Cells are sensitive to replication stress\n"
                    + "CC       hydroxyurea (HU) and also exhibits weak sensitivity to"
                    + " ultraviolet\n"
                    + "CC       (UV) and methylmethane sulfonate (MMS).";
        verify(CommentType.DISRUPTION_PHENOTYPE, line, 9.0, true);
    }

    @Test
    void shouldScore90() {
        String line =
                "CC   -!- DISRUPTION PHENOTYPE: Cells are sensitive to replication stress\n"
                    + "CC       hydroxyurea (HU) and also exhibits weak sensitivity to"
                    + " ultraviolet\n"
                    + "CC       (UV) and methylmethane sulfonate (MMS).";
        verify(CommentType.DISRUPTION_PHENOTYPE, line, 9.0, false);
    }

    @Test
    void shouldWithEvScore90() {
        String line =
                "CC   -!- DISRUPTION PHENOTYPE: Cells are sensitive to replication stress\n"
                    + "CC       hydroxyurea (HU) and also exhibits weak sensitivity to"
                    + " ultraviolet\n"
                    + "CC       (UV) and methylmethane sulfonate (MMS)."
                    + " {ECO:0000256|HAMAP-Rule:MF_01146}.";
        verify(CommentType.DISRUPTION_PHENOTYPE, line, 9.0, false);
    }
}
