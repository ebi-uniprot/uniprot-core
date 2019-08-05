package uk.ebi.uniprot.scorer.uniprotkb.comments;

import org.junit.Test;
import org.uniprot.core.uniprot.comment.CommentType;

public class DiseaseScoredTest extends CommentScoreTestBase {
    @Test
    public void shouldSpScore90() throws Exception {
        String line = "CC   -!- DISEASE: Deafness, autosomal recessive, 12 (DFNB12) [MIM:601386]:\n" +
                "CC       A form of non-syndromic sensorineural hearing loss. Sensorineural\n" +
                "CC       deafness results from damage to the neural receptors of the inner\n" +
                "CC       ear, the nerve pathways to the brain, or the area of the brain\n" +
                "CC       that receives sound information. Note=The disease is caused by\n" +
                "CC       mutations affecting the gene represented in this entry.";
        verify(CommentType.DISEASE, line, 9.0, true);

    }

    @Test
    public void shouldScore90() throws Exception {
        String line = "CC   -!- DISEASE: Deafness, autosomal recessive, 12 (DFNB12) [MIM:601386]:\n" +
                "CC       A form of non-syndromic sensorineural hearing loss. Sensorineural\n" +
                "CC       deafness results from damage to the neural receptors of the inner\n" +
                "CC       ear, the nerve pathways to the brain, or the area of the brain\n" +
                "CC       that receives sound information. Note=The disease is caused by\n" +
                "CC       mutations affecting the gene represented in this entry.";
        verify(CommentType.DISEASE, line, 9.0, false);

    }

}
