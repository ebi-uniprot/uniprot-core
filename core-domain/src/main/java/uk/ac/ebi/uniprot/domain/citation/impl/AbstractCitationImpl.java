package uk.ac.ebi.uniprot.domain.citation.impl;

import uk.ac.ebi.uniprot.common.Utils;
import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.citation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public abstract class AbstractCitationImpl implements Citation {
    private CitationType citationType;
    private List<String> authoringGroup;
    private List<Author> authors;
    private List<DBCrossReference<CitationXrefType>> citationXrefs;
    private String title;
    private PublicationDate publicationDate;

    private AbstractCitationImpl() {
        this.authors = Collections.emptyList();
        this.authoringGroup = Collections.emptyList();
    }

    public AbstractCitationImpl(CitationType citationType, List<String> authoringGroup, List<Author> authors,
                                List<DBCrossReference<CitationXrefType>> citationXrefs, String title, PublicationDate publicationDate) {
        this.citationType = citationType;
        this.authoringGroup = Utils.nonNullUnmodifiableList(authoringGroup);
        this.authors = Utils.nonNullUnmodifiableList(authors);
        this.citationXrefs = citationXrefs;
        this.title = Utils.nullToEmpty(title);
        this.publicationDate = publicationDate;
    }

    @Override
    public List<DBCrossReference<CitationXrefType>> getCitationXrefs() {
        return citationXrefs;
    }

    @Override
    public Optional<DBCrossReference<CitationXrefType>> getCitationXrefsByType(CitationXrefType type) {
        return citationXrefs.stream().filter(xref -> xref.getDatabaseType() == type).findAny();
    }

    @Override
    public boolean hasCitationXrefs() {
        return citationXrefs != null;
    }

    @Override
    public List<String> getAuthoringGroup() {
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
        return Utils.notEmpty(this.title);
    }

    @Override
    public PublicationDate getPublicationDate() {
        return publicationDate;
    }


    @Override
    public boolean hasAuthoringGroup(){
        return Utils.notEmpty(this.authoringGroup);
    }

    @Override
    public boolean hasAuthors(){
        return Utils.notEmpty(this.authors);
    }

    @Override
    public boolean hasPublicationDate(){
        return this.publicationDate != null;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((authoringGroup == null) ? 0 : authoringGroup.hashCode());
        result = prime * result + ((authors == null) ? 0 : authors.hashCode());
        result = prime * result + ((citationType == null) ? 0 : citationType.hashCode());
        result = prime * result + ((citationXrefs == null) ? 0 : citationXrefs.hashCode());
        result = prime * result + ((publicationDate == null) ? 0 : publicationDate.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
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
        AbstractCitationImpl other = (AbstractCitationImpl) obj;
        if (authoringGroup == null) {
            if (other.authoringGroup != null)
                return false;
        } else if (!authoringGroup.equals(other.authoringGroup))
            return false;
        if (authors == null) {
            if (other.authors != null)
                return false;
        } else if (!authors.equals(other.authors))
            return false;
        if (citationType != other.citationType)
            return false;
        if (citationXrefs == null) {
            if (other.citationXrefs != null)
                return false;
        } else if (!citationXrefs.equals(other.citationXrefs))
            return false;
        if (publicationDate == null) {
            if (other.publicationDate != null)
                return false;
        } else if (!publicationDate.equals(other.publicationDate))
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        return true;
    }
}
