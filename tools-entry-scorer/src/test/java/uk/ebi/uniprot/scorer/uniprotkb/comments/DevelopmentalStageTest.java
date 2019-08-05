package uk.ebi.uniprot.scorer.uniprotkb.comments;

import org.junit.Test;
import org.uniprot.core.uniprot.comment.CommentType;

public class DevelopmentalStageTest extends CommentScoreTestBase {
    @Test
    public void shouldScore30() throws Exception {
        String line = "CC   -!- DEVELOPMENTAL STAGE: Expressed in spores.";
        verify(CommentType.DEVELOPMENTAL_STAGE, line, 3.0);
    }

    @Test
    public void shouldWithEvScore30() throws Exception {
        String line = "CC   -!- DEVELOPMENTAL STAGE: Expressed in spores. {ECO:0000256|HAMAP-Rule:MF_01146}.";
        verify(CommentType.DEVELOPMENTAL_STAGE, line, 3.0);
    }
}
