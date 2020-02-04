package org.uniprot.core.taxonomy;

import java.io.Serializable;

import org.uniprot.core.uniprot.taxonomy.OrganismName;

/** @author lgonzales */
public interface TaxonomyLineage extends OrganismName, Serializable {

    long getTaxonId();

    TaxonomyRank getRank();

    boolean isHidden();

    boolean hasTaxonId();

    boolean hasRank();
}
