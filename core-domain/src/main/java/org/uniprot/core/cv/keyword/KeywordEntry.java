package org.uniprot.core.cv.keyword;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import org.uniprot.core.Statistics;

public interface KeywordEntry extends Serializable {
    Keyword getKeyword();

    String getDefinition();

    List<String> getSynonyms();

    List<GeneOntology> getGeneOntologies();

    Set<KeywordEntry> getParents();

    List<String> getSites();

    Keyword getCategory();

    List<KeywordEntry> getChildren();

    String getAccession();

    Statistics getStatistics();
}
