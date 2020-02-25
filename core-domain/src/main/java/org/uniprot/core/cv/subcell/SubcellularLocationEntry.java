package org.uniprot.core.cv.subcell;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.uniprot.core.Statistics;
import org.uniprot.core.cv.keyword.KeywordGeneOntology;
import org.uniprot.core.cv.keyword.KeywordId;

public interface SubcellularLocationEntry extends Serializable {
    SubcellLocationCategory getCategory();

    String getId();

    String getAccession();

    String getDefinition();

    String getContent();

    List<String> getSynonyms();

    Optional<KeywordId> getKeyword();

    List<KeywordGeneOntology> getGeneOntologies();

    String getNote();

    List<String> getReferences();

    List<String> getLinks();

    List<SubcellularLocationEntry> getIsA();

    List<SubcellularLocationEntry> getPartOf();

    Statistics getStatistics();
}
