package uk.ac.ebi.uniprot.domain.taxonomy;

import java.io.Serializable;

/**
 *
 * @author lgonzales
 */
public interface OrganismHost extends OrganismName, Serializable {

    long getTaxonId();

}
