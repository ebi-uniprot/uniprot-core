package org.uniprot.core.cv.subcell;

import org.uniprot.core.Statistics;
import org.uniprot.core.cv.go.GoTerm;
import org.uniprot.core.cv.keyword.KeywordId;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface SubcellularLocationEntry extends Serializable {
    SubcellLocationCategory getCategory();

    /**
     * Unique can be use as an identifier
     *
     * @return Unique name of subcellular location
     */
    String getName();

    /**
     * Uniprot accession as an id to identify CrossRef uniquely
     *
     * @return accession
     */
    String getId();

    String getDefinition();

    String getContent();

    List<String> getSynonyms();

    Optional<KeywordId> getKeyword();

    List<GoTerm> getGeneOntologies();

    String getNote();

    List<String> getReferences();

    List<String> getLinks();

    List<SubcellularLocationEntry> getIsA();

    List<SubcellularLocationEntry> getPartOf();

    Statistics getStatistics();
}
