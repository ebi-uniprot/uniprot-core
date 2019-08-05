package org.uniprot.core.scorer.uniprotkb.comments;

import org.junit.Test;
import org.uniprot.core.uniprot.comment.CommentType;

public class MassSpectrometryScoredTest extends CommentScoreTestBase {
    @Test
    public void shouldScore90() throws Exception {
        String line = "CC   -!- MASS SPECTROMETRY: Mass=33253; Mass_error=0.5; Method=MALDI;\n"+
        					"CC       Range=2-306; Evidence={ECO:0000269|EMBL:EOP66756.1};";
        verify(CommentType.MASS_SPECTROMETRY, line, 9.0);
    }

}
