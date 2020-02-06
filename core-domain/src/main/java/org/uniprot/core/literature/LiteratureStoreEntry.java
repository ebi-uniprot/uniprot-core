package org.uniprot.core.literature;

import java.io.Serializable;
import java.util.List;

import org.uniprot.core.util.Utils;

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
