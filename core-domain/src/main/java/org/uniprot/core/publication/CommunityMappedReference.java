package org.uniprot.core.publication;

import org.uniprot.core.util.Utils;

public interface CommunityMappedReference extends MappedReference {
    CommunityAnnotation getCommunityAnnotation();

    default boolean hasCommunityAnnotation() {
        return Utils.notNull(getCommunityAnnotation());
    }
}
