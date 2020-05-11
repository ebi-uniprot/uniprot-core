package org.uniprot.core.cv.pathway;

import java.util.List;

import org.uniprot.core.cv.disease.DiseaseCrossReference;

public interface PathwayEntry {

    /**
     * Uniprot accession as an id to identify pathway uniquely
     *
     * @return accession
     */
    String getId();

    /**
     * Unique can be use as an identifier
     *
     * @return Unique name of pathway e-g protein sumoylation
     */
    String getName();

    String getDefinition();

    String getPathwayClass();

    List<String> getSynonyms();

    List<PathwayEntry> getIsAParents();

    List<PathwayEntry> getPartOfParents();

    List<DiseaseCrossReference> getCrossReferences();
}
