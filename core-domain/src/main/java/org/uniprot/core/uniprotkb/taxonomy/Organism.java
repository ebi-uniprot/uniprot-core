package org.uniprot.core.uniprotkb.taxonomy;

import java.io.Serializable;
import java.util.List;

import org.uniprot.core.uniprotkb.evidence.HasEvidences;

public interface Organism extends OrganismName, HasEvidences, Serializable {

    long getTaxonId();

    List<String> getLineages();
}
