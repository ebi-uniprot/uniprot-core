package org.uniprot.core.scorer.uniprotkb.xdb;

import java.util.List;

import org.uniprot.core.scorer.uniprotkb.Consensus;
import org.uniprot.core.scorer.uniprotkb.HasScore;
import org.uniprot.core.scorer.uniprotkb.ScoreUtil;
import org.uniprot.core.uniprot.evidence.EvidenceDatabase;
import org.uniprot.core.uniprot.xdb.UniProtCrossReference;

public class PDBScored implements HasScore {
    private List<UniProtCrossReference> uniProtCrossReferences;

    private final List<EvidenceDatabase> evidenceDatabases;

    public PDBScored(
            List<UniProtCrossReference> uniProtCrossReferences,
            List<EvidenceDatabase> evidenceDatabases) {
        this.uniProtCrossReferences = uniProtCrossReferences;
        this.evidenceDatabases = evidenceDatabases;
    }

    public PDBScored(List<UniProtCrossReference> uniProtCrossReferences) {
        this(uniProtCrossReferences, null);
    }

    @Override
    public double score() {
        double score = 0;
        if (!uniProtCrossReferences.isEmpty() && hasEvidences()) score += 1;
        return score;
    }

    private boolean hasEvidences() {
        for (UniProtCrossReference xref : uniProtCrossReferences) {
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
