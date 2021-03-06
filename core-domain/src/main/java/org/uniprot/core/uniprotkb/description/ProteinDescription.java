package org.uniprot.core.uniprotkb.description;

import java.io.Serializable;
import java.util.List;

public interface ProteinDescription extends Serializable {

    ProteinName getRecommendedName();

    List<ProteinName> getAlternativeNames();

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

    boolean hasFlag();

    Flag getFlag();
}
