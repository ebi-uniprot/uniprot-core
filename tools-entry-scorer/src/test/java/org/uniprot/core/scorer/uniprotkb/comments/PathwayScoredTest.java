package org.uniprot.core.scorer.uniprotkb.comments;

import org.junit.Test;
import org.uniprot.core.uniprot.comment.CommentType;

public class PathwayScoredTest extends CommentScoreTestBase {
    @Test
    public void shouldSpScore30() throws Exception {
        String line = "CC   -!- PATHWAY: Amino-acid biosynthesis; L-methionine biosynthesis via\n" +
                "CC       salvage pathway; S-methyl-5-thio-alpha-D-ribose 1-phosphate from\n" +
                "CC       S-methyl-5'-thioadenosine (hydrolase route): step 1/2.";
        verify(CommentType.PATHWAY, line, 3.0, true);
    }

    @Test
    public void shouldScore30() throws Exception {
        String line = "CC   -!- PATHWAY: Amino-acid biosynthesis; L-methionine biosynthesis via\n" +
                "CC       salvage pathway; S-methyl-5-thio-alpha-D-ribose 1-phosphate from\n" +
                "CC       S-methyl-5'-thioadenosine (hydrolase route): step 1/2.";
        verify(CommentType.PATHWAY, line, 3.0);

    }

    @Test
    public void shouldWithEvScore30() throws Exception {
        String line = "CC   -!- PATHWAY: Amino-acid biosynthesis; L-methionine biosynthesis via\n" +
                "CC       salvage pathway; S-methyl-5-thio-alpha-D-ribose 1-phosphate from\n" +
                "CC       S-methyl-5'-thioadenosine (hydrolase route): step 1/2. {ECO:0000256|HAMAP-Rule:MF_01146}.";
        verify(CommentType.PATHWAY, line, 3.0);

    }
}
