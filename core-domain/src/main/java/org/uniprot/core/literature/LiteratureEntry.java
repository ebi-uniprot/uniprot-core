package org.uniprot.core.literature;

import java.io.Serializable;
import java.util.List;

import org.uniprot.core.citation.Author;
import org.uniprot.core.citation.Journal;
import org.uniprot.core.citation.PublicationDate;
import org.uniprot.core.util.Utils;

/** @author lgonzales */
public interface LiteratureEntry extends Serializable {

    Long getPubmedId();

    String getDoiId();

    String getTitle();

    List<Author> getAuthors();

    boolean isCompleteAuthorList();

    List<String> getAuthoringGroups();

    Journal getJournal();

    PublicationDate getPublicationDate();

    String getFirstPage();

    String getLastPage();

    String getVolume();

    String getLiteratureAbstract();

    LiteratureStatistics getStatistics();

    default boolean hasPubmedId() {
        return Utils.notNull(getPubmedId()) && getPubmedId() > 0;
    }

    default boolean hasDoiId() {
        return Utils.notNullNotEmpty(getDoiId());
    }

    default boolean hasTitle() {
        return Utils.notNullNotEmpty(getTitle());
    }

    default boolean hasAuthoringGroup() {
        return Utils.notNullNotEmpty(getAuthoringGroups());
    }

    default boolean hasAuthors() {
        return Utils.notNullNotEmpty(getAuthors());
    }

    default boolean hasPublicationDate() {
        return Utils.notNull(getPublicationDate());
    }

    default boolean hasJournal() {
        return Utils.notNull(getJournal());
    }

    default boolean hasFirstPage() {
        return Utils.notNullNotEmpty(getFirstPage());
    }

    default boolean hasLastPage() {
        return Utils.notNullNotEmpty(getLastPage());
    }

    default boolean hasVolume() {
        return Utils.notNullNotEmpty(getVolume());
    }

    default boolean hasLiteratureAbstract() {
        return Utils.notNullNotEmpty(getLiteratureAbstract());
    }

    default boolean hasStatistics() {
        return Utils.notNull(getStatistics());
    }
}
