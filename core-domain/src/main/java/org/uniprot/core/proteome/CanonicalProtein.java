package org.uniprot.core.proteome;

import java.io.Serializable;
import java.util.List;

public interface CanonicalProtein extends Serializable {
    Protein getCanonicalProtein();

    List<Protein> getRelatedProteins();
}
