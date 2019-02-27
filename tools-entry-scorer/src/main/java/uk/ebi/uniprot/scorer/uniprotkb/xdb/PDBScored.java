package uk.ebi.uniprot.scorer.uniprotkb.xdb;

import java.util.List;

import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidenceType;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtDBCrossReference;
import uk.ebi.uniprot.scorer.uniprotkb.Consensus;
import uk.ebi.uniprot.scorer.uniprotkb.HasScore;
import uk.ebi.uniprot.scorer.uniprotkb.ScoreUtil;

public class PDBScored implements HasScore {
	 private List<UniProtDBCrossReference> xrefs;

	    private final List<EvidenceType> evidenceTypes;

	    public PDBScored(List<UniProtDBCrossReference> xrefs, List<EvidenceType> evidenceTypes) {
	        this.xrefs = xrefs;
	        this.evidenceTypes = evidenceTypes;
	    }

	    public PDBScored(List<UniProtDBCrossReference> xrefs) {
	        this(xrefs, null);
	    }

	    @Override
	    public double score() {
	        double score = 0;
	        if (!xrefs.isEmpty() && hasEvidences())
	            score += 1;
	        return score;
	    }

	    private boolean hasEvidences() {
	        for (UniProtDBCrossReference xref : xrefs) {
	            if (ScoreUtil.hasEvidence(xref.getEvidences(), evidenceTypes)) {
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
