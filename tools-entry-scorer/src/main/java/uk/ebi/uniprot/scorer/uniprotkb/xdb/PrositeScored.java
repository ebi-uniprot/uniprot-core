package uk.ebi.uniprot.scorer.uniprotkb.xdb;

import java.util.List;

import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidenceType;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtDBCrossReference;
import uk.ebi.uniprot.scorer.uniprotkb.Consensus;
import uk.ebi.uniprot.scorer.uniprotkb.HasScore;
import uk.ebi.uniprot.scorer.uniprotkb.ScoreUtil;
//Not used
public class PrositeScored implements HasScore {
	 private final List<UniProtDBCrossReference> xrefs;
	    private final List<EvidenceType> evidenceTypes;

	    public PrositeScored(List<UniProtDBCrossReference> xrefs, List<EvidenceType> evidenceTypes) {
	        this.xrefs = xrefs;
	        this.evidenceTypes = evidenceTypes;
	    }

	    public PrositeScored(List<UniProtDBCrossReference> xrefs) {
	        this(xrefs, null);
	    }

	    @Override
	    public double score() {
	        double score = 0;
	        boolean found3 = false;
	        boolean found01 = false;

	        for (UniProtDBCrossReference xref : xrefs) {
	            if (ScoreUtil.hasEvidence(xref.getEvidences(), evidenceTypes)) {
	                String des = xref.getProperties().get(1).getValue();
	                if (des.equals("FALSE_NEG") || des.equals("PARTIAL")) {
	                    if (!found3) {
	                        score += 3;
	                        found3 = true;
	                    }
	                } else if (!found01) {
	                    score += 0.1;
	                    found01 = true;
	                }
	            }
	        }
	        return score;
	    }

	    @Override
	    public Consensus consensus() {
	        return Consensus.PRESENCE;
	    }
}
