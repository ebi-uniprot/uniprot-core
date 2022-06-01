package org.uniprot.core.cv.chebi;

import java.io.Serializable;
import java.util.List;

/**
 * Created 05/06/19
 *
 * @author Edd
 */
public interface ChebiEntry extends Serializable {
    String getId();

    String getName();

    String getInchiKey();

    List<ChebiEntry> getRelatedIds();

    List<ChebiEntry> getMajorMicrospecies();

    List<String> getSynonyms();
}
