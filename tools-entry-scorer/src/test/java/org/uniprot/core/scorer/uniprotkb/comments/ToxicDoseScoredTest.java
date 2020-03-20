package org.uniprot.core.scorer.uniprotkb.comments;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.comment.CommentType;

class ToxicDoseScoredTest extends CommentScoreTestBase {
    @Test
    void shouldSpScore90() {
        String line = "CC   -!- TOXIC DOSE: LD(50) is 1.2 mg/kg by intravenous injection.";
        verify(CommentType.TOXIC_DOSE, line, 9.0, true);
    }

    @Test
    void shouldScore90() {
        String line = "CC   -!- TOXIC DOSE: LD(50) is 1.2 mg/kg by intravenous injection.";
        verify(CommentType.TOXIC_DOSE, line, 9.0, false);
    }

    @Test
    void shouldWithEvScore90() {
        String line =
                "CC   -!- TOXIC DOSE: LD(50) is 1.2 mg/kg by intravenous injection. {ECO:0000256|HAMAP-Rule:MF_01146}.";
        verify(CommentType.TOXIC_DOSE, line, 9.0, false);
    }
}
