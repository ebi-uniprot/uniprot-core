package org.uniprot.core.uniprotkb.description;

import java.io.Serializable;
import java.util.List;

public interface ProteinSection extends Serializable {
    ProteinName getRecommendedName();

    List<ProteinName> getAlternativeNames();

    Name getAllergenName();

    Name getBiotechName();

    List<Name> getCdAntigenNames();

    List<Name> getInnNames();

    boolean hasRecommendedName();

    boolean hasAlternativeNames();

    boolean hasAllergenName();

    boolean hasBiotechName();

    boolean hasCdAntigenNames();

    boolean hasInnNames();
}
