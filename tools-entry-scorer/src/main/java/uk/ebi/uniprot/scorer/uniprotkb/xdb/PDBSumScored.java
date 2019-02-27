package uk.ebi.uniprot.scorer.uniprotkb.xdb;

import java.util.List;

import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidenceType;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtDBCrossReference;
import uk.ebi.uniprot.scorer.uniprotkb.Consensus;
import uk.ebi.uniprot.scorer.uniprotkb.HasScore;

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
