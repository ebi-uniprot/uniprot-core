package uk.ac.ebi.uniprot.domain.citation.impl;

import uk.ac.ebi.uniprot.common.Utils;
import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.citation.*;

import java.util.List;

import static java.util.Collections.emptyList;

public class ElectronicArticleImpl extends AbstractCitationImpl implements ElectronicArticle {
    private Journal journal;
    private Locator locator;

    private ElectronicArticleImpl() {
        this(emptyList(), emptyList(), emptyList(), null, null, null, null);
    }

    public ElectronicArticleImpl(List<String> authoringGroup, List<Author> authors, List<DBCrossReference<CitationXrefType>> citationXrefs,
                                 String title, PublicationDate publicationDate, String journalName, Locator locator) {
        super(CitationType.ELECTRONIC_ARTICLE, authoringGroup, authors, citationXrefs, title, publicationDate);
        if (journalName != null) {
            this.journal = new JournalImpl(journalName);
        }
        this.locator = locator;
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
    public boolean hasLocator() {
        return this.locator != null;
    }

    @Override
    public boolean hasJournal() {
        return this.journal != null ;
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

        }

        public LocatorImpl(String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return value;
        }

        @Override
        public boolean hasValue() {
            return Utils.notEmpty(this.value);
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
