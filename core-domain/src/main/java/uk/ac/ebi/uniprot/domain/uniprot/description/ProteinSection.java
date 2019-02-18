package uk.ac.ebi.uniprot.domain.uniprot.description;

import java.io.Serializable;
import java.util.List;

public interface ProteinSection extends Serializable {
    ProteinName getRecommendedName();

    List<ProteinName> getAlternativeNames();

    boolean hasRecommendedName();

    boolean hasAlternativeNames();
}
