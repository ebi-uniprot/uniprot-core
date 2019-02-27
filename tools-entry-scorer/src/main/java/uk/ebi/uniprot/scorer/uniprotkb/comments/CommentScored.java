package uk.ebi.uniprot.scorer.uniprotkb.comments;


import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidenceCode;
import uk.ebi.uniprot.scorer.uniprotkb.HasScore;

public interface CommentScored extends HasScore {
    public void setIsSwissProt(boolean b);

    public EvidenceCode getDefaultEvidenceCode();

    public boolean isSwissProt();
}
