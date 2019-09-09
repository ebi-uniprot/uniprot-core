package org.uniprot.core.scorer.uniprotkb.comments;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.comment.CommentType;

public class PharmaceuticalScoredTest extends CommentScoreTestBase {
    @Test
    public void shouldSpScore30() throws Exception {
        String line = "CC   -!- PHARMACEUTICAL: Available under the name Osigraft (Stryker). Its\n" +
                "CC       use is indicated in the treatment of tibial non-union of at least\n" +
                "CC       9 months duration, secondary to trauma, in skeletally mature\n" +
                "CC       patients, in cases where autograft has failed or is unfeasible.";
        verify(CommentType.PHARMACEUTICAL, line, 3.0, true);

    }

    @Test
    public void shouldScore30() throws Exception {
        String line = "CC   -!- PHARMACEUTICAL: Available under the name Osigraft (Stryker). Its\n" +
                "CC       use is indicated in the treatment of tibial non-union of at least\n" +
                "CC       9 months duration, secondary to trauma, in skeletally mature\n" +
                "CC       patients, in cases where autograft has failed or is unfeasible.";
        verify(CommentType.PHARMACEUTICAL, line, 3.0, false);
    }

    @Test
    public void shouldWithEvScore30() throws Exception {
        String line = "CC   -!- PHARMACEUTICAL: Its\n" +
                "CC       use is indicated in the treatment of tibial non-union of at least\n" +
                "CC       9 months duration, secondary to trauma, in skeletally mature\n" +
                "CC       patients, in cases where autograft has failed or is unfeasible.\n" +
                "CC       {ECO:0000256|HAMAP-Rule:MF_01146}.";
        verify(CommentType.PHARMACEUTICAL, line, 3.0, false);

    }
}
