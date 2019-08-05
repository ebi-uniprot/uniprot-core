package org.uniprot.core.scorer.uniprotkb.comments;

import org.junit.Test;
import org.uniprot.core.uniprot.comment.CommentType;

public class SubunitScoredTest extends CommentScoreTestBase {
    @Test
    public void shouldScore30() throws Exception {
        String line = "CC   -!- SUBUNIT: Homodimer. {ECO:0000256|HAMAP-Rule:MF_01146}.";
        verify(CommentType.SUBUNIT, line, 3.0, false);
    }

    @Test
    public void shouldScore90() throws Exception {
        String line = "CC   -!- SUBUNIT: Homodimer. {ECO:0000269}.";
        verify(CommentType.SUBUNIT, line, 9.0, false);
    }

    @Test
    public void test3() throws Exception {
        String line = ("CC   -!- SUBUNIT: Homodimer. {ECO:0000256}.");
        verify(CommentType.SUBUNIT, line, 3.0, false);
    }
}
