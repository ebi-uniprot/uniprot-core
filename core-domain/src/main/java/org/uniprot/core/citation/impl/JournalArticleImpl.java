package org.uniprot.core.citation.impl;

import static java.util.Collections.emptyList;

import java.util.List;
import java.util.Objects;

import org.uniprot.core.DBCrossReference;
import org.uniprot.core.citation.*;
import org.uniprot.core.util.Utils;

public class JournalArticleImpl extends AbstractCitationImpl implements JournalArticle {
    private static final long serialVersionUID = -1925700851366460680L;
    private Journal journal;
    private String firstPage;
    private String lastPage;
    private String volume;

    // no arg constructor for JSON deserialization
    JournalArticleImpl() {
        this(emptyList(), emptyList(), emptyList(), null, null, null, null, null, null);
    }

    public JournalArticleImpl(
            List<String> authoringGroup,
            List<Author> authors,
            List<DBCrossReference<CitationXrefType>> citationXrefs,
            String title,
            PublicationDate publicationDate,
            String journalName,
            String firstPage,
            String lastPage,
            String volume) {
        super(
                CitationType.JOURNAL_ARTICLE,
                authoringGroup,
                authors,
                citationXrefs,
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
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((firstPage == null) ? 0 : firstPage.hashCode());
        result = prime * result + ((journal == null) ? 0 : journal.hashCode());
        result = prime * result + ((lastPage == null) ? 0 : lastPage.hashCode());
        result = prime * result + ((volume == null) ? 0 : volume.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        JournalArticleImpl other = (JournalArticleImpl) obj;
        return Objects.equals(this.journal, other.journal)
                && Objects.equals(this.lastPage, other.lastPage)
                && Objects.equals(this.volume, other.volume)
                && Objects.equals(this.firstPage, other.firstPage);
    }
}
