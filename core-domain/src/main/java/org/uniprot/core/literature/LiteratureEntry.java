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

    List<String> getAuthoringGroup();

    Journal getJournal();

    PublicationDate getPublicationDate();

    String getFirstPage();

    String getLastPage();

    String getVolume();

    String getLiteratureAbstract();

    List<LiteratureMappedReference> getLiteratureMappedReferences();

    LiteratureStatistics getStatistics();

    default boolean hasPubmedId() {
        return Utils.notNull(getPubmedId()) && getPubmedId() > 0;
    }

    default boolean hasDoiId() {
        return Utils.notNullOrEmpty(getDoiId());
    }

    default boolean hasTitle() {
        return Utils.notNullOrEmpty(getTitle());
    }

    default boolean hasAuthoringGroup() {
        return Utils.notNullOrEmpty(getAuthoringGroup());
    }

    default boolean hasAuthors() {
        return Utils.notNullOrEmpty(getAuthors());
    }

    default boolean hasPublicationDate() {
        return Utils.notNull(getPublicationDate());
    }

    default boolean hasJournal() {
        return Utils.notNull(getJournal());
    }

    default boolean hasFirstPage() {
        return Utils.notNullOrEmpty(getFirstPage());
    }

    default boolean hasLastPage() {
        return Utils.notNullOrEmpty(getLastPage());
    }

    default boolean hasVolume() {
        return Utils.notNullOrEmpty(getVolume());
    }

    default boolean hasLiteratureAbstract() {
        return Utils.notNullOrEmpty(getLiteratureAbstract());
    }

    default boolean hasLiteratureMappedReferences() {
        return Utils.notNullOrEmpty(getLiteratureMappedReferences());
    }

    default boolean hasStatistics() {
        return Utils.notNull(getStatistics());
    }
}
