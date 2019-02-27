package uk.ebi.uniprot.scorer.uniprotkb.comments;

import org.junit.Test;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType;

public class PolymorphismScoredTest extends CommentScoreTestBase {
    @Test
    public void shouldSpScore90() throws Exception {
        String line = "CC   -!- POLYMORPHISM: Variations in SDAD1 may be a cause of susceptibility\n" +
                "CC       to seasonal allergic rhinitis (SAR). SAR is a common allergic\n" +
                "CC       disorder characterized by episodes of sneezing, rhinorrhea, and\n" +
                "CC       swelling of the nasal mucosa.";
        verify(CommentType.POLYMORPHISM, line, 9.0, true);
    }

    @Test
    public void shouldScore90() throws Exception {
        String line = "CC   -!- POLYMORPHISM: Variations in SDAD1 may be a cause of susceptibility\n" +
                "CC       to seasonal allergic rhinitis (SAR). SAR is a common allergic\n" +
                "CC       disorder characterized by episodes of sneezing, rhinorrhea, and\n" +
                "CC       swelling of the nasal mucosa.";
        verify(CommentType.POLYMORPHISM, line, 9.0, false);
    }

    @Test
    public void shouldWithEvScore90() throws Exception {
        String line = "CC   -!- POLYMORPHISM: Variations in SDAD1 may be a cause of susceptibility\n" +
                "CC       to seasonal allergic rhinitis (SAR). SAR is a common allergic\n" +
                "CC       disorder characterized by episodes of sneezing, rhinorrhea, and\n" +
                "CC       swelling of the nasal mucosa. {ECO:0000256|HAMAP-Rule:MF_01146}.";
        verify(CommentType.POLYMORPHISM, line, 9.0, false);
    }
}
