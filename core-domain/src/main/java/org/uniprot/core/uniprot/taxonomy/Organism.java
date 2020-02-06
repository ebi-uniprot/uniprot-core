package org.uniprot.core.uniprot.taxonomy;

import java.io.Serializable;
import java.util.List;

import org.uniprot.core.uniprot.evidence.HasEvidences;

public interface Organism extends OrganismName, HasEvidences, Serializable {

    long getTaxonId();

    List<String> getLineages();
}
