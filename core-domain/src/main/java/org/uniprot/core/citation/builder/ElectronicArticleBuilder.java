package org.uniprot.core.citation.builder;

import org.uniprot.core.citation.ElectronicArticle;
import org.uniprot.core.citation.impl.ElectronicArticleImpl;


public final class ElectronicArticleBuilder extends AbstractCitationBuilder<ElectronicArticleBuilder, ElectronicArticle> {
    private String journalName;
    private String locator;

    public ElectronicArticle build() {
        return new ElectronicArticleImpl(authoringGroups, authors, xrefs, title,
                                         publicationDate, journalName,
                                         new ElectronicArticleImpl.LocatorImpl(locator));
    }

    @Override
    public ElectronicArticleBuilder from(ElectronicArticle instance) {
        init(instance);
        return this
                .journalName(instance.getJournal().getName())
                .locator(instance.getLocator().getValue());
    }

    public ElectronicArticleBuilder journalName(String journalName) {
        this.journalName = journalName;
        return this;
    }

    public ElectronicArticleBuilder locator(String locator) {
        this.locator = locator;
        return this;
    }

    @Override
    protected ElectronicArticleBuilder getThis() {
        return this;
    }
}
