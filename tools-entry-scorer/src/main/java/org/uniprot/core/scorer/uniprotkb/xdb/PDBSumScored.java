package org.uniprot.core.scorer.uniprotkb.xdb;

import java.util.List;

import org.uniprot.core.scorer.uniprotkb.Consensus;
import org.uniprot.core.scorer.uniprotkb.HasScore;
import org.uniprot.core.uniprot.evidence.EvidenceDatabase;
import org.uniprot.core.uniprot.xdb.UniProtCrossReference;

public class PDBSumScored implements HasScore {
    @SuppressWarnings("unused")
    private List<UniProtCrossReference> xrefs;

    @SuppressWarnings("unused")
    private final List<EvidenceDatabase> evidenceDatabases;

    public PDBSumScored(
            List<UniProtCrossReference> xrefs, List<EvidenceDatabase> evidenceDatabases) {
        this.xrefs = xrefs;
        this.evidenceDatabases = evidenceDatabases;
    }

    public PDBSumScored(List<UniProtCrossReference> xrefs) {
        this(xrefs, null);
    }

    // PDBsum replicates PDB DR lines so PDBsum DR lines could be scored as zero.
    @Override
    public double score() {
        return 0.0d;
    }

    @Override
    public Consensus consensus() {
        return Consensus.PRESENCE;
    }
}
