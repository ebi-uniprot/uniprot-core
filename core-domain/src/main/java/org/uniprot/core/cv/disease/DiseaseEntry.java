package org.uniprot.core.cv.disease;

import java.io.Serializable;
import java.util.List;

import org.uniprot.core.cv.keyword.KeywordId;

public interface DiseaseEntry extends Serializable {
    String getId();

    String getAccession();

    String getAcronym();

    String getDefinition();

    List<String> getAlternativeNames();

    List<DiseaseCrossReference> getCrossReferences();

    List<KeywordId> getKeywords();

    Long getReviewedProteinCount();

    Long getUnreviewedProteinCount();
}
