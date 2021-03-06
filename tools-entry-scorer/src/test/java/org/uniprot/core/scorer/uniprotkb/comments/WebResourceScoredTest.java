package org.uniprot.core.scorer.uniprotkb.comments;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.comment.CommentType;

class WebResourceScoredTest extends CommentScoreTestBase {
    @Test
    void test1() throws Exception {
        String line =
                "CC   -!- WEB RESOURCE: Name=GeneReviews;\n"
                        + "CC      "
                        + " URL=\"http://www.ncbi.nlm.nih.gov/sites/GeneTests/lab/gene/PPP2R2B\";";
        verify(CommentType.WEBRESOURCE, line, 1.0);
    }
}
