package org.uniprot.core.citation.impl;

import static java.util.Collections.emptyList;

import java.util.List;
import java.util.Objects;

import org.uniprot.core.CrossReference;
import org.uniprot.core.citation.*;
import org.uniprot.core.util.Utils;

public class ElectronicArticleImpl extends AbstractCitationImpl implements ElectronicArticle {
    private static final long serialVersionUID = -8389378286532442748L;
    private Journal journal;
    private Locator locator;

    // no arg constructor for JSON deserialization
    ElectronicArticleImpl() {
        this(emptyList(), emptyList(), emptyList(), null, null, null, null);
    }

    public ElectronicArticleImpl(
            List<String> authoringGroup,
            List<Author> authors,
            List<CrossReference<CitationDatabase>> citationXrefs,
            String title,
            PublicationDate publicationDate,
            String journalName,
            Locator locator) {
        super(
                CitationType.ELECTRONIC_ARTICLE,
                authoringGroup,
                authors,
                citationXrefs,
                title,
                publicationDate);
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
        return this.journal != null;
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
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        ElectronicArticleImpl other = (ElectronicArticleImpl) obj;
        return Objects.equals(this.journal, other.journal)
                && Objects.equals(this.locator, other.locator);
    }

    public static class LocatorImpl implements Locator {
        private String value;

        // no arg constructor for JSON deserialization
        LocatorImpl() {}

        public LocatorImpl(String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return value;
        }

        @Override
        public boolean hasValue() {
            return Utils.notNullNotEmpty(this.value);
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
            if (this == obj) return true;
            if (obj == null) return false;
            if (getClass() != obj.getClass()) return false;
            LocatorImpl other = (LocatorImpl) obj;
            return Objects.equals(this.value, other.value);
        }
    }
}
