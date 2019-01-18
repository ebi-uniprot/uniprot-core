package uk.ac.ebi.uniprot.domain.citation.builder;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.citation.Author;
import uk.ac.ebi.uniprot.domain.citation.Citation;
import uk.ac.ebi.uniprot.domain.citation.CitationXrefType;
import uk.ac.ebi.uniprot.domain.citation.PublicationDate;
import uk.ac.ebi.uniprot.domain.citation.impl.AuthorImpl;
import uk.ac.ebi.uniprot.domain.citation.impl.PublicationDateImpl;

public abstract class
AbstractCitationBuilder<B extends AbstractCitationBuilder<B,T>,T extends Citation>
	implements CitationBuilder<T>{

    protected List<String> authoringGroups = new ArrayList<>();
    protected List<Author> authors = new ArrayList<>();
    protected List<DBCrossReference<CitationXrefType>> xrefs;
    protected String title = "";
    protected PublicationDate publicationDate;
    protected abstract B getThis();
    public static PublicationDate createPublicationDate(String date) {
        return new PublicationDateImpl(date);
    }


    public static Author createAuthor(String name) {
        return new AuthorImpl(name);
    }


    public B authoringGroups(List<String> val) {
        this.authoringGroups = val;
        return getThis();
    }

    public B authors(List<Author> val) {
        this.authors = val;
        return getThis();
    }

    public B citationXrefs(List<DBCrossReference<CitationXrefType>> xrefs) {
        this.xrefs = xrefs;
        return getThis();
    }

    public B title(String title) {
        this.title = title;
        return getThis();
    }

    public B publicationDate(PublicationDate publicationDate) {
        this.publicationDate = publicationDate;
        return getThis();
    }
}
