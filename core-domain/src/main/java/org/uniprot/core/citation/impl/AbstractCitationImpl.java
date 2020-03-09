package org.uniprot.core.citation.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.uniprot.core.CrossReference;
import org.uniprot.core.citation.*;
import org.uniprot.core.util.Utils;

public abstract class AbstractCitationImpl implements Citation {
    private static final long serialVersionUID = -2752460607884626559L;
    private CitationType citationType;
    private List<String> authoringGroup;
    private List<Author> authors;
    private List<CrossReference<CitationDatabase>> citationCrossReferences;
    private String title;
    private PublicationDate publicationDate;

    AbstractCitationImpl(
            CitationType citationType,
            List<String> authoringGroup,
            List<Author> authors,
            List<CrossReference<CitationDatabase>> citationCrossReferences,
            String title,
            PublicationDate publicationDate) {
        this.citationType = citationType;
        this.authoringGroup = Utils.unmodifiableList(authoringGroup);
        this.authors = Utils.unmodifiableList(authors);
        this.citationCrossReferences = Utils.unmodifiableList(citationCrossReferences);
        this.title = Utils.emptyOrString(title);
        this.publicationDate = publicationDate;
    }

    @Override
    public List<CrossReference<CitationDatabase>> getCitationCrossReferences() {
        return citationCrossReferences;
    }

    @Override
    public Optional<CrossReference<CitationDatabase>> getCitationCrossReferenceByType(
            CitationDatabase citationDatabase) {
        return citationCrossReferences.stream()
                .filter(xref -> xref.getDatabase() == citationDatabase)
                .findAny();
    }

    @Override
    public boolean hasCitationCrossReferences() {
        return citationCrossReferences != null;
    }

    @Override
    public List<String> getAuthoringGroups() {
        return this.authoringGroup;
    }

    @Override
    public List<Author> getAuthors() {
        return this.authors;
    }

    @Override
    public CitationType getCitationType() {
        return citationType;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public boolean hasTitle() {
        return Utils.notNullNotEmpty(this.title);
    }

    @Override
    public PublicationDate getPublicationDate() {
        return publicationDate;
    }

    @Override
    public boolean hasAuthoringGroup() {
        return Utils.notNullNotEmpty(this.authoringGroup);
    }

    @Override
    public boolean hasAuthors() {
        return Utils.notNullNotEmpty(this.authors);
    }

    @Override
    public boolean hasPublicationDate() {
        return this.publicationDate != null;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((authoringGroup == null) ? 0 : authoringGroup.hashCode());
        result = prime * result + ((authors == null) ? 0 : authors.hashCode());
        result = prime * result + ((citationType == null) ? 0 : citationType.hashCode());
        result =
                prime * result
                        + ((citationCrossReferences == null)
                                ? 0
                                : citationCrossReferences.hashCode());
        result = prime * result + ((publicationDate == null) ? 0 : publicationDate.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        AbstractCitationImpl other = (AbstractCitationImpl) obj;
        return Objects.equals(this.authoringGroup, other.authoringGroup)
                && Objects.equals(this.authors, other.authors)
                && Objects.equals(this.citationType, other.citationType)
                && Objects.equals(this.citationCrossReferences, other.citationCrossReferences)
                && Objects.equals(this.publicationDate, other.publicationDate)
                && Objects.equals(this.title, other.title);
    }
}
