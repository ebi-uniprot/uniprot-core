package org.uniprot.core.literature;

import org.uniprot.core.util.Utils;

import java.io.Serializable;
import java.util.List;

/**
 * @author lgonzales
 * @since 2019-12-05
 */
public interface LiteratureStoreEntry extends Serializable {

    LiteratureEntry getLiteratureEntry();

    List<LiteratureMappedReference> getLiteratureMappedReferences();

    default boolean hasLiteratureMappedReferences() {
        return Utils.notNullNotEmpty(getLiteratureMappedReferences());
    }

    default boolean hasLiteratureEntry() {
        return getLiteratureEntry() != null;
    }
}
