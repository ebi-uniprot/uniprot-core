package uk.ac.ebi.uniprot.domain.uniprot.description;

import java.io.Serializable;
import java.util.List;

public interface ProteinDescription extends Serializable {

	ProteinRecName getRecommendedName();

    List<ProteinAltName> getAlternativeNames();
    
    Name getAllergenName();

	Name getBiotechName();

	List<Name> getCdAntigenNames();

	List<Name> getInnNames();


    List<ProteinSubName> getSubmissionNames();

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

}
