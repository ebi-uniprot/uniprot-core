package org.uniprot.core.scorer.uniprotkb.comments;


import org.uniprot.core.scorer.uniprotkb.HasScore;
import org.uniprot.core.uniprot.evidence.EvidenceCode;

public interface CommentScored extends HasScore {
    public void setIsSwissProt(boolean b);

    public EvidenceCode getDefaultEvidenceCode();

    public boolean isSwissProt();
}
