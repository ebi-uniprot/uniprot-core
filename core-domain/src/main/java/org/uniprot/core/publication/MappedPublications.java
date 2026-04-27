package org.uniprot.core.publication;

import java.io.Serializable;
import java.util.List;

/**
 * @author sahmad
 * @created 18/12/2020
 */
public interface MappedPublications extends Serializable {
    List<UniProtKBMappedReference> getUniProtKBMappedReferences();

    List<ComputationallyMappedReference> getComputationallyMappedReferences();

    List<CommunityMappedReference> getCommunityMappedReferences();
}
