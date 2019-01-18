package uk.ac.ebi.uniprot.domain.citation.builder;

import uk.ac.ebi.uniprot.domain.citation.ElectronicArticle;
import uk.ac.ebi.uniprot.domain.citation.Locator;
import uk.ac.ebi.uniprot.domain.citation.impl.ElectronicArticleImpl;
import uk.ac.ebi.uniprot.domain.citation.impl.ElectronicArticleImpl.LocatorImpl;


public final class ElectronicArticleBuilder extends AbstractCitationBuilder<ElectronicArticleBuilder, ElectronicArticle> {
    private String journalName;
    private Locator locator;

    public static ElectronicArticleBuilder newInstance() {
        return new ElectronicArticleBuilder();
    }

    public static Locator createLocator(String locator) {
        return new LocatorImpl(locator);
    }

    public ElectronicArticle build() {
        return new ElectronicArticleImpl(authoringGroups, authors,
                                         xrefs, title, publicationDate,
                                         journalName, locator);
    }

    public ElectronicArticleBuilder journalName(String journalName) {
        this.journalName = journalName;
        return this;
    }

    public ElectronicArticleBuilder locator(String locator) {
        this.locator = createLocator(locator);
        return this;
    }

	@Override
	protected ElectronicArticleBuilder getThis() {
		return this;
	}


}
