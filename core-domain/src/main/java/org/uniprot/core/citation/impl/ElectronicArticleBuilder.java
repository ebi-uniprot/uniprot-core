package org.uniprot.core.citation.impl;

import org.uniprot.core.citation.ElectronicArticle;

import javax.annotation.Nonnull;

public final class ElectronicArticleBuilder
        extends AbstractCitationBuilder<ElectronicArticleBuilder, ElectronicArticle> {
    private String journalName;
    private String locator;

    public @Nonnull ElectronicArticle build() {
        return new ElectronicArticleImpl(
                authoringGroups,
                authors,
                citationCrossReferences,
                title,
                publicationDate,
                journalName,
                new ElectronicArticleImpl.LocatorImpl(locator));
    }

    public static @Nonnull ElectronicArticleBuilder from(@Nonnull ElectronicArticle instance) {
        ElectronicArticleBuilder builder = new ElectronicArticleBuilder();
        init(builder, instance);
        return builder.journalName(instance.getJournal().getName())
                .locator(instance.getLocator().getValue());
    }

    public @Nonnull ElectronicArticleBuilder journalName(String journalName) {
        this.journalName = journalName;
        return this;
    }

    public @Nonnull ElectronicArticleBuilder locator(String locator) {
        this.locator = locator;
        return this;
    }

    @Override
    protected @Nonnull ElectronicArticleBuilder getThis() {
        return this;
    }
}
