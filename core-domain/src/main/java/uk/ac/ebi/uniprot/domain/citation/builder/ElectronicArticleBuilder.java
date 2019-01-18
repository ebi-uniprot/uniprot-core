package uk.ac.ebi.uniprot.domain.citation.builder;

import uk.ac.ebi.uniprot.domain.citation.ElectronicArticle;
import uk.ac.ebi.uniprot.domain.citation.impl.ElectronicArticleImpl;


public final class ElectronicArticleBuilder extends AbstractCitationBuilder<ElectronicArticleBuilder, ElectronicArticle> {
    private String journalName;
    private String locator;

    public ElectronicArticle build() {
        return new ElectronicArticleImpl(this);
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

    public String getJournalName() {
        return journalName;
    }

    public String getLocator() {
        return locator;
    }

    @Override
    protected ElectronicArticleBuilder getThis() {
        return this;
    }
}
