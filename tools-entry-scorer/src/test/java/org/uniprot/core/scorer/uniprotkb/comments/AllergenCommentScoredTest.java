package org.uniprot.core.scorer.uniprotkb.comments;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.comment.CommentType;

class AllergenCommentScoredTest extends CommentScoreTestBase {
    @Test
    void shouldSPScore30() {
        String line = "ALLERGEN: Causes an allergic reaction in human. Binds to IgE and\n" + "IgG.";
        verify(CommentType.ALLERGEN, line, 3.0, true);
    }

    @Test
    void shouldScore10() throws Exception {
        String line = "ALLERGEN: Causes an allergic reaction in human. Binds to IgE and\n" + "IgG.";
        verify(CommentType.ALLERGEN, line, 1.0);
    }

    @Test
    void shouldScore10_1() throws Exception {
        String line =
                "ALLERGEN: Causes an allergic reaction in human. Binds to IgE and\n"
                        + "IgG. {ECO:0000256|HAMAP-Rule:MF_01146}.";
        verify(CommentType.ALLERGEN, line, 1.0);
    }
}
