package uk.ac.ebi.uniprot.domain.literature;

import uk.ac.ebi.uniprot.common.Utils;
import uk.ac.ebi.uniprot.domain.citation.Author;
import uk.ac.ebi.uniprot.domain.citation.Journal;
import uk.ac.ebi.uniprot.domain.citation.PublicationDate;

import java.io.Serializable;
import java.util.List;

/**
 * @author lgonzales
 */
public interface LiteratureEntry extends Serializable {

    String getPubmedId();

    String getDoiId();

    String getTitle();

    List<Author> getAuthors();

    List<String> getAuthoringGroup();

    Journal getJournal();

    PublicationDate getPublicationDate();

    String getFirstPage();

    String getLastPage();

    String getVolume();

    String getLiteratureAbstract();

    LiteratureStatistics getStatistics();

    default boolean hasPubmedId() {
        return Utils.notEmpty(getPubmedId());
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

    default boolean hasStatistics() {
        return Utils.nonNull(getStatistics());
    }

}
