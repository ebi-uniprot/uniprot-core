package uk.ac.ebi.uniprot.domain.citation.builder;

import uk.ac.ebi.uniprot.domain.citation.Author;
import uk.ac.ebi.uniprot.domain.citation.Citation;
import uk.ac.ebi.uniprot.domain.citation.CitationType;
import uk.ac.ebi.uniprot.domain.citation.CitationXrefs;
import uk.ac.ebi.uniprot.domain.citation.PublicationDate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

abstract class AbstractCitationBuilder<T extends Citation> implements CitationBuilder<T> {
    protected List<String> authoringGroups = new ArrayList<>();
    protected List<Author> authors = new ArrayList<>();
    protected CitationXrefs xrefs;
    protected String title = "";
    protected PublicationDate publicationDate;
    
    public static PublicationDate createPublicationDate(String date){
        return new PublicationDateImpl(date);
    }

    
    public static Author createAuthor(String name){
        return new AuthorImpl(name);
    }

    public AbstractCitationBuilder authoringGroups(List<String> val) {
        this.authoringGroups = val;
        return this;
    }

    public AbstractCitationBuilder authors(List<Author> val) {
        this.authors = val;
        return this;
    }

    public AbstractCitationBuilder citationXrefs(CitationXrefs xrefs) {
        this.xrefs = xrefs;
        return this;
    }

    public AbstractCitationBuilder title(String title) {
        this.title = title;
        return this;
    }

    public AbstractCitationBuilder publicationDate(PublicationDate publicationDate) {
        this.publicationDate = publicationDate;
        return this;
    }

    
     abstract class AbstractCitationImpl implements Citation {
        private final CitationType type;
        private final List<String> authoringGroups;
        private final List<Author> authors;
        private final CitationXrefs xrefs;
        private final String title;
        private final PublicationDate publicationDate;
        AbstractCitationImpl(CitationType type, List<String> authoringGroups,
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
            AbstractCitationImpl other = (AbstractCitationImpl) obj;
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
     
     static class PublicationDateImpl implements PublicationDate {
         private final String date;
         public PublicationDateImpl(String date){
             this.date = date;
         }
        @Override
        public String getValue() {
           return date;
        }
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((date == null) ? 0 : date.hashCode());
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
            PublicationDateImpl other = (PublicationDateImpl) obj;
            if (date == null) {
                if (other.date != null)
                    return false;
            } else if (!date.equals(other.date))
                return false;
            return true;
        }
        
        
     }
     static class AuthorImpl implements Author {
         private final String author;
         public AuthorImpl(String author){
             this.author = author;
         }
         @Override
         public String getValue() {
             return author;
         }
         @Override
         public int hashCode() {
             final int prime = 31;
             int result = 1;
             result = prime * result + ((author == null) ? 0 : author.hashCode());
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
             AuthorImpl other = (AuthorImpl) obj;
             if (author == null) {
                 if (other.author != null)
                     return false;
             } else if (!author.equals(other.author))
                 return false;
             return true;
         }
         @Override
         public String toString() {
             return author;
         }

     }

}
