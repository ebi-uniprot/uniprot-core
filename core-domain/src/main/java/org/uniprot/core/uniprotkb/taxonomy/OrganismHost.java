package org.uniprot.core.uniprotkb.taxonomy;

import java.io.Serializable;

/** @author lgonzales */
public interface OrganismHost extends OrganismName, Serializable {

    long getTaxonId();
}
