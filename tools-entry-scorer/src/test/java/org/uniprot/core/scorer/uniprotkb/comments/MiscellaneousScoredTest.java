package org.uniprot.core.scorer.uniprotkb.comments;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.comment.CommentType;

class MiscellaneousScoredTest extends CommentScoreTestBase {
    private static String NON_SCORE_TEXT =
            "CC   -!- MISCELLANEOUS: The sequence shown here is derived from an\n"
                    + "CC       EMBL/GenBank/DDBJ third party annotation (TPA) entry.";
    private static String SCORED_TEXT =
            "CC   -!- MISCELLANEOUS: Present with 13500 molecules/cell in log phase SD\n"
                    + "CC       medium.";
    private static String SCORED_TEXT_2 =
            "CC   -!- MISCELLANEOUS: Present with 13500 molecules/cell in log phase SD\n"
                    + "CC       medium. {ECO:0000256|HAMAP-Rule:MF_01146}.";
    private static String NON_SCORE_TEXT_2 =
            "CC   -!- MISCELLANEOUS: The sequence shown here is derived from an\n"
                + "CC       EMBL/GenBank/DDBJ third party annotation (TPA) entry (By similarity).";

    @Test
    void shouldScore0() throws Exception {
        verify(CommentType.MISCELLANEOUS, NON_SCORE_TEXT, 0.0);
    }

    // TODO: 12/02/19 This test has no @Test (ported from old code-base)?
    void shouldAnScore0() throws Exception {
        verify(CommentType.MISCELLANEOUS, NON_SCORE_TEXT_2, 0.0);
    }

    // TODO: 12/02/19 This test has no @Test (ported from old code-base)?
    void shouldSpScore10() throws Exception {
        verify(CommentType.MISCELLANEOUS, SCORED_TEXT, 1.0, true);
    }

    // TODO: 12/02/19 This test has no @Test (ported from old code-base)?
    void shouldScore10() throws Exception {
        verify(CommentType.MISCELLANEOUS, SCORED_TEXT, 1.0, false);
    }

    // TODO: 12/02/19 This test has no @Test (ported from old code-base)?
    void shouldWithEvScore10() throws Exception {
        verify(CommentType.MISCELLANEOUS, SCORED_TEXT_2, 1.0, false);
    }
}
