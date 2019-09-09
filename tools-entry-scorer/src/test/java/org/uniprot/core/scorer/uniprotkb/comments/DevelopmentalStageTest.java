package org.uniprot.core.scorer.uniprotkb.comments;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.comment.CommentType;

class DevelopmentalStageTest extends CommentScoreTestBase {
    @Test
    void shouldScore30() throws Exception {
        String line = "CC   -!- DEVELOPMENTAL STAGE: Expressed in spores.";
        verify(CommentType.DEVELOPMENTAL_STAGE, line, 3.0);
    }

    @Test
    void shouldWithEvScore30() throws Exception {
        String line = "CC   -!- DEVELOPMENTAL STAGE: Expressed in spores. {ECO:0000256|HAMAP-Rule:MF_01146}.";
        verify(CommentType.DEVELOPMENTAL_STAGE, line, 3.0);
    }
}
