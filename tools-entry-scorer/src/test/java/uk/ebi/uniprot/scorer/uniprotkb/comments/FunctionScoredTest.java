package uk.ebi.uniprot.scorer.uniprotkb.comments;

import org.junit.Test;
import org.uniprot.core.uniprot.comment.CommentType;

public class FunctionScoredTest extends CommentScoreTestBase {
    @Test
    public void shouldSpScore90() throws Exception {
        String line = "CC   -!- FUNCTION: ATP-dependent 5'-3' DNA helicase involved in nucleotide\n" +
                "CC       excision repair (NER) of DNA.";
        verify(CommentType.FUNCTION, line, 9.0, true);
    }

    @Test
    public void shouldScore30() throws Exception {
        String line = "CC   -!- FUNCTION: ATP-dependent 5'-3' DNA helicase involved in nucleotide\n" +
                "CC       excision repair (NER) of DNA.";
        verify(CommentType.FUNCTION, line, 3.0, false);
    }

    @Test
    public void shouldWithEvScore30() throws Exception {
        String line = "CC   -!- FUNCTION: ATP-dependent 5'-3' DNA helicase involved in nucleotide\n" +
                "CC       excision repair (NER) of DNA. {ECO:0000256|HAMAP-Rule:MF_01146}.";
        verify(CommentType.FUNCTION, line, 3.0, false);
    }
}
