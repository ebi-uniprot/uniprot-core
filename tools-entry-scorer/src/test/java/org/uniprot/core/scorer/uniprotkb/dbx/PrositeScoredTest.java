package org.uniprot.core.scorer.uniprotkb.dbx;

import org.junit.jupiter.api.Test;
import org.uniprot.core.scorer.uniprotkb.HasScore;
import org.uniprot.core.scorer.uniprotkb.xdb.PrositeScored;
import org.uniprot.core.uniprot.xdb.UniProtDBCrossReference;

import java.util.List;

class PrositeScoredTest extends AbstractDBXTest {
    @Test
    void shouldPrositeScore01() {
        String line = "DR   PROSITE; PS00028; ZINC_FINGER_C2H2_1; 4.";
        testDBXrefScore(line, 0.1);
    }

    @Test
    void shouldProsite5Score01() {
        String line = "DR   PROSITE; PS00237; G_PROTEIN_RECEP_F1_1; 1.";
        testDBXrefScore(line, 0.1);
    }

    @Test
    void shouldProsite4Score01() {
        String line = "DR   PROSITE; PS00603; TK_CELLULAR_TYPE; 1.";
        testDBXrefScore(line, 0.1);
    }

    @Test
    void shouldProsite3Score01() {
        String line = "DR   PROSITE; PS50234; VWFA; 1.";
        testDBXrefScore(line, 0.1);
    }

    @Test
    void shouldProsite2Score01() {
        String line = "DR   PROSITE; PS00134; TRYPSIN_HIS; 1.";
        testDBXrefScore(line, 0.1);
    }

    @Test
    void shouldProsite1Score01() {
        String line = "DR   PROSITE; PS00603; TK_CELLULAR_TYPE; 1.\n" +
                "DR   PROSITE; PS50234; VWFA; 1.";
        testDBXrefScore(line, 0.1);
    }

    @Test
    void shouldScore01() {
        String line = "DR   PROSITE; PS00603; TK_CELLULAR_TYPE; 1.\n" +
                "DR   PROSITE; PS00028; ZINC_FINGER_C2H2_1; 4.\n" +
                "DR   PROSITE; PS50234; VWFA; 1.";
        testDBXrefScore(line, 0.1);
    }

    @Override
    HasScore getScored(String lines) {
        List<UniProtDBCrossReference> crossReferences = getDBXRefs(lines, "PROSITE");
        return new PrositeScored(crossReferences);
    }
}