package org.uniprot.core.scorer.uniprotkb.xdb;

import java.util.List;

import org.uniprot.core.scorer.uniprotkb.Consensus;
import org.uniprot.core.scorer.uniprotkb.HasScore;
import org.uniprot.core.uniprotkb.evidence.EvidenceDatabase;
import org.uniprot.core.uniprotkb.xdb.UniProtkbCrossReference;

public class PDBSumScored implements HasScore {
    @SuppressWarnings("unused")
    private List<UniProtkbCrossReference> uniProtkbCrossReferences;

    @SuppressWarnings("unused")
    private final List<EvidenceDatabase> evidenceDatabases;

    public PDBSumScored(
            List<UniProtkbCrossReference> uniProtkbCrossReferences,
            List<EvidenceDatabase> evidenceDatabases) {
        this.uniProtkbCrossReferences = uniProtkbCrossReferences;
        this.evidenceDatabases = evidenceDatabases;
    }

    public PDBSumScored(List<UniProtkbCrossReference> uniProtkbCrossReferences) {
        this(uniProtkbCrossReferences, null);
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
