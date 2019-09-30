package org.uniprot.core.scorer.uniprotkb.comments;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.comment.CommentType;

class SimilarityScoredTest extends CommentScoreTestBase {
    @Test
    void shouldScore0() throws Exception {
        String line = "CC   -!- SIMILARITY: Contains 1 KilA-N domain.";
        verify(CommentType.SIMILARITY, line, 0.0);
    }

    @Test
    void shouldScore30() throws Exception {
        String line =
                "CC   -!- SIMILARITY: In the C-terminal section; belongs to the transposase\n"
                        + "CC       35 family.";
        verify(CommentType.SIMILARITY, line, 3.0);
    }
}
