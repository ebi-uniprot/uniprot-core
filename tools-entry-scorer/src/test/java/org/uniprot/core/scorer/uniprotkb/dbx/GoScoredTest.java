package org.uniprot.core.scorer.uniprotkb.dbx;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.scorer.uniprotkb.HasScore;
import org.uniprot.core.scorer.uniprotkb.xdb.GoScored;
import org.uniprot.core.uniprotkb.xdb.UniProtkbCrossReference;

class GoScoredTest extends AbstractDBXTest {
    @Test
    void shouldGoIEAScore2() {
        String line = "DR   GO; GO:0008483; F:transaminase activity; IEA:UniProtKB-KW.";
        testDBXrefScore(line, 2.0);
    }

    @Test
    void shouldGoISSScore2() {
        String line = "DR   GO; GO:0008483; F:transaminase activity; ISS:UniProtKB-KW.";
        testDBXrefScore(line, 2.0);
    }

    @Test
    void shouldGoNASScore2() {
        String line = "DR   GO; GO:0008483; F:transaminase activity; NAS:UniProtKB-KW.";
        testDBXrefScore(line, 2.0);
    }

    @Test
    void shouldGoIPIScore5() {
        String line = "DR   GO; GO:0005515; F:protein binding; IPI:IntAct.";
        testDBXrefScore(line, 5.0);
    }

    @Test
    void shouldGoIDAScore5() {
        String line = "DR   GO; GO:0005515; F:protein binding; IDA:IntAct.";
        testDBXrefScore(line, 5.0);
    }

    @Test
    void shouldGoIGIScore5() {
        String line = "DR   GO; GO:0005515; F:protein binding; IGI:IntAct.";
        testDBXrefScore(line, 5.0);
    }

    @Test
    void shouldGoIMPScore5() {
        String line = "DR   GO; GO:0005515; F:protein binding; IMP:IntAct.";
        testDBXrefScore(line, 5.0);
    }

    @Test
    void shouldgoTASScore4() {
        String line = "DR   GO; GO:0005515; F:protein binding; TAS:IntAct.";
        testDBXrefScore(line, 4.0);
    }

    @Test
    void shouldgoICScore4() {
        String line = "DR   GO; GO:0005515; F:protein binding; IC:IntAct.";
        testDBXrefScore(line, 4.0);
    }

    @Test
    void shouldgoIEPScore3() {
        String line = "DR   GO; GO:0005515; F:protein binding; IEP:IntAct.";
        testDBXrefScore(line, 3.0);
    }

    @Test
    void shouldGoRCAScore3() {
        String line = "DR   GO; GO:0005515; F:protein binding; RCA:IntAct.";
        testDBXrefScore(line, 3.0);
    }

    @Test
    void shouldGoIGCScore3() {
        String line = "DR   GO; GO:0005515; F:protein binding; IGC:IntAct.";
        testDBXrefScore(line, 3.0);
    }

    @Test
    void shouldGoISOScore2() {
        String line = "DR   GO; GO:0005515; F:protein binding; ISO:IntAct.";
        testDBXrefScore(line, 2.0);
    }

    @Test
    void shouldGoISAScore2() {
        String line = "DR   GO; GO:0005515; F:protein binding; ISA:IntAct.";
        testDBXrefScore(line, 2.0);
    }

    @Test
    void shouldGoISMScore2() {
        String line = "DR   GO; GO:0005515; F:protein binding; ISM:IntAct.";
        testDBXrefScore(line, 2.0);
    }

    @Test
    void shouldGoIBDScore2() {
        String line = "DR   GO; GO:0005515; F:protein binding; IBD:IntAct.";
        testDBXrefScore(line, 2.0);
    }

    @Test
    void shouldGoIKRScore2() {
        String line = "DR   GO; GO:0005515; F:protein binding; IKR:IntAct.";
        testDBXrefScore(line, 2.0);
    }

    @Test
    void shouldGoIRDScore2() {
        String line = "DR   GO; GO:0005515; F:protein binding; IRD:IntAct.";
        testDBXrefScore(line, 2.0);
    }

    @Test
    void shouldGoNDScore0() {
        String line = "DR   GO; GO:0005515; F:protein binding; ND:IntAct.";
        testDBXrefScore(line, 0.0);
    }

    @Test
    void shouldGoHDAScore3() {
        String line = "DR   GO; GO:0005515; F:protein binding; HDA:IntAct.";
        testDBXrefScore(line, 3.0);
    }

    @Test
    void shouldGoHMPScore3() {
        String line = "DR   GO; GO:0005515; F:protein binding; HMP:IntAct.";
        testDBXrefScore(line, 3.0);
    }

    @Test
    void shouldGoHGIScore3() {
        String line = "DR   GO; GO:0005515; F:protein binding; HGI:IntAct.";
        testDBXrefScore(line, 3.0);
    }

    @Test
    void shouldGoHEPScore2() {
        String line = "DR   GO; GO:0005515; F:protein binding; HEP:IntAct.";
        testDBXrefScore(line, 2.0);
    }

    @Test
    void shouldGoHTPScore2() {
        String line = "DR   GO; GO:0005515; F:protein binding; HTP:IntAct.";
        testDBXrefScore(line, 2.0);
    }

    @Override
    HasScore getScored(String lines) {
        List<UniProtkbCrossReference> crossReferences = getDBXRefs(lines, "GO");
        return new GoScored(crossReferences);
    }
}
