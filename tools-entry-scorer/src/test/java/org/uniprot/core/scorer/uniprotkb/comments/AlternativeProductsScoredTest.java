package org.uniprot.core.scorer.uniprotkb.comments;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.comment.CommentType;

/**
 * Created by IntelliJ IDEA. User: spatient Date: 17-Jul-2007 Time: 10:47:25 To change this template
 * use File | Settings | File Templates.
 */
class AlternativeProductsScoredTest extends CommentScoreTestBase {
    @Test
    void shouldScore18() throws Exception {
        verify(
                CommentType.ALTERNATIVE_PRODUCTS,
                "ALTERNATIVE PRODUCTS:\n"
                        + "Event=Alternative splicing; Named isoforms=6;\n"
                        + "  Comment=Additional isoforms seem to exist.;\n"
                        + "Name=1; Synonyms=A;\n"
                        + "  IsoId=Q9V8R9-1; Sequence=Displayed;\n"
                        + "  Note=Does not exhibit APOBEC1 complementation activity. Ref.4\n"
                        + "  sequence is in conflict in positions: 33:I->T. No experimental\n"
                        + "  confirmation available.;\n"
                        + "Name=2;\n"
                        + "  IsoId=Q9V8R9-2; Sequence=VSP_000476, VSP_000477, VSP_000479,\n"
                        + "                           VSP_000480, VSP_000481;\n"
                        + "Name=Bim-alpha3; Synonyms=BCL2-like 11 transcript variant 10,\n"
                        + "Bim-AD, BimAD;\n"
                        + "  IsoId=Q9V8R9-3; Sequence=VSP_000475, VSP_000478, VSP_000479;\n"
                        + "Name=4; Synonyms=B;\n"
                        + "  IsoId=Q9V8R9-4; Sequence=VSP_000476, VSP_000477, VSP_000479;\n"
                        + "Name=5;\n"
                        + "  IsoId=Q9V8R9-5; Sequence=VSP_000474, VSP_000478;\n"
                        + "  Note=No experimental confirmation available.;\n"
                        + "Name=6; Synonyms=D;\n"
                        + "  IsoId=Q9V8R9-6; Sequence=Described;\n"
                        + "  Note=No experimental confirmation available.;",
                18.0);
    }
}
