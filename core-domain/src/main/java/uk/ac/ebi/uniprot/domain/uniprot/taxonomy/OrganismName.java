package uk.ac.ebi.uniprot.domain.uniprot.taxonomy;

import java.io.Serializable;
import java.util.List;

public interface OrganismName extends Serializable {

    String getScientificName();

    String getCommonName();

    List<String> getSynonyms();

    boolean hasScientificName();

    boolean hasCommonName();

    boolean hasSynonyms();
}
