package uk.ebi.uniprot.scorer.uniprotkb.comments;

import org.junit.Test;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType;

public class InteractionScoredTest extends CommentScoreTestBase {
    @Test
    public void shouldScore90() throws Exception {
        String line = "CC   -!- INTERACTION:\n" +
                "CC       Self; NbExp=1; IntAct=EBI-2856, EBI-2856;\n" +
                "CC       Q02821:SRP1; NbExp=2; IntAct=EBI-2856, EBI-1797;\n" +
                "CC       P84198:VIM (xeno); NbExp=4; IntAct=EBI-356498, EBI-457639;";
        verify(CommentType.INTERACTION, line, 9.0);
    }
}