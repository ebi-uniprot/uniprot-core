package uk.ac.ebi.uniprot.domain.citation.impl;

import uk.ac.ebi.uniprot.domain.citation.Author;
import uk.ac.ebi.uniprot.domain.citation.Citation;
import uk.ac.ebi.uniprot.domain.citation.CitationType;
import uk.ac.ebi.uniprot.domain.citation.CitationXrefs;
import uk.ac.ebi.uniprot.domain.citation.PublicationDate;

import java.util.Collections;
import java.util.List;

public abstract class CitationImpl implements Citation {
    private final CitationType type;
    private final List<String> authoringGroups;
    private final List<Author> authors;
    private final CitationXrefs xrefs;
    private final String title;
    private final PublicationDate publicationDate;
    CitationImpl(CitationType type, List<String> authoringGroups,
            List<Author> authors, CitationXrefs xrefs,
            String title, PublicationDate publicationDate
            ){
        this.type = type;
        if((authoringGroups == null ) || authoringGroups.isEmpty()){
            this.authoringGroups = Collections.emptyList();
        }else{
            this.authoringGroups = Collections.unmodifiableList(authoringGroups);
        }
        
        if((authors == null ) || authors.isEmpty()){
            this.authors = Collections.emptyList();
        }else{
            this.authors = Collections.unmodifiableList(authors);
        }
        this.xrefs = xrefs;
        this.title = title;
        this.publicationDate =publicationDate;
    }
    @Override
    public CitationXrefs getCitationXrefs() {
      return xrefs;
    }

    @Override
    public boolean hasCitationXrefs() {
        return xrefs !=null;
    }

    @Override
    public List<String> getAuthoringGroup() {
        return this.authoringGroups;
    }

    @Override
    public List<Author> getAuthors() {
        return this.authors;
    }

    @Override
    public CitationType getCitationType() {
       return type;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public boolean hasTitle() {
        return (( title !=null ) && !title.isEmpty());
    }

    @Override
    public PublicationDate getPublicationDate() {
       return publicationDate;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((authoringGroups == null) ? 0 : authoringGroups.hashCode());
        result = prime * result + ((authors == null) ? 0 : authors.hashCode());
        result = prime * result + ((publicationDate == null) ? 0 : publicationDate.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        result = prime * result + ((xrefs == null) ? 0 : xrefs.hashCode());
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
        CitationImpl other = (CitationImpl) obj;
        if (authoringGroups == null) {
            if (other.authoringGroups != null)
                return false;
        } else if (!authoringGroups.equals(other.authoringGroups))
            return false;
        if (authors == null) {
            if (other.authors != null)
                return false;
        } else if (!authors.equals(other.authors))
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
        if (type != other.type)
            return false;
        if (xrefs == null) {
            if (other.xrefs != null)
                return false;
        } else if (!xrefs.equals(other.xrefs))
            return false;
        return true;
    }

}
