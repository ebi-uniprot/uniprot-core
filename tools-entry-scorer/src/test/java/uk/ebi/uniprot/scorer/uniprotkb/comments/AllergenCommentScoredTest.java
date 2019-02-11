package uk.ebi.uniprot.scorer.uniprotkb.comments;

import org.junit.Test;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType;

public class AllergenCommentScoredTest extends CommentScoreTestBase {
    @Test
    public void shouldSPScore30() throws Exception {
        String line = "ALLERGEN: Causes an allergic reaction in human. Binds to IgE and\n"+
        		"IgG.";
        verify(CommentType.ALLERGEN, line, 3.0, true);
    }
    
    @Test 
    public void shouldScore10() throws Exception {
        String line = "ALLERGEN: Causes an allergic reaction in human. Binds to IgE and\n"+
        		"IgG.";
        verify(CommentType.ALLERGEN, line, 1.0);
    }

    @Test
    public void shouldScore10_1() throws Exception {
        String line= "ALLERGEN: Causes an allergic reaction in human. Binds to IgE and\n"+
        		"IgG. {ECO:0000256|HAMAP-Rule:MF_01146}.";
        verify(CommentType.ALLERGEN, line, 1.0);
    }
}
