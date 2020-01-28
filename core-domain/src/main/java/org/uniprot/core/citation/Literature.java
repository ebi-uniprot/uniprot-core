package org.uniprot.core.citation;

import org.uniprot.core.util.Utils;

/**
 * @author lgonzales
 * @since 2020-01-28
 */
public interface Literature extends JournalArticle {

    boolean isCompleteAuthorList();

    String getLiteratureAbstract();

    default boolean hasLiteratureAbstract() {
        return Utils.notNullOrEmpty(getLiteratureAbstract());
    }
}
