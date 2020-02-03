package org.uniprot.core.literature;

import java.io.Serializable;

import org.uniprot.core.citation.Citation;
import org.uniprot.core.util.Utils;

/** @author lgonzales */
public interface LiteratureEntry extends Serializable {

    Citation getCitation();

    LiteratureStatistics getStatistics();

    default boolean hasCitation() {
        return Utils.notNull(getCitation());
    }

    default boolean hasStatistics() {
        return Utils.notNull(getStatistics());
    }
}
