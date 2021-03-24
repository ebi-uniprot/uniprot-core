package org.uniprot.core.citation.impl;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.uniprot.core.CrossReference;
import org.uniprot.core.citation.*;
import org.uniprot.core.util.Crc64;
import org.uniprot.core.util.Utils;

public abstract class AbstractCitationImpl implements Citation {
    private static final long serialVersionUID = -2752460607884626559L;
    private final String id;
    private final CitationType citationType;
    private final List<String> authoringGroup;
    private final List<Author> authors;
    private final List<CrossReference<CitationDatabase>> citationCrossReferences;
    private final String title;
    private final PublicationDate publicationDate;

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
        this.id = generateId();
    }

    @Override
    public String getId() {
        return id;
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
        return Utils.notNullNotEmpty(citationCrossReferences);
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

    protected String getHashInput() {
        StringBuilder idInput = new StringBuilder();
        idInput.append(this.citationType.getName());

        getDatabaseId(CitationDatabase.DOI)
                .ifPresent(idInput::append);

        if(this.hasTitle()) {
            idInput.append(this.title);
        }
        if(this.hasAuthors()) {
            String authorsStr = this.authors.stream()
                    .map(Author::getValue)
                    .collect(Collectors.joining(" "));
            idInput.append(authorsStr);
        }
        if(this.hasAuthoringGroup()) {
            String authorsStr = String.join(" ", this.authoringGroup);
            idInput.append(authorsStr);
        }
        if(this.hasPublicationDate()){
            idInput.append(this.publicationDate.getValue());
        }
        return idInput.toString();
    }

    private String generateId() {
        return getDatabaseId(CitationDatabase.PUBMED)
                .orElse(getDatabaseId(CitationDatabase.AGRICOLA)
                        .orElse(generateHash()));
    }

    private Optional<String> getDatabaseId(CitationDatabase database) {
        return citationCrossReferences
                .stream()
                .filter(xref -> database == xref.getDatabase())
                .map(CrossReference::getId)
                .findFirst();
    }

    private String generateHash() {
        String hashInput = getHashInput();
        String base16Hash = Crc64.getCrc64(hashInput);
        String base32Hash = new BigInteger(base16Hash,16)
                .toString(32)
                .toUpperCase();
        return "CI-"+base32Hash;
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
