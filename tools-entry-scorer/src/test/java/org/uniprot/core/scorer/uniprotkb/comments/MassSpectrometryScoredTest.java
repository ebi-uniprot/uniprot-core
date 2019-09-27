package org.uniprot.core.scorer.uniprotkb.comments;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.comment.CommentType;

class MassSpectrometryScoredTest extends CommentScoreTestBase {
    @Test
    void shouldScore90() throws Exception {
        String line =
                "CC   -!- MASS SPECTROMETRY: Mass=33253; Mass_error=0.5; Method=MALDI;\n"
                        + "CC       Range=2-306; Evidence={ECO:0000269|EMBL:EOP66756.1};";
        verify(CommentType.MASS_SPECTROMETRY, line, 9.0);
    }
}
