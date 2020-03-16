package org.uniprot.core.scorer.uniprotkb.comments;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.comment.CommentType;

class PolymorphismScoredTest extends CommentScoreTestBase {
    @Test
    void shouldSpScore90() {
        String line =
                "CC   -!- POLYMORPHISM: Variations in SDAD1 may be a cause of susceptibility\n"
                        + "CC       to seasonal allergic rhinitis (SAR). SAR is a common allergic\n"
                        + "CC       disorder characterized by episodes of sneezing, rhinorrhea, and\n"
                        + "CC       swelling of the nasal mucosa.";
        verify(CommentType.POLYMORPHISM, line, 9.0, true);
    }

    @Test
    void shouldScore90() {
        String line =
                "CC   -!- POLYMORPHISM: Variations in SDAD1 may be a cause of susceptibility\n"
                        + "CC       to seasonal allergic rhinitis (SAR). SAR is a common allergic\n"
                        + "CC       disorder characterized by episodes of sneezing, rhinorrhea, and\n"
                        + "CC       swelling of the nasal mucosa.";
        verify(CommentType.POLYMORPHISM, line, 9.0, false);
    }

    @Test
    void shouldWithEvScore90() {
        String line =
                "CC   -!- POLYMORPHISM: Variations in SDAD1 may be a cause of susceptibility\n"
                        + "CC       to seasonal allergic rhinitis (SAR). SAR is a common allergic\n"
                        + "CC       disorder characterized by episodes of sneezing, rhinorrhea, and\n"
                        + "CC       swelling of the nasal mucosa. {ECO:0000256|HAMAP-Rule:MF_01146}.";
        verify(CommentType.POLYMORPHISM, line, 9.0, false);
    }
}
