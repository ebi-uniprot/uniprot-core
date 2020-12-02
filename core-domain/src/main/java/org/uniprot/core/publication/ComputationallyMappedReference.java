package org.uniprot.core.publication;

import org.uniprot.core.util.Utils;

public interface ComputationallyMappedReference extends PublicationMappedReference {
    String getAnnotation();

    default boolean hasAnnotation() {
        return Utils.notNullNotEmpty(getAnnotation());
    }
}
