package org.uniprot.core.literature;

import java.io.Serializable;
import java.util.List;

import org.uniprot.core.citation.Author;
import org.uniprot.core.citation.Journal;
import org.uniprot.core.citation.PublicationDate;
import org.uniprot.core.util.Utils;

/**
 * @author lgonzales
 */
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
        return Utils.nonNull(getPubmedId()) && getPubmedId() > 0;
    }

    default boolean hasDoiId() {
        return Utils.notEmpty(getDoiId());
    }

    default boolean hasTitle() {
        return Utils.notEmpty(getTitle());
    }

    default boolean hasAuthoringGroup() {
        return Utils.notEmpty(getAuthoringGroup());
    }

    default boolean hasAuthors() {
        return Utils.notEmpty(getAuthors());
    }

    default boolean hasPublicationDate() {
        return Utils.nonNull(getPublicationDate());
    }

    default boolean hasJournal() {
        return Utils.nonNull(getJournal());
    }

    default boolean hasFirstPage() {
        return Utils.notEmpty(getFirstPage());
    }

    default boolean hasLastPage() {
        return Utils.notEmpty(getLastPage());
    }

    default boolean hasVolume() {
        return Utils.notEmpty(getVolume());
    }

    default boolean hasLiteratureAbstract() {
        return Utils.notEmpty(getLiteratureAbstract());
    }

    default boolean hasLiteratureMappedReferences() {
        return Utils.notEmpty(getLiteratureMappedReferences());
    }

    default boolean hasStatistics() {
        return Utils.nonNull(getStatistics());
    }

}
