package uk.ebi.uniprot.scorer.uniprotkb.dbx;

import org.junit.Test;
import org.uniprot.core.uniprot.xdb.UniProtDBCrossReference;

import uk.ebi.uniprot.scorer.uniprotkb.HasScore;
import uk.ebi.uniprot.scorer.uniprotkb.xdb.HamapScored;

import java.util.List;

public class HamapScoredTest extends AbstractDBXTest {
    @Test
    public void shouldScore01() {
        String line = "DR   HAMAP; MF_01546; AaeX; 1; atypical.";
        testDBXrefScore(line, 0.1);
    }

    @Test
    public void shouldHamap7Score01() {
        String line = "DR   HAMAP; MF_01395; AcetylCoA_CT_beta; 1; fused.";
        testDBXrefScore(line, 0.1);
    }

    @Test
    public void shouldHamap6Score01() {
        String line = "DR   HAMAP; MF_01105; N-acetyl_glu_synth; 1; atypical/fused.";
        testDBXrefScore(line, 0.1);

    }

    @Test
    public void shouldHamap5Score01() {
        String line = "DR   HAMAP; MF_00006; Arg_succ_lyase; 1; fused.";
        testDBXrefScore(line, 0.1);
    }

    @Test
    public void shouldHamap4Score01() {
        String line = "DR   HAMAP; MF_00229; His_ammonia-lyase; 1; -.";
        testDBXrefScore(line, 0.1);
    }

    @Test
    public void shouldHamap3Score01() {
        String line = "DR   HAMAP; MF_00229; His_ammonia-lyase; 1; -.\n" +
                "DR   HAMAP; MF_01105; N-acetyl_glu_synth; 1; atypical/fused.";
        testDBXrefScore(line, 0.1);
    }

    @Test
    public void shouldHamap2Score01() {
        String line = "DR   HAMAP; MF_00229; His_ammonia-lyase; 1; -.\n" +
                "DR   HAMAP; MF_00229; His_ammonia-lyase; 2321; -.\n" +
                "DR   HAMAP; MF_00006; Arg_succ_lyase; 1; fused.\n" +
                "DR   HAMAP; MF_01105; N-acetyl_glu_synth; 1; atypical/fused.";
        testDBXrefScore(line, 0.1);

    }

    @Test
    public void shouldHamapScore01() {
        String line = "DR   HAMAP; MF_00229; His_ammonia-lyase; 1; -.\n" +
                "DR   HAMAP; MF_00229; His_ammonia-lyase32; 1; -.";
        testDBXrefScore(line, 0.1);
    }

    @Override
    HasScore getScored(String lines) {
        List<UniProtDBCrossReference> crossReferences = getDBXRefs(lines, "HAMAP");
        return new HamapScored(crossReferences);
    }
}