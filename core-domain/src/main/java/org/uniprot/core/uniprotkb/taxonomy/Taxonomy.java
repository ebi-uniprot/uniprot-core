package org.uniprot.core.uniprotkb.taxonomy;

import java.io.Serializable;

public interface Taxonomy extends OrganismName, Serializable {

    long getTaxonId();

    String getMnemonic();

    boolean hasMnemonic();
}
