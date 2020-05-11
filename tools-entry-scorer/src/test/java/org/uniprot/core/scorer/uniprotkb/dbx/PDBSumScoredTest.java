package org.uniprot.core.scorer.uniprotkb.dbx;

import org.junit.jupiter.api.Test;
import org.uniprot.core.scorer.uniprotkb.HasScore;
import org.uniprot.core.scorer.uniprotkb.xdb.PDBSumScored;
import org.uniprot.core.uniprotkb.xdb.UniProtKBCrossReference;

import java.util.List;

class PDBSumScoredTest extends AbstractDBXTest {
    @Test
    void shouldScore0() {
        String line = "DR   PDBsum; 1Z7S; -.";
        testDBXrefScore(line, 0);
    }

    @Test
    void shouldScore0Again() {
        String line = "DR   PDBsum; 1X7Z; -.";
        testDBXrefScore(line, 0);
    }

    @Override
    HasScore getScored(String lines) {
        List<UniProtKBCrossReference> crossReferences = getDBXRefs(lines, "PDBsum");
        return new PDBSumScored(crossReferences);
    }
}
