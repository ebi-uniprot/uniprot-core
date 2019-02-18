package uk.ac.ebi.uniprot.domain.uniprot.description;

import java.util.List;

public interface ProteinDescription {

    ProteinName getRecommendedName();

    List<ProteinName> getAlternativeNames();

    List<ProteinName> getSubmissionNames();

    Name getAllergenName();

    Name getBiotechName();

    List<Name> getCdAntigenNames();

    List<Name> getInnNames();

    List<ProteinSection> getIncludes();

    List<ProteinSection> getContains();

    boolean hasRecommendedName();

    boolean hasAlternativeNames();

    boolean hasSubmissionNames();

    boolean hasAllergenName();

    boolean hasBiotechName();

    boolean hasCdAntigenNames();

    boolean hasInnNames();

    boolean hasIncludes();

    boolean hasContains();

    boolean isValid();

    Flag getFlag();
    
    void setFlag(Flag flag);

    boolean hasFlag();

}
