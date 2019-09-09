package org.uniprot.core.scorer.uniprotkb.comments;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.comment.CommentType;

public class ToxicDoseScoredTest extends CommentScoreTestBase {
    @Test
    public void shouldSpScore90() throws Exception {
        String line = "CC   -!- TOXIC DOSE: LD(50) is 1.2 mg/kg by intravenous injection.";
        verify(CommentType.TOXIC_DOSE, line, 9.0, true);
    }

    @Test
    public void shouldScore90() throws Exception {
        String line = "CC   -!- TOXIC DOSE: LD(50) is 1.2 mg/kg by intravenous injection.";
        verify(CommentType.TOXIC_DOSE, line, 9.0, false);
    }

    @Test
    public void shouldWithEvScore90() throws Exception {
        String line =
                "CC   -!- TOXIC DOSE: LD(50) is 1.2 mg/kg by intravenous injection. {ECO:0000256|HAMAP-Rule:MF_01146}.";
        verify(CommentType.TOXIC_DOSE, line, 9.0, false);
    }
}
