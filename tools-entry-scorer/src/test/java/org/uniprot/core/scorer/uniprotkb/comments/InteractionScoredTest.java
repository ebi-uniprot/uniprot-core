package org.uniprot.core.scorer.uniprotkb.comments;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.comment.CommentType;

class InteractionScoredTest extends CommentScoreTestBase {
    @Test
    void shouldScore90() throws Exception {
        String line =
                "CC   -!- INTERACTION:\n"
                        + "CC       P12345; P12345-1; NbExp=1; IntAct=EBI-2856, EBI-2856;\n"
                        + "CC       P12345; Q02821: SRP1; NbExp=2; IntAct=EBI-2856, EBI-1797;\n"
                        + "CC       P12345; P84198: VIM; Xeno; NbExp=4; IntAct=EBI-356498,"
                        + " EBI-457639;";
        verify(CommentType.INTERACTION, line, 9.0);
    }
}
