package org.uniprot.core.cv.pathway;

import java.util.List;

import org.uniprot.core.cv.disease.DiseaseCrossReference;

public interface PathwayEntry {
    String getAccession();

    String getId();

    String getDefinition();

    String getPathwayClass();

    List<String> getSynonyms();

    List<PathwayEntry> getIsAParents();

    List<PathwayEntry> getPartOfParents();

    List<DiseaseCrossReference> getCrossReferences();
}
