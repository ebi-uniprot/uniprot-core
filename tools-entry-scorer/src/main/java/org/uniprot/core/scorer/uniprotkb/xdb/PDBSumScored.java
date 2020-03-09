package org.uniprot.core.scorer.uniprotkb.xdb;

import java.util.List;

import org.uniprot.core.scorer.uniprotkb.Consensus;
import org.uniprot.core.scorer.uniprotkb.HasScore;
import org.uniprot.core.uniprot.evidence.EvidenceDatabase;
import org.uniprot.core.uniprot.xdb.UniProtCrossReference;

public class PDBSumScored implements HasScore {
    @SuppressWarnings("unused")
    private List<UniProtCrossReference> uniProtCrossReferences;

    @SuppressWarnings("unused")
    private final List<EvidenceDatabase> evidenceDatabases;

    public PDBSumScored(
            List<UniProtCrossReference> uniProtCrossReferences,
            List<EvidenceDatabase> evidenceDatabases) {
        this.uniProtCrossReferences = uniProtCrossReferences;
        this.evidenceDatabases = evidenceDatabases;
    }

    public PDBSumScored(List<UniProtCrossReference> uniProtCrossReferences) {
        this(uniProtCrossReferences, null);
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
