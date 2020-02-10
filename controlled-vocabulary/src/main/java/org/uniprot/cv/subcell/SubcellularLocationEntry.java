package org.uniprot.cv.subcell;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.uniprot.core.cv.keyword.Keyword;
import org.uniprot.cv.keyword.GeneOntology;

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
