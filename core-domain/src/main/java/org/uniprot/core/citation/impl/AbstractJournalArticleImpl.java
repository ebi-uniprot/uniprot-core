package org.uniprot.core.citation.impl;

import org.uniprot.core.CrossReference;
import org.uniprot.core.citation.*;
import org.uniprot.core.util.Utils;

import java.util.List;
import java.util.Objects;

/**
 * @author lgonzales
 * @since 2020-01-28
 */
public abstract class AbstractJournalArticleImpl extends AbstractCitationImpl
        implements JournalArticle {

    private static final long serialVersionUID = 567103717651131572L;
    private Journal journal;
    private String firstPage;
    private String lastPage;
    private String volume;

    AbstractJournalArticleImpl(
            CitationType citationType,
            List<String> authoringGroup,
            List<Author> authors,
            List<CrossReference<CitationDatabase>> citationCrossReferences,
            String title,
            PublicationDate publicationDate,
            String journalName,
            String firstPage,
            String lastPage,
            String volume) {
        super(
                citationType,
                authoringGroup,
                authors,
                citationCrossReferences,
                title,
                publicationDate);
        if (journalName != null) {
            this.journal = new JournalImpl(journalName);
        }
        this.firstPage = Utils.emptyOrString(firstPage);
        this.lastPage = Utils.emptyOrString(lastPage);
        this.volume = Utils.emptyOrString(volume);
    }

    @Override
    public Journal getJournal() {
        return journal;
    }

    @Override
    public String getFirstPage() {
        return firstPage;
    }

    @Override
    public String getLastPage() {
        return lastPage;
    }

    @Override
    public String getVolume() {
        return volume;
    }

    @Override
    public boolean hasJournal() {
        return this.journal != null;
    }

    @Override
    public boolean hasFirstPage() {
        return Utils.notNullNotEmpty(this.firstPage);
    }

    @Override
    public boolean hasLastPage() {
        return Utils.notNullNotEmpty(this.lastPage);
    }

    @Override
    public boolean hasVolume() {
        return Utils.notNullNotEmpty(this.volume);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AbstractJournalArticleImpl that = (AbstractJournalArticleImpl) o;
        return Objects.equals(getJournal(), that.getJournal())
                && Objects.equals(getFirstPage(), that.getFirstPage())
                && Objects.equals(getLastPage(), that.getLastPage())
                && Objects.equals(getVolume(), that.getVolume());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                super.hashCode(), getJournal(), getFirstPage(), getLastPage(), getVolume());
    }
}
