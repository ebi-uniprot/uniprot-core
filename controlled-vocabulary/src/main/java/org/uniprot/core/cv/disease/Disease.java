package org.uniprot.core.cv.disease;

import org.uniprot.core.cv.keyword.Keyword;

import java.io.Serializable;
import java.util.List;

public interface Disease extends Serializable {
    String getId();

    String getAccession();

    String getAcronym();

    String getDefinition();

    List<String> getAlternativeNames();

    List<CrossReference> getCrossReferences();

    List<Keyword> getKeywords();

    Long getReviewedProteinCount();

    Long getUnreviewedProteinCount();
}
