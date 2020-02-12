package org.uniprot.core.cv.disease;

import java.io.Serializable;
import java.util.List;

import org.uniprot.core.cv.keyword.Keyword;

public interface DiseaseEntry extends Serializable {
    String getId();

    String getAccession();

    String getAcronym();

    String getDefinition();

    List<String> getAlternativeNames();

    List<DiseaseCrossReference> getCrossReferences();

    List<Keyword> getKeywords();

    Long getReviewedProteinCount();

    Long getUnreviewedProteinCount();
}
