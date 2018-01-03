package uk.ac.ebi.uniprot.domain.citation.builder;

import uk.ac.ebi.uniprot.domain.citation.Author;
import uk.ac.ebi.uniprot.domain.citation.CitationType;
import uk.ac.ebi.uniprot.domain.citation.CitationXrefs;
import uk.ac.ebi.uniprot.domain.citation.ElectronicArticle;
import uk.ac.ebi.uniprot.domain.citation.Locator;
import uk.ac.ebi.uniprot.domain.citation.PublicationDate;

import java.util.List;

public final class ElectronicArticleBuilder extends AbstractCitationBuilder<ElectronicArticle> {
    public static ElectronicArticleBuilder newInstance() {
        return new ElectronicArticleBuilder();
    }

    private String journalName;
    private Locator locator;

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

    class ElectronicArticleImpl extends AbstractCitationImpl implements ElectronicArticle {
        private final String journalName;
        private final Locator locator;

        ElectronicArticleImpl(List<String> authoringGroups, List<Author> authors, CitationXrefs xrefs,
            String title, PublicationDate publicationDate,
            String journalName, Locator locator) {
            super(CitationType.ELECTRONIC_ARTICLE, authoringGroups, authors, xrefs, title, publicationDate);
            this.journalName = journalName;
            this.locator = locator;
        }

        @Override
        public Locator getLocator() {
            return locator;
        }

        @Override
        public String getJournalName() {
            return journalName;
        }

    }

    static class LocatorImpl implements Locator {
        private final String locator;

        public LocatorImpl(String locator) {
            this.locator = locator;
        }

        @Override
        public String getValue() {
            return locator;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;

            result = prime * result + ((locator == null) ? 0 : locator.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            LocatorImpl other = (LocatorImpl) obj;
            if (locator == null) {
                if (other.locator != null)
                    return false;
            } else if (!locator.equals(other.locator))
                return false;
            return true;
        }

    }
}
