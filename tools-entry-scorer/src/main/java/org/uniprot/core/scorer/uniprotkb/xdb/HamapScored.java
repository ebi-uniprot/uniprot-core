package org.uniprot.core.scorer.uniprotkb.xdb;

import java.util.List;

import org.uniprot.core.scorer.uniprotkb.Consensus;
import org.uniprot.core.scorer.uniprotkb.HasScore;
import org.uniprot.core.scorer.uniprotkb.ScoreUtil;
import org.uniprot.core.uniprot.evidence.EvidenceDatabase;
import org.uniprot.core.uniprot.xdb.UniProtDBCrossReference;

public class HamapScored implements HasScore {
    private List<UniProtDBCrossReference> xrefs;
    private final List<EvidenceDatabase> evidenceDatabases;

    public HamapScored(
            List<UniProtDBCrossReference> xrefs, List<EvidenceDatabase> evidenceDatabases) {
        this.xrefs = xrefs;
        this.evidenceDatabases = evidenceDatabases;
    }

    public HamapScored(List<UniProtDBCrossReference> xrefs) {
        this(xrefs, null);
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
        for (UniProtDBCrossReference xref : xrefs) {
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
