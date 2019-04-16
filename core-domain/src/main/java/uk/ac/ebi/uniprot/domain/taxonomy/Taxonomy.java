package uk.ac.ebi.uniprot.domain.taxonomy;

import java.io.Serializable;

public interface Taxonomy extends OrganismName, Serializable {
    long getTaxonId();
    String getMnemonic();
}
