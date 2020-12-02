package org.uniprot.core.publication;

import org.uniprot.core.util.Utils;

public interface CommunityMappedReference extends PublicationMappedReference {
    CommunityAnnotation getCommunityAnnotation();

    default boolean hasCommunityAnnotation() {
        return Utils.notNull(getCommunityAnnotation());
    }
}
