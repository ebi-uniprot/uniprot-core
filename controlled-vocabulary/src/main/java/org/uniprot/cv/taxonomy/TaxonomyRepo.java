package org.uniprot.cv.taxonomy;

import java.util.Optional;

/** Contains a set of methods to query a taxonomy repository */
public interface TaxonomyRepo {
    /**
     * Provides a node with taxonomic data pertaining to the provided taxon ID.
     *
     * @param taxonId An NCBI taxonomic identifier
     * @return taxonomic data for the given taxon id
     * @throws TaxonMappingException is thrown if an error occurs whilst retrieving the taxonomic
     *     node
     */
    Optional<TaxonomicNode> retrieveNodeUsingTaxID(int taxonId);
}
