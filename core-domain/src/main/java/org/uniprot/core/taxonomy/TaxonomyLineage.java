package org.uniprot.core.taxonomy;

import org.uniprot.core.uniprotkb.taxonomy.OrganismName;

import java.io.Serializable;

/** @author lgonzales */
public interface TaxonomyLineage extends OrganismName, Serializable {

    long getTaxonId();

    TaxonomyRank getRank();

    boolean isHidden();

    boolean hasTaxonId();

    boolean hasRank();
}
