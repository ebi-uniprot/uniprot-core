package uk.ebi.uniprot.scorer.uniprotkb.dbx;

import org.junit.Test;
import org.uniprot.core.uniprot.xdb.UniProtDBCrossReference;

import uk.ebi.uniprot.scorer.uniprotkb.HasScore;
import uk.ebi.uniprot.scorer.uniprotkb.xdb.GoScored;

import java.util.List;

public class GoScoredTest extends AbstractDBXTest {
    @Test
    public void shouldGoIEAScore2() {
        String line = "DR   GO; GO:0008483; F:transaminase activity; IEA:UniProtKB-KW.";
        testDBXrefScore(line, 2.0);
    }

    @Test
    public void shouldGoISSScore2() {
        String line = "DR   GO; GO:0008483; F:transaminase activity; ISS:UniProtKB-KW.";
        testDBXrefScore(line, 2.0);
    }

    @Test
    public void shouldGoNASScore2() {
        String line = "DR   GO; GO:0008483; F:transaminase activity; NAS:UniProtKB-KW.";
        testDBXrefScore(line, 2.0);
    }

    @Test
    public void shouldGoIPIScore5() {
        String line = "DR   GO; GO:0005515; F:protein binding; IPI:IntAct.";
        testDBXrefScore(line, 5.0);
    }

    @Test
    public void shouldGoIDAScore5() {
        String line = "DR   GO; GO:0005515; F:protein binding; IDA:IntAct.";
        testDBXrefScore(line, 5.0);
    }

    @Test
    public void shouldGoIGIScore5() {
        String line = "DR   GO; GO:0005515; F:protein binding; IGI:IntAct.";
        testDBXrefScore(line, 5.0);
    }

    @Test
    public void shouldGoIMPScore5() {
        String line = "DR   GO; GO:0005515; F:protein binding; IMP:IntAct.";
        testDBXrefScore(line, 5.0);
    }

    @Test
    public void shouldgoTASScore4() {
        String line = "DR   GO; GO:0005515; F:protein binding; TAS:IntAct.";
        testDBXrefScore(line, 4.0);
    }

    @Test
    public void shouldgoICScore4() {
        String line = "DR   GO; GO:0005515; F:protein binding; IC:IntAct.";
        testDBXrefScore(line, 4.0);
    }

    @Test
    public void shouldgoIEPScore3() {
        String line = "DR   GO; GO:0005515; F:protein binding; IEP:IntAct.";
        testDBXrefScore(line, 3.0);
    }

    @Test
    public void shouldGoRCAScore3() {
        String line = "DR   GO; GO:0005515; F:protein binding; RCA:IntAct.";
        testDBXrefScore(line, 3.0);
    }

    @Test
    public void shouldGoIGCScore3() {
        String line = "DR   GO; GO:0005515; F:protein binding; IGC:IntAct.";
        testDBXrefScore(line, 3.0);
    }

    @Test
    public void shouldGoISOScore2() {
        String line = "DR   GO; GO:0005515; F:protein binding; ISO:IntAct.";
        testDBXrefScore(line, 2.0);
    }

    @Test
    public void shouldGoISAScore2() {
        String line = "DR   GO; GO:0005515; F:protein binding; ISA:IntAct.";
        testDBXrefScore(line, 2.0);
    }

    @Test
    public void shouldGoISMScore2() {
        String line = "DR   GO; GO:0005515; F:protein binding; ISM:IntAct.";
        testDBXrefScore(line, 2.0);
    }

    @Test
    public void shouldGoIBDScore2() {
        String line = "DR   GO; GO:0005515; F:protein binding; IBD:IntAct.";
        testDBXrefScore(line, 2.0);
    }

    @Test
    public void shouldGoIKRScore2() {
        String line = "DR   GO; GO:0005515; F:protein binding; IKR:IntAct.";
        testDBXrefScore(line, 2.0);
    }

    @Test
    public void shouldGoIRDScore2() {
        String line = "DR   GO; GO:0005515; F:protein binding; IRD:IntAct.";
        testDBXrefScore(line, 2.0);
    }

    @Test
    public void shouldGoNDScore0() {
        String line = "DR   GO; GO:0005515; F:protein binding; ND:IntAct.";
        testDBXrefScore(line, 0.0);
    }

    @Test
    public void shouldGoHDAScore3() {
        String line = "DR   GO; GO:0005515; F:protein binding; HDA:IntAct.";
        testDBXrefScore(line, 3.0);
    }

    @Test
    public void shouldGoHMPScore3() {
        String line = "DR   GO; GO:0005515; F:protein binding; HMP:IntAct.";
        testDBXrefScore(line, 3.0);
    }

    @Test
    public void shouldGoHGIScore3() {
        String line = "DR   GO; GO:0005515; F:protein binding; HGI:IntAct.";
        testDBXrefScore(line, 3.0);
    }

    @Test
    public void shouldGoHEPScore2() {
        String line = "DR   GO; GO:0005515; F:protein binding; HEP:IntAct.";
        testDBXrefScore(line, 2.0);
    }

    @Test
    public void shouldGoHTPScore2() {
        String line = "DR   GO; GO:0005515; F:protein binding; HTP:IntAct.";
        testDBXrefScore(line, 2.0);
    }

    @Override
    HasScore getScored(String lines) {
        List<UniProtDBCrossReference> crossReferences = getDBXRefs(lines, "GO");
        return new GoScored(crossReferences);
    }
}
