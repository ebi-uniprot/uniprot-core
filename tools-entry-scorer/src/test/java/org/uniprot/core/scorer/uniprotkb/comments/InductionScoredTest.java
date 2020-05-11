package org.uniprot.core.scorer.uniprotkb.comments;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.comment.CommentType;

class InductionScoredTest extends CommentScoreTestBase {
    @Test
    void shouldSpScore30() {
        String line =
                "CC   -!- INDUCTION: Regulated in a cell cycle-dependent manner, peaking in\n"
                    + "CC       G1 phase. Appears exclusively during the G1 and S phases (at\n"
                    + "CC       protein level). Negatively regulated by transcription factor SBF\n"
                    + "CC       (SWI4-SWI6 cell-cycle box binding factor).";
        verify(CommentType.INDUCTION, line, 3.0, true);
    }

    @Test
    void shouldScore10() {
        String line =
                "CC   -!- INDUCTION: Regulated in a cell cycle-dependent manner, peaking in\n"
                    + "CC       G1 phase. Appears exclusively during the G1 and S phases (at\n"
                    + "CC       protein level). Negatively regulated by transcription factor SBF\n"
                    + "CC       (SWI4-SWI6 cell-cycle box binding factor).";
        verify(CommentType.INDUCTION, line, 1.0, false);
    }

    @Test
    void shouldWithEvScore10() throws Exception {
        String line =
                "CC   -!- INDUCTION: Regulated in a cell cycle-dependent manner, peaking in\n"
                    + "CC       G1 phase. Appears exclusively during the G1 and S phases (at\n"
                    + "CC       protein level). Negatively regulated by transcription factor SBF\n"
                    + "CC       (SWI4-SWI6 cell-cycle box binding factor)."
                    + " {ECO:0000256|HAMAP-Rule:MF_01146}.";
        verify(CommentType.INDUCTION, line, 1.0);
    }
}
