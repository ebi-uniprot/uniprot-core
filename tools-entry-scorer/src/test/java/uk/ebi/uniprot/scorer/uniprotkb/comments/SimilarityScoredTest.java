package uk.ebi.uniprot.scorer.uniprotkb.comments;

import org.junit.Test;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.CommentType;

public class SimilarityScoredTest extends CommentScoreTestBase {
    @Test
    public void shouldScore0() throws Exception {
        String line =
                "CC   -!- SIMILARITY: Contains 1 KilA-N domain.";
        verify(CommentType.SIMILARITY, line, 0.0);

    }

    @Test
    public void shouldScore30() throws Exception {
        String line = "CC   -!- SIMILARITY: In the C-terminal section; belongs to the transposase\n" +
                "CC       35 family.";
        verify(CommentType.SIMILARITY, line, 3.0);
    }

}