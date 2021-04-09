package org.uniprot.core.citation.impl;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.uniprot.core.CrossReference;
import org.uniprot.core.citation.*;
import org.uniprot.core.util.Crc64;
import org.uniprot.core.util.Utils;

public abstract class AbstractCitationImpl implements Citation {
    private static final long serialVersionUID = 3081031699917057612L;
    protected static final String CITATION_TYPE_PREFIX = " citationType-";
    protected static final String TITLE_PREFIX = " title-";
    protected static final String AUTHORS_PREFIX = " authors-";
    protected static final String AUTHORING_GROUP_PREFIX = " authoringGroup-";
    protected static final String PUBLICATION_DATE_PREFIX = " publicationDate-";
    protected static final String JOURNAL_PREFIX = " journal-";
    protected static final String BOOK_NAME_PREFIX = " bookName-";
    protected static final String ELECTRONIC_JOURNAL_PREFIX = " electronicJournal-";
    protected static final String VOLUME_PREFIX = " volume-";
    protected static final String BOOK_VOLUME_PREFIX = " bookVolume-";
    protected static final String FIRST_PAGE_PREFIX = " firstPage-";
    protected static final String BOOK_FIRST_PAGE_PREFIX = " bookFirstPage-";
    protected static final String LAST_PAGE_PREFIX = " lastPage-";
    protected static final String BOOK_LAST_PAGE_PREFIX = " bookLastPage-";
    protected static final String PUBLISHER_PREFIX = " publisher-";
    protected static final String LOCATOR_PREFIX = " locator-";
    protected static final String PATENT_NUMBER_PREFIX = " patentNumber-";
    protected static final String SUBMISSION_DATABASE_PREFIX = " submissionDatabase-";
    protected static final String INSTITUTE_PREFIX = " institute-";
    protected static final String ADDRESS_PREFIX = " address-";
    protected String id;
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
        idInput.append(CITATION_TYPE_PREFIX).append(this.citationType.getName());

        if (this.hasTitle()) {
            idInput.append(TITLE_PREFIX).append(this.title);
        }
        if (this.hasAuthors()) {
            String authorsStr =
                    this.authors.stream().map(Author::getValue).collect(Collectors.joining(" "));
            idInput.append(AUTHORS_PREFIX).append(authorsStr);
        }
        if (this.hasAuthoringGroup()) {
            String authorsStr = String.join(" ", this.authoringGroup);
            idInput.append(AUTHORING_GROUP_PREFIX).append(authorsStr);
        }
        if (this.hasPublicationDate()) {
            idInput.append(PUBLICATION_DATE_PREFIX).append(this.publicationDate.getValue());
        }
        return idInput.toString();
    }

    protected String generateId() {
        return getDatabaseId(CitationDatabase.PUBMED)
                .orElse(getDatabaseId(CitationDatabase.AGRICOLA).orElseGet(getCitationId()));
    }

    private Supplier<String> getCitationId() {
        return () ->
                getDatabaseId(CitationDatabase.DOI)
                        .map(this::generateHash)
                        .orElseGet(() -> generateHash(getHashInput()));
    }

    String generateHash(String hashInput) {
        String base16Hash = Crc64.getCrc64(hashInput);
        String base32Hash = new BigInteger(base16Hash, 16).toString(32).toUpperCase();
        return "CI-" + base32Hash;
    }

    private Optional<String> getDatabaseId(CitationDatabase database) {
        return citationCrossReferences.stream()
                .filter(xref -> database == xref.getDatabase())
                .map(CrossReference::getId)
                .findFirst();
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
