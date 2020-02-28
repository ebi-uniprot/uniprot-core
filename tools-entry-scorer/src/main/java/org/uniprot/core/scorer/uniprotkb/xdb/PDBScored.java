package org.uniprot.core.scorer.uniprotkb.xdb;

import java.util.List;

import org.uniprot.core.scorer.uniprotkb.Consensus;
import org.uniprot.core.scorer.uniprotkb.HasScore;
import org.uniprot.core.scorer.uniprotkb.ScoreUtil;
import org.uniprot.core.uniprot.evidence.EvidenceDatabase;
import org.uniprot.core.uniprot.xdb.UniProtDBCrossReference;

public class PDBScored implements HasScore {
    private List<UniProtDBCrossReference> xrefs;

    private final List<EvidenceDatabase> evidenceDatabases;

    public PDBScored(
            List<UniProtDBCrossReference> xrefs, List<EvidenceDatabase> evidenceDatabases) {
        this.xrefs = xrefs;
        this.evidenceDatabases = evidenceDatabases;
    }

    public PDBScored(List<UniProtDBCrossReference> xrefs) {
        this(xrefs, null);
    }

    @Override
    public double score() {
        double score = 0;
        if (!xrefs.isEmpty() && hasEvidences()) score += 1;
        return score;
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
