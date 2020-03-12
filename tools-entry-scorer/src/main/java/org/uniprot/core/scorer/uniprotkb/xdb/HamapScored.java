package org.uniprot.core.scorer.uniprotkb.xdb;

import java.util.List;

import org.uniprot.core.scorer.uniprotkb.Consensus;
import org.uniprot.core.scorer.uniprotkb.HasScore;
import org.uniprot.core.scorer.uniprotkb.ScoreUtil;
import org.uniprot.core.uniprotkb.evidence.EvidenceDatabase;
import org.uniprot.core.uniprotkb.xdb.UniProtkbCrossReference;

public class HamapScored implements HasScore {
    private List<UniProtkbCrossReference> uniProtkbCrossReferences;
    private final List<EvidenceDatabase> evidenceDatabases;

    public HamapScored(
            List<UniProtkbCrossReference> uniProtkbCrossReferences,
            List<EvidenceDatabase> evidenceDatabases) {
        this.uniProtkbCrossReferences = uniProtkbCrossReferences;
        this.evidenceDatabases = evidenceDatabases;
    }

    public HamapScored(List<UniProtkbCrossReference> uniProtkbCrossReferences) {
        this(uniProtkbCrossReferences, null);
    }

    @Override
    public double score() {
        if (hasEvidences()) {
            return 0.1;
        } else {
            return 0;
        }
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
