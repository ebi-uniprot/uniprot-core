package org.uniprot.core.scorer.uniprotkb.dbx;

import org.junit.Test;
import org.uniprot.core.scorer.uniprotkb.HasScore;
import org.uniprot.core.scorer.uniprotkb.xdb.PDBSumScored;
import org.uniprot.core.uniprot.xdb.UniProtDBCrossReference;

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