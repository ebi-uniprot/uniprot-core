package uk.ac.ebi.uniprot.domain.taxonomy;

import java.util.List;

public interface Taxon   {
    TaxonId getTaxonId();
      String getScientificName();
      String getCommonName();
      List<String> getSynonyms();
}
