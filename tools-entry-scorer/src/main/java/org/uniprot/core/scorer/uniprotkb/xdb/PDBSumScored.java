package org.uniprot.core.scorer.uniprotkb.xdb;

import java.util.List;

import org.uniprot.core.scorer.uniprotkb.Consensus;
import org.uniprot.core.scorer.uniprotkb.HasScore;
import org.uniprot.core.uniprot.evidence.EvidenceType;
import org.uniprot.core.uniprot.xdb.UniProtDBCrossReference;

public class PDBSumScored implements HasScore {
	  @SuppressWarnings("unused")
	    private List<UniProtDBCrossReference> xrefs;

	    @SuppressWarnings("unused")
	    private final List<EvidenceType> evidenceTypes;

	    public PDBSumScored(List<UniProtDBCrossReference> xrefs, List<EvidenceType> evidenceTypes) {
	        this.xrefs = xrefs;
	        this.evidenceTypes = evidenceTypes;
	    }

	    public PDBSumScored(List<UniProtDBCrossReference> xrefs) {
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