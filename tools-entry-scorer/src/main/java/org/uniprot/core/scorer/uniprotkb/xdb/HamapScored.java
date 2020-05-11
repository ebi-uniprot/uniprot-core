package org.uniprot.core.scorer.uniprotkb.xdb;

import org.uniprot.core.scorer.uniprotkb.Consensus;
import org.uniprot.core.scorer.uniprotkb.HasScore;
import org.uniprot.core.scorer.uniprotkb.ScoreUtil;
import org.uniprot.core.uniprotkb.evidence.EvidenceDatabase;
import org.uniprot.core.uniprotkb.xdb.UniProtKBCrossReference;

import java.util.List;

public class HamapScored implements HasScore {
    private List<UniProtKBCrossReference> uniProtKBCrossReferences;
    private final List<EvidenceDatabase> evidenceDatabases;

    public HamapScored(
            List<UniProtKBCrossReference> uniProtKBCrossReferences,
            List<EvidenceDatabase> evidenceDatabases) {
        this.uniProtKBCrossReferences = uniProtKBCrossReferences;
        this.evidenceDatabases = evidenceDatabases;
    }

    public HamapScored(List<UniProtKBCrossReference> uniProtKBCrossReferences) {
        this(uniProtKBCrossReferences, null);
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
