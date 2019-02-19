package uk.ac.ebi.uniprot.domain.uniprot.description;

import java.io.Serializable;
import java.util.List;

public interface ProteinDescription extends Serializable {

	ProteinRecName getRecommendedName();

    List<ProteinAltName> getAlternativeNames();

    List<ProteinSubName> getSubmissionNames();

    List<ProteinSection> getIncludes();

    List<ProteinSection> getContains();

    boolean hasRecommendedName();

    boolean hasAlternativeNames();

    boolean hasSubmissionNames();

    boolean hasIncludes();

    boolean hasContains();

    boolean isValid();

    Flag getFlag();
    
    void setFlag(Flag flag);

}
