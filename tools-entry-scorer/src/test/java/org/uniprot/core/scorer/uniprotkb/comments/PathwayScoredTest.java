package org.uniprot.core.scorer.uniprotkb.comments;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.comment.CommentType;

class PathwayScoredTest extends CommentScoreTestBase {
    @Test
    void shouldSpScore30() {
        String line =
                "CC   -!- PATHWAY: Amino-acid biosynthesis; L-methionine biosynthesis via\n"
                        + "CC       salvage pathway; S-methyl-5-thio-alpha-D-ribose 1-phosphate from\n"
                        + "CC       S-methyl-5'-thioadenosine (hydrolase route): step 1/2.";
        verify(CommentType.PATHWAY, line, 3.0, true);
    }

    @Test
    void shouldScore30() throws Exception {
        String line =
                "CC   -!- PATHWAY: Amino-acid biosynthesis; L-methionine biosynthesis via\n"
                        + "CC       salvage pathway; S-methyl-5-thio-alpha-D-ribose 1-phosphate from\n"
                        + "CC       S-methyl-5'-thioadenosine (hydrolase route): step 1/2.";
        verify(CommentType.PATHWAY, line, 3.0);
    }

    @Test
    void shouldWithEvScore30() throws Exception {
        String line =
                "CC   -!- PATHWAY: Amino-acid biosynthesis; L-methionine biosynthesis via\n"
                        + "CC       salvage pathway; S-methyl-5-thio-alpha-D-ribose 1-phosphate from\n"
                        + "CC       S-methyl-5'-thioadenosine (hydrolase route): step 1/2. {ECO:0000256|HAMAP-Rule:MF_01146}.";
        verify(CommentType.PATHWAY, line, 3.0);
    }
}
