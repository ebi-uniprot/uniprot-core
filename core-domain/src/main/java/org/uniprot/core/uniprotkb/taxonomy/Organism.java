package org.uniprot.core.uniprotkb.taxonomy;

import org.uniprot.core.uniprotkb.evidence.HasEvidences;

import java.io.Serializable;
import java.util.List;

public interface Organism extends OrganismName, HasEvidences, Serializable {

    long getTaxonId();

    List<String> getLineages();
}
