package org.uniprot.core.scorer.uniprotkb.xdb;

import java.util.List;

import org.uniprot.core.scorer.uniprotkb.Consensus;
import org.uniprot.core.scorer.uniprotkb.HasScore;
import org.uniprot.core.uniprotkb.evidence.EvidenceDatabase;
import org.uniprot.core.uniprotkb.xdb.UniProtKBCrossReference;

public class PDBSumScored implements HasScore {
    @SuppressWarnings("unused")
    private List<UniProtKBCrossReference> uniProtKBCrossReferences;

    @SuppressWarnings("unused")
    private final List<EvidenceDatabase> evidenceDatabases;

    public PDBSumScored(
            List<UniProtKBCrossReference> uniProtKBCrossReferences,
            List<EvidenceDatabase> evidenceDatabases) {
        this.uniProtKBCrossReferences = uniProtKBCrossReferences;
        this.evidenceDatabases = evidenceDatabases;
    }

    public PDBSumScored(List<UniProtKBCrossReference> uniProtKBCrossReferences) {
        this(uniProtKBCrossReferences, null);
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
