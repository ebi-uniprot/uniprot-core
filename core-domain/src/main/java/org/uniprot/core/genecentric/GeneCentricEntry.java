package org.uniprot.core.genecentric;

import java.io.Serializable;
import java.util.List;

/**
 * @author lgonzales
 * @since 15/10/2020
 */
public interface GeneCentricEntry extends Serializable {

    String getProteomeId();

    Protein getCanonicalProtein();

    List<Protein> getRelatedProteins();
}
