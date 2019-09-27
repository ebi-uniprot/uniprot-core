package org.uniprot.core.taxonomy;

import java.io.Serializable;

/** @author lgonzales */
public interface TaxonomyLineage extends Serializable {

    long getTaxonId();

    String getScientificName();

    TaxonomyRank getRank();

    boolean isHidden();

    boolean hasTaxonId();

    boolean hasScientificName();

    boolean hasRank();
}
