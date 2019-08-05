package uk.ebi.uniprot.scorer.uniprotkb.xdb;

import java.util.List;

import org.uniprot.core.uniprot.evidence.EvidenceType;
import org.uniprot.core.uniprot.xdb.UniProtDBCrossReference;

import uk.ebi.uniprot.scorer.uniprotkb.Consensus;
import uk.ebi.uniprot.scorer.uniprotkb.HasScore;
import uk.ebi.uniprot.scorer.uniprotkb.ScoreUtil;

public class GoScored implements HasScore {
	 private final List<UniProtDBCrossReference> xrefs;
	    private final List<EvidenceType> evidenceTypes;

	    public GoScored(List<UniProtDBCrossReference> xrefs, List<EvidenceType> evidenceTypes) {
	        this.xrefs = xrefs;
	        this.evidenceTypes = evidenceTypes;
	    }

	    public GoScored(List<UniProtDBCrossReference> xrefs) {
	        this(xrefs, null);
	    }

	    @Override
	    public double score() {
	        double score = 0;
	        for (UniProtDBCrossReference xref : xrefs) {
	            if (ScoreUtil.hasEvidence(xref.getEvidences(), evidenceTypes))
	                score += score(xref);
	        }
	        return score;
	    }

	    private double score(UniProtDBCrossReference xref) {

	        String type = xref.getProperties().get(1).getValue();
	        int index = type.indexOf(':');
	        if (index != -1)
	            type = type.substring(0, index);
	        double score = 0;
	        // this is the status
	        switch (type) {

	            case "IDA":
	            case "IMP":
	            case "IGI":
	            case "IPI":
	                score += 5;
	                break;
	            case "TAS":
	            case "IC":
	                score += 4;
	                break;
	            case "EXP":
	            case "IEP":
	            case "IGC":
	            case "RCA":
	            case "HDA":
	            case "HMP":
	            case "HGI":
	                score += 3;
	                break;
	            case "ISS":
	            case "ISO":
	            case "ISA":
	            case "ISM":
	            case "IBA":
	            case "IBD":
	            case "IKR":
	            case "IRD":
	            case "NAS":
	            case "IEA":
	            case "HEP":
	            case "HTP":
	                score += 2;
	                break;

	            // all others score 0!
	            default:
	                score += 0;
	                break;
	        }

	        return score;
	    }

	    @Override
	    public Consensus consensus() {
	        return Consensus.NUMBER;
	    }

}
