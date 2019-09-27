package org.uniprot.core.scorer.uniprotkb.comments;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.comment.CommentType;

class FunctionScoredTest extends CommentScoreTestBase {
    @Test
    void shouldSpScore90() {
        String line =
                "CC   -!- FUNCTION: ATP-dependent 5'-3' DNA helicase involved in nucleotide\n"
                        + "CC       excision repair (NER) of DNA.";
        verify(CommentType.FUNCTION, line, 9.0, true);
    }

    @Test
    void shouldScore30() {
        String line =
                "CC   -!- FUNCTION: ATP-dependent 5'-3' DNA helicase involved in nucleotide\n"
                        + "CC       excision repair (NER) of DNA.";
        verify(CommentType.FUNCTION, line, 3.0, false);
    }

    @Test
    void shouldWithEvScore30() {
        String line =
                "CC   -!- FUNCTION: ATP-dependent 5'-3' DNA helicase involved in nucleotide\n"
                        + "CC       excision repair (NER) of DNA. {ECO:0000256|HAMAP-Rule:MF_01146}.";
        verify(CommentType.FUNCTION, line, 3.0, false);
    }
}
