package uk.ac.ebi.uniprot.domain.uniprot.taxonomy;

import java.io.Serializable;

/**
 *
 * @author lgonzales
 */
public interface OrganismHost extends OrganismName, Serializable {

    long getTaxonId();

}
