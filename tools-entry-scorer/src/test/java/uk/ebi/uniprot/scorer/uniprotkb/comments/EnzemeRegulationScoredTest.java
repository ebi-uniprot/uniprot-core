package uk.ebi.uniprot.scorer.uniprotkb.comments;

import org.junit.Test;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType;

public class EnzemeRegulationScoredTest extends CommentScoreTestBase {
    @Test
    public void shouldScore30() throws Exception {
        String line = "CC   -!- ACTIVITY REGULATION: GTPase activity is stimulated in the presence\n" +
                "CC       of 60S subunits. {ECO:0000256|HAMAP-Rule:MF_01146}.";
        verify(CommentType.ACTIVITY_REGULATION, line, 3.0);
    }

    @Test
    public void shouldWithEvScore30() throws Exception {
        String line = "CC   -!- ACTIVITY REGULATION: GTPase activity is stimulated in the presence\n" +
                "CC       of 60S subunits. {ECO:0000256}.";
        verify(CommentType.ACTIVITY_REGULATION, line, 3.0);
    }

    @Test
    public void shouldSpScore90() throws Exception {
        String line = "CC   -!- ACTIVITY REGULATION: GTPase activity is stimulated in the presence\n" +
                "CC       of 60S subunits.";
        verify(CommentType.ACTIVITY_REGULATION, line, 9.0, true);
    }
}
