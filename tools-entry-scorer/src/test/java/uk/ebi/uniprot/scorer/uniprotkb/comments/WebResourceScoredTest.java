package uk.ebi.uniprot.scorer.uniprotkb.comments;

import org.junit.Test;
import org.uniprot.core.uniprot.comment.CommentType;

public class WebResourceScoredTest extends CommentScoreTestBase {
    @Test
    public void test1() throws Exception {
        String line = "CC   -!- WEB RESOURCE: Name=GeneReviews;\n" +
                "CC       URL=\"http://www.ncbi.nlm.nih.gov/sites/GeneTests/lab/gene/PPP2R2B\";";
        verify(CommentType.WEBRESOURCE, line, 1.0);
    }

}
