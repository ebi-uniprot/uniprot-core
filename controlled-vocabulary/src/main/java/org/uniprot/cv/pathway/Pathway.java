package org.uniprot.cv.pathway;

import java.util.List;

import org.uniprot.core.cv.disease.CrossReference;

public interface Pathway {
    String getAccession();

    String getId();

    String getDefinition();

    String getPathwayClass();

    List<String> getSynonyms();

    List<Pathway> getIsAParents();

    List<Pathway> getPartOfParents();

    List<CrossReference> getCrossReferences();
}
