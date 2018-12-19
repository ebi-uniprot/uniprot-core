package uk.ac.ebi.uniprot.domain.taxonomy;

import java.util.List;

public interface TaxonNode {
    TaxonNode getParent();

    List<Taxon> getTaxonLineage();

    Taxon getTaxon();

    TaxonomyRank getRank();
}
