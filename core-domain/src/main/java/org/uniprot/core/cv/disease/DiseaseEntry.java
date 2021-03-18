package org.uniprot.core.cv.disease;

import java.io.Serializable;
import java.util.List;

import org.uniprot.core.Statistics;
import org.uniprot.core.cv.keyword.KeywordId;

public interface DiseaseEntry extends Serializable {
    /**
     * Unique can be use as an identifier
     *
     * @return Unique name of disease e-g 3M syndrome 1
     */
    String getName();

    /**
     * Uniprot accession as an id to identify disease uniquely
     *
     * @return Uniprot accession e-g DI-00011
     */
    String getId();

    String getAcronym();

    String getDefinition();

    List<String> getAlternativeNames();

    List<DiseaseCrossReference> getCrossReferences();

    List<KeywordId> getKeywords();

    Statistics getStatistics();
}
