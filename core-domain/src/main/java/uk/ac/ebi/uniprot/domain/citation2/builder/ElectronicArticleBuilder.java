package uk.ac.ebi.uniprot.domain.citation2.builder;

import uk.ac.ebi.uniprot.domain.citation2.ElectronicArticle;
import uk.ac.ebi.uniprot.domain.citation2.impl.ElectronicArticleImpl;


public final class ElectronicArticleBuilder extends AbstractCitationBuilder<ElectronicArticleBuilder, ElectronicArticle> {
    private String journalName;
    private String locator;

    public ElectronicArticle build() {
        return new ElectronicArticleImpl(this);
    }

    @Override
    public ElectronicArticleBuilder from(ElectronicArticle instance) {
        ElectronicArticleBuilder builder = new ElectronicArticleBuilder();
        init(builder, instance);
        return builder
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
}
