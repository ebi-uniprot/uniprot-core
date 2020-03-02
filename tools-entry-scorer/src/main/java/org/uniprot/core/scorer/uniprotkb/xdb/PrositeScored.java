package org.uniprot.core.scorer.uniprotkb.xdb;

import java.util.List;

import org.uniprot.core.scorer.uniprotkb.Consensus;
import org.uniprot.core.scorer.uniprotkb.HasScore;
import org.uniprot.core.scorer.uniprotkb.ScoreUtil;
import org.uniprot.core.uniprot.evidence.EvidenceDatabase;
import org.uniprot.core.uniprot.xdb.UniProtCrossReference;

// Not used
public class PrositeScored implements HasScore {
    private final List<UniProtCrossReference> xrefs;
    private final List<EvidenceDatabase> evidenceDatabases;

    public PrositeScored(
            List<UniProtCrossReference> xrefs, List<EvidenceDatabase> evidenceDatabases) {
        this.xrefs = xrefs;
        this.evidenceDatabases = evidenceDatabases;
    }

    public PrositeScored(List<UniProtCrossReference> xrefs) {
        this(xrefs, null);
    }

    @Override
    public double score() {
        double score = 0;
        boolean found3 = false;
        boolean found01 = false;

        for (UniProtCrossReference xref : xrefs) {
            if (ScoreUtil.hasEvidence(xref.getEvidences(), evidenceDatabases)) {
                String des = xref.getProperties().get(1).getValue();
                if (des.equals("FALSE_NEG") || des.equals("PARTIAL")) {
                    if (!found3) {
                        score += 3;
                        found3 = true;
                    }
                } else if (!found01) {
                    score += 0.1;
                    found01 = true;
                }
            }
        }
        return score;
    }

    @Override
    public Consensus consensus() {
        return Consensus.PRESENCE;
    }
}
