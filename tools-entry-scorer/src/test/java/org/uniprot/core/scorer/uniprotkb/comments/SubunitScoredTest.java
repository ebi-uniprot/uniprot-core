package org.uniprot.core.scorer.uniprotkb.comments;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.comment.CommentType;

class SubunitScoredTest extends CommentScoreTestBase {
    @Test
    void shouldScore30() {
        String line = "CC   -!- SUBUNIT: Homodimer. {ECO:0000256|HAMAP-Rule:MF_01146}.";
        verify(CommentType.SUBUNIT, line, 3.0, false);
    }

    @Test
    void shouldScore90() {
        String line = "CC   -!- SUBUNIT: Homodimer. {ECO:0000269}.";
        verify(CommentType.SUBUNIT, line, 9.0, false);
    }

    @Test
    void test3() {
        String line = ("CC   -!- SUBUNIT: Homodimer. {ECO:0000256}.");
        verify(CommentType.SUBUNIT, line, 3.0, false);
    }
}
