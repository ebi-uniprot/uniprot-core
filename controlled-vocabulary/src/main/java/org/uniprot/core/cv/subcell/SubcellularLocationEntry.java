package org.uniprot.core.cv.subcell;

import org.uniprot.core.cv.keyword.GeneOntology;
import org.uniprot.core.cv.keyword.Keyword;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface SubcellularLocationEntry extends Serializable {
    SubcellLocationCategory getCategory();

    String getId();

    String getAccession();

    String getDefinition();

    String getContent();

    List<String> getSynonyms();

    Optional<Keyword> getKeyword();

    List<GeneOntology> getGeneOntologies();

    String getNote();

    List<String> getReferences();

    List<String> getLinks();

    List<SubcellularLocationEntry> getIsA();

    List<SubcellularLocationEntry> getPartOf();

    SubcellularLocationStatistics getStatistics();
}
