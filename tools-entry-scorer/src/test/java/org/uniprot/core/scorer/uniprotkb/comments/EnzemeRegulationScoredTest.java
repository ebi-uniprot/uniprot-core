package org.uniprot.core.scorer.uniprotkb.comments;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.comment.CommentType;

class EnzemeRegulationScoredTest extends CommentScoreTestBase {
    @Test
    void shouldScore30() throws Exception {
        String line = "CC   -!- ACTIVITY REGULATION: GTPase activity is stimulated in the presence\n" +
                "CC       of 60S subunits. {ECO:0000256|HAMAP-Rule:MF_01146}.";
        verify(CommentType.ACTIVITY_REGULATION, line, 3.0);
    }

    @Test
    void shouldWithEvScore30() throws Exception {
        String line = "CC   -!- ACTIVITY REGULATION: GTPase activity is stimulated in the presence\n" +
                "CC       of 60S subunits. {ECO:0000256}.";
        verify(CommentType.ACTIVITY_REGULATION, line, 3.0);
    }

    @Test
    void shouldSpScore90() throws Exception {
        String line = "CC   -!- ACTIVITY REGULATION: GTPase activity is stimulated in the presence\n" +
                "CC       of 60S subunits.";
        verify(CommentType.ACTIVITY_REGULATION, line, 9.0, true);
    }
}
