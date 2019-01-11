package uk.ac.ebi.uniprot.domain.citation2.builder;

import uk.ac.ebi.uniprot.domain.citation2.JournalArticle;
import uk.ac.ebi.uniprot.domain.citation2.impl.JournalArticleImpl;

public final class JournalArticleBuilder extends AbstractCitationBuilder<JournalArticleBuilder, JournalArticle> {
    private String journalName;
    private String firstPage = "";
    private String lastPage = "";
    private String volume = "";

    @Override
    public JournalArticle build() {
        return new JournalArticleImpl(this);
    }

    @Override
    public JournalArticleBuilder from(JournalArticle instance) {
        JournalArticleBuilder builder = new JournalArticleBuilder();
        init(builder, instance);
        return builder
                .journalName(instance.getJournal().getName())
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

    public String getJournalName() {
        return journalName;
    }

    public String getFirstPage() {
        return firstPage;
    }

    public String getLastPage() {
        return lastPage;
    }

    public String getVolume() {
        return volume;
    }
}
