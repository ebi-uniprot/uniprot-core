package org.uniprot.core.literature;

import org.uniprot.core.citation.Citation;
import org.uniprot.core.util.Utils;

import java.io.Serializable;

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
