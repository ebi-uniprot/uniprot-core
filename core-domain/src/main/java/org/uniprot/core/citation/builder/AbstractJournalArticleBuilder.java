package org.uniprot.core.citation.builder;

import javax.annotation.Nonnull;

import org.uniprot.core.citation.JournalArticle;

/**
 * @author lgonzales
 * @since 2020-01-28
 */
public abstract class AbstractJournalArticleBuilder<
                B extends AbstractJournalArticleBuilder<B, T>, T extends JournalArticle>
        extends AbstractCitationBuilder<B, T> {

    protected String journalName;
    protected String firstPage = "";
    protected String lastPage = "";
    protected String volume = "";

    public @Nonnull B journalName(String journalName) {
        this.journalName = journalName;
        return getThis();
    }

    public @Nonnull B firstPage(String firstPage) {
        this.firstPage = firstPage;
        return getThis();
    }

    public @Nonnull B lastPage(String lastPage) {
        this.lastPage = lastPage;
        return getThis();
    }

    public @Nonnull B volume(String volume) {
        this.volume = volume;
        return getThis();
    }

    protected static <B extends AbstractJournalArticleBuilder<B, T>, T extends JournalArticle>
            void init(@Nonnull B builder, @Nonnull T instance) {
        AbstractCitationBuilder.init(builder, instance);
        if (instance.hasJournal()) {
            builder.journalName(instance.getJournal().getName());
        }
        builder.firstPage(instance.getFirstPage());
        builder.lastPage(instance.getLastPage());
        builder.volume(instance.getVolume());
    }
}
