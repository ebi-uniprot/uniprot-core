package org.uniprot.core.scorer.uniprotkb.comments;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.comment.CommentType;

public class PTMScoredTest extends CommentScoreTestBase {
    @Test
    public void shouldSpScore60() throws Exception {
        String line = "CC   -!- PTM: Phosphorylated by CDC28.";
        verify(CommentType.PTM, line, 6.0, true);

    }

    @Test
    public void shouldScore20() throws Exception {
        String line = "CC   -!- PTM: Phosphorylated by CDC28.";
        verify(CommentType.PTM, line, 2.0, false);

    }

    @Test
    public void shouldWithEvScore20() throws Exception {
        String line = "CC   -!- PTM: Phosphorylated by CDC28. {ECO:0000256|HAMAP-Rule:MF_01146}.";
        verify(CommentType.PTM, line, 2.0, false);
    }

    @Test
    public void shouldWithEv2Score20() throws Exception {
        String line = "CC   -!- PTM: Ubiquitinated (Probable). Degraded by the proteasome.\n" +
                "CC       {ECO:0000256|HAMAP-Rule:MF_01146}.";
        verify(CommentType.PTM, line, 2.0, false);
    }

    @Test
    public void shouldWithEv3Score20() throws Exception {
        String line = "CC   -!- PTM: Ubiquitinated (Probable). Degraded by the proteasome. {ECO:0000256}.";
        verify(CommentType.PTM, line, 2.0, false);
    }

    @Test
    public void shouldWithEv4Score20() throws Exception {
        String line = "CC   -!- PTM: Degraded by the proteasome. {ECO:0000256}.";
        verify(CommentType.PTM, line, 2.0, false);
    }
}
