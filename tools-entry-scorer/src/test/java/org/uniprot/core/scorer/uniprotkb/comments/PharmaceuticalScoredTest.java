package org.uniprot.core.scorer.uniprotkb.comments;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.comment.CommentType;

class PharmaceuticalScoredTest extends CommentScoreTestBase {
    @Test
    void shouldSpScore30() {
        String line = "CC   -!- PHARMACEUTICAL: Available under the name Osigraft (Stryker). Its\n" +
                "CC       use is indicated in the treatment of tibial non-union of at least\n" +
                "CC       9 months duration, secondary to trauma, in skeletally mature\n" +
                "CC       patients, in cases where autograft has failed or is unfeasible.";
        verify(CommentType.PHARMACEUTICAL, line, 3.0, true);

    }

    @Test
    void shouldScore30() {
        String line = "CC   -!- PHARMACEUTICAL: Available under the name Osigraft (Stryker). Its\n" +
                "CC       use is indicated in the treatment of tibial non-union of at least\n" +
                "CC       9 months duration, secondary to trauma, in skeletally mature\n" +
                "CC       patients, in cases where autograft has failed or is unfeasible.";
        verify(CommentType.PHARMACEUTICAL, line, 3.0, false);
    }

    @Test
    void shouldWithEvScore30() {
        String line = "CC   -!- PHARMACEUTICAL: Its\n" +
                "CC       use is indicated in the treatment of tibial non-union of at least\n" +
                "CC       9 months duration, secondary to trauma, in skeletally mature\n" +
                "CC       patients, in cases where autograft has failed or is unfeasible.\n" +
                "CC       {ECO:0000256|HAMAP-Rule:MF_01146}.";
        verify(CommentType.PHARMACEUTICAL, line, 3.0, false);
    }
}
