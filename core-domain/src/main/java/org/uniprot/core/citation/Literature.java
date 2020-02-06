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
        return Utils.notNullNotEmpty(getLiteratureAbstract());
    }

    Long getPubmedId();

    String getDoiId();

    default boolean hasPubmedId() {
        return getPubmedId() > 0L;
    }

    default boolean hasDoiId() {
        return Utils.notNullNotEmpty(getDoiId());
    }
}
