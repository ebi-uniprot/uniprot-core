package org.uniprot.core.scorer.uniprotkb.comments;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.comment.CommentType;

class PTMScoredTest extends CommentScoreTestBase {
    @Test
    void shouldSpScore60() {
        String line = "CC   -!- PTM: Phosphorylated by CDC28.";
        verify(CommentType.PTM, line, 6.0, true);
    }

    @Test
    void shouldScore20() {
        String line = "CC   -!- PTM: Phosphorylated by CDC28.";
        verify(CommentType.PTM, line, 2.0, false);
    }

    @Test
    void shouldWithEvScore20() {
        String line = "CC   -!- PTM: Phosphorylated by CDC28. {ECO:0000256|HAMAP-Rule:MF_01146}.";
        verify(CommentType.PTM, line, 2.0, false);
    }

    @Test
    void shouldWithEv2Score20() {
        String line =
                "CC   -!- PTM: Ubiquitinated (Probable). Degraded by the proteasome.\n"
                        + "CC       {ECO:0000256|HAMAP-Rule:MF_01146}.";
        verify(CommentType.PTM, line, 2.0, false);
    }

    @Test
    void shouldWithEv3Score20() {
        String line =
                "CC   -!- PTM: Ubiquitinated (Probable). Degraded by the proteasome. {ECO:0000256}.";
        verify(CommentType.PTM, line, 2.0, false);
    }

    @Test
    void shouldWithEv4Score20() {
        String line = "CC   -!- PTM: Degraded by the proteasome. {ECO:0000256}.";
        verify(CommentType.PTM, line, 2.0, false);
    }
}
