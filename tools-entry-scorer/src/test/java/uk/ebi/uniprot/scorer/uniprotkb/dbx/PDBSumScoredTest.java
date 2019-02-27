package uk.ebi.uniprot.scorer.uniprotkb.dbx;

import org.junit.Test;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtDBCrossReference;
import uk.ebi.uniprot.scorer.uniprotkb.HasScore;
import uk.ebi.uniprot.scorer.uniprotkb.xdb.PDBSumScored;

import java.util.List;

public class PDBSumScoredTest extends AbstractDBXTest {
    @Test
    public void shouldScore0() {
        String line = "DR   PDBsum; 1Z7S; -.";
        testDBXrefScore(line, 0);
    }

    @Test
    public void shouldScore0Again() {
        String line = "DR   PDBsum; 1X7Z; -.";
        testDBXrefScore(line, 0);
    }

    @Override
    HasScore getScored(String lines) {
        List<UniProtDBCrossReference> crossReferences = getDBXRefs(lines, "PDBsum");
        return new PDBSumScored(crossReferences);
    }
}