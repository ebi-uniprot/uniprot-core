package org.uniprot.core.citation.builder;

import org.uniprot.core.citation.JournalArticle;
import org.uniprot.core.citation.impl.JournalArticleImpl;

public final class JournalArticleBuilder
        extends AbstractCitationBuilder<JournalArticleBuilder, JournalArticle> {
    private String journalName;
    private String firstPage = "";
    private String lastPage = "";
    private String volume = "";

    @Override
    public JournalArticle build() {
        return new JournalArticleImpl(
                authoringGroups,
                authors,
                xrefs,
                title,
                publicationDate,
                journalName,
                firstPage,
                lastPage,
                volume);
    }

    @Override
    public JournalArticleBuilder from(JournalArticle instance) {
        init(instance);
        return this.journalName(instance.getJournal().getName())
                .firstPage(instance.getFirstPage())
                .lastPage(instance.getLastPage())
                .volume(instance.getVolume());
    }

    public JournalArticleBuilder journalName(String journalName) {
        this.journalName = journalName;
        return this;
    }

    public JournalArticleBuilder firstPage(String firstPage) {
        this.firstPage = firstPage;
        return this;
    }

    public JournalArticleBuilder lastPage(String lastPage) {
        this.lastPage = lastPage;
        return this;
    }

    public JournalArticleBuilder volume(String volume) {
        this.volume = volume;
        return this;
    }

    @Override
    protected JournalArticleBuilder getThis() {
        return this;
    }
}
