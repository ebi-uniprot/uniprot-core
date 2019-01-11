package uk.ac.ebi.uniprot.domain.citation2.impl;

import uk.ac.ebi.uniprot.domain.citation2.CitationType;
import uk.ac.ebi.uniprot.domain.citation2.ElectronicArticle;
import uk.ac.ebi.uniprot.domain.citation2.Journal;
import uk.ac.ebi.uniprot.domain.citation2.Locator;
import uk.ac.ebi.uniprot.domain.citation2.builder.ElectronicArticleBuilder;

import java.util.Collections;

public class ElectronicArticleImpl extends AbstractCitationImpl implements ElectronicArticle {
    private Journal journal;
    private Locator locator;

    private ElectronicArticleImpl() {
        super(CitationType.ELECTRONIC_ARTICLE, Collections.emptyList(), Collections.emptyList(), null, "", null);
    }

    public ElectronicArticleImpl(ElectronicArticleBuilder builder) {
        super(CitationType.ELECTRONIC_ARTICLE, builder.getAuthoringGroups(), builder.getAuthors(), builder.getXrefs(),
              builder.getTitle(), builder.getPublicationDate());
        this.journal = new JournalImpl(builder.getJournalName());
        this.locator = new LocatorImpl(builder.getLocator());
    }

    @Override
    public Locator getLocator() {
        return locator;
    }

    @Override
    public Journal getJournal() {
        return journal;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((journal == null) ? 0 : journal.hashCode());
        result = prime * result + ((locator == null) ? 0 : locator.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        ElectronicArticleImpl other = (ElectronicArticleImpl) obj;
        if (journal == null) {
            if (other.journal != null)
                return false;
        } else if (!journal.equals(other.journal))
            return false;
        if (locator == null) {
            if (other.locator != null)
                return false;
        } else if (!locator.equals(other.locator))
            return false;
        return true;
    }

    public static class LocatorImpl implements Locator {
        private String value;

        private LocatorImpl() {
            this.value = "";
        }

        public LocatorImpl(String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return value;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;

            result = prime * result + ((value == null) ? 0 : value.hashCode());
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
            if (value == null) {
                if (other.value != null)
                    return false;
            } else if (!value.equals(other.value))
                return false;
            return true;
        }

    }

}
