package org.uniprot.core.scorer.uniprotkb.xdb;

import org.uniprot.core.scorer.uniprotkb.Consensus;
import org.uniprot.core.scorer.uniprotkb.HasScore;
import org.uniprot.core.scorer.uniprotkb.ScoreUtil;
import org.uniprot.core.uniprotkb.evidence.EvidenceDatabase;
import org.uniprot.core.uniprotkb.xdb.UniProtKBCrossReference;

import java.util.List;

public class PDBScored implements HasScore {
    private List<UniProtKBCrossReference> uniProtKBCrossReferences;

    private final List<EvidenceDatabase> evidenceDatabases;

    public PDBScored(
            List<UniProtKBCrossReference> uniProtKBCrossReferences,
            List<EvidenceDatabase> evidenceDatabases) {
        this.uniProtKBCrossReferences = uniProtKBCrossReferences;
        this.evidenceDatabases = evidenceDatabases;
    }

    public PDBScored(List<UniProtKBCrossReference> uniProtKBCrossReferences) {
        this(uniProtKBCrossReferences, null);
    }

    @Override
    public double score() {
        double score = 0;
        if (!uniProtKBCrossReferences.isEmpty() && hasEvidences()) score += 1;
        return score;
    }

    private boolean hasEvidences() {
        for (UniProtKBCrossReference xref : uniProtKBCrossReferences) {
            if (ScoreUtil.hasEvidence(xref.getEvidences(), evidenceDatabases)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Consensus consensus() {
        return Consensus.PRESENCE;
    }
}
