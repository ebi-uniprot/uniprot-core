package org.uniprot.core.scorer.uniprotkb.xdb;

import java.util.List;

import org.uniprot.core.scorer.uniprotkb.Consensus;
import org.uniprot.core.scorer.uniprotkb.HasScore;
import org.uniprot.core.scorer.uniprotkb.ScoreUtil;
import org.uniprot.core.uniprotkb.evidence.EvidenceDatabase;
import org.uniprot.core.uniprotkb.xdb.UniProtKBCrossReference;

// Not used
public class PrositeScored implements HasScore {
    private final List<UniProtKBCrossReference> uniProtKBCrossReferences;
    private final List<EvidenceDatabase> evidenceDatabases;

    public PrositeScored(
            List<UniProtKBCrossReference> uniProtKBCrossReferences,
            List<EvidenceDatabase> evidenceDatabases) {
        this.uniProtKBCrossReferences = uniProtKBCrossReferences;
        this.evidenceDatabases = evidenceDatabases;
    }

    public PrositeScored(List<UniProtKBCrossReference> uniProtKBCrossReferences) {
        this(uniProtKBCrossReferences, null);
    }

    @Override
    public double score() {
        double score = 0;
        boolean found3 = false;
        boolean found01 = false;

        for (UniProtKBCrossReference xref : uniProtKBCrossReferences) {
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
