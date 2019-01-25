package uk.ac.ebi.uniprot.domain.taxonomy;


import uk.ac.ebi.uniprot.domain.uniprot.evidence2.HasEvidences;

import java.io.Serializable;
import java.util.List;

public interface Organism extends OrganismName, HasEvidences, Serializable {

    long getTaxonId();

    List<String> getLineage();

}
