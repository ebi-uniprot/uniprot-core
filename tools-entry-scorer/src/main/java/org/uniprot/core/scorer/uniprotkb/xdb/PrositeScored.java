package org.uniprot.core.scorer.uniprotkb.xdb;

import java.util.List;

import org.uniprot.core.scorer.uniprotkb.Consensus;
import org.uniprot.core.scorer.uniprotkb.HasScore;
import org.uniprot.core.scorer.uniprotkb.ScoreUtil;
import org.uniprot.core.uniprot.evidence.EvidenceDatabase;
import org.uniprot.core.uniprot.xdb.UniProtCrossReference;

// Not used
public class PrositeScored implements HasScore {
    private final List<UniProtCrossReference> uniProtCrossReferences;
    private final List<EvidenceDatabase> evidenceDatabases;

    public PrositeScored(
            List<UniProtCrossReference> uniProtCrossReferences,
            List<EvidenceDatabase> evidenceDatabases) {
        this.uniProtCrossReferences = uniProtCrossReferences;
        this.evidenceDatabases = evidenceDatabases;
    }

    public PrositeScored(List<UniProtCrossReference> uniProtCrossReferences) {
        this(uniProtCrossReferences, null);
    }

    @Override
    public double score() {
        double score = 0;
        boolean found3 = false;
        boolean found01 = false;

        for (UniProtCrossReference xref : uniProtCrossReferences) {
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
