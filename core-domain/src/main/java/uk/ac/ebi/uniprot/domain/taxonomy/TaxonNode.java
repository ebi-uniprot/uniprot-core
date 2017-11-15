package uk.ac.ebi.uniprot.domain.taxonomy;

import java.util.List;

public interface TaxonNode  {
    TaxonNode getParent();
    List<String> getTaxonLineage();
    TaxonomyRank getRank();
    Taxon getTaxon();
}
