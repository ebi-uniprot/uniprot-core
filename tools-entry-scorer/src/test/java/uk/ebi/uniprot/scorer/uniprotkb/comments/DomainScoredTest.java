package uk.ebi.uniprot.scorer.uniprotkb.comments;

import org.junit.Test;
import org.uniprot.core.uniprot.comment.CommentType;

public class DomainScoredTest extends CommentScoreTestBase {
    @Test
    public void shouldScore10() throws Exception {
        String line = "CC   -!- DOMAIN: The twin Cx9C motifs are involved in the recognition by\n" +
                "CC       the mitochondrial MIA40-ERV1 disulfide relay system and the\n" +
                "CC       subsequent transfer of disulfide bonds by dithiol/disulfide\n" +
                "CC       exchange reactions to the newly imported protein. {ECO:0000256|HAMAP-Rule:MF_01146}.";
        verify(CommentType.DOMAIN, line, 1.0);
    }

    @Test
    public void shouldWithEvScore10() throws Exception {
        String line = "CC   -!- DOMAIN: The twin Cx9C motifs are involved in the recognition by\n" +
                "CC       the mitochondrial MIA40-ERV1 disulfide relay system and the\n" +
                "CC       subsequent transfer of disulfide bonds by dithiol/disulfide\n" +
                "CC       exchange reactions to the newly imported protein. {ECO:0000256}.";
        verify(CommentType.DOMAIN, line, 1.0);
    }

    @Test
    public void shouldSpScore10() throws Exception {
        String line = "CC   -!- DOMAIN: The twin Cx9C motifs are involved in the recognition by\n" +
                "CC       the mitochondrial MIA40-ERV1 disulfide relay system and the\n" +
                "CC       subsequent transfer of disulfide bonds by dithiol/disulfide\n" +
                "CC       exchange reactions to the newly imported protein.";
        verify(CommentType.DOMAIN, line, 1.0, true);
    }
}
