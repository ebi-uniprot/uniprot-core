package org.uniprot.core.citation.builder;

import javax.annotation.Nonnull;

import org.uniprot.core.citation.JournalArticle;
import org.uniprot.core.citation.impl.JournalArticleImpl;

public final class JournalArticleBuilder
        extends AbstractJournalArticleBuilder<JournalArticleBuilder, JournalArticle> {

    @Override
    public @Nonnull JournalArticle build() {
        return new JournalArticleImpl(
                authoringGroups,
                authors,
                citationCrossReferences,
                title,
                publicationDate,
                journalName,
                firstPage,
                lastPage,
                volume);
    }

    public static @Nonnull JournalArticleBuilder from(@Nonnull JournalArticle instance) {
        JournalArticleBuilder builder = new JournalArticleBuilder();
        init(builder, instance);
        return builder;
    }

    @Override
    protected @Nonnull JournalArticleBuilder getThis() {
        return this;
    }
}
