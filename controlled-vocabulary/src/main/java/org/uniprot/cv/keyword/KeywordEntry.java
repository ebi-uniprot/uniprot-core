package org.uniprot.cv.keyword;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import org.uniprot.core.cv.keyword.Keyword;

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

    KeywordStatistics getStatistics();
}
