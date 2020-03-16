package org.uniprot.core.scorer.uniprotkb.xdb;

import java.util.List;

import org.uniprot.core.scorer.uniprotkb.Consensus;
import org.uniprot.core.scorer.uniprotkb.HasScore;
import org.uniprot.core.scorer.uniprotkb.ScoreUtil;
import org.uniprot.core.uniprotkb.evidence.EvidenceDatabase;
import org.uniprot.core.uniprotkb.xdb.UniProtkbCrossReference;

public class PDBScored implements HasScore {
    private List<UniProtkbCrossReference> uniProtkbCrossReferences;

    private final List<EvidenceDatabase> evidenceDatabases;

    public PDBScored(
            List<UniProtkbCrossReference> uniProtkbCrossReferences,
            List<EvidenceDatabase> evidenceDatabases) {
        this.uniProtkbCrossReferences = uniProtkbCrossReferences;
        this.evidenceDatabases = evidenceDatabases;
    }

    public PDBScored(List<UniProtkbCrossReference> uniProtkbCrossReferences) {
        this(uniProtkbCrossReferences, null);
    }

    @Override
    public double score() {
        double score = 0;
        if (!uniProtkbCrossReferences.isEmpty() && hasEvidences()) score += 1;
        return score;
    }

    private boolean hasEvidences() {
        for (UniProtkbCrossReference xref : uniProtkbCrossReferences) {
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
