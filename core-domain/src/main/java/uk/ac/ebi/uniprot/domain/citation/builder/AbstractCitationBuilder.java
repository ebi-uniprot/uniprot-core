package uk.ac.ebi.uniprot.domain.citation.builder;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.citation.*;
import uk.ac.ebi.uniprot.domain.citation.impl.AuthorImpl;
import uk.ac.ebi.uniprot.domain.citation.impl.CitationXrefsImpl;
import uk.ac.ebi.uniprot.domain.citation.impl.PublicationDateImpl;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractCitationBuilder<T extends Citation> implements CitationBuilder<T> {
    protected List<String> authoringGroups = new ArrayList<>();
    protected List<Author> authors = new ArrayList<>();
    protected CitationXrefs xrefs;
    protected String title = "";
    protected PublicationDate publicationDate;

    public static PublicationDate createPublicationDate(String date) {
        return new PublicationDateImpl(date);
    }


    public static Author createAuthor(String name) {
        return new AuthorImpl(name);
    }

    public static CitationXrefs createCitationXrefs(List<DBCrossReference<CitationXrefType>> xrefs) {
        return new CitationXrefsImpl(xrefs);
    }

    public AbstractCitationBuilder<T> authoringGroups(List<String> val) {
        this.authoringGroups = val;
        return this;
    }

    public AbstractCitationBuilder<T> authors(List<Author> val) {
        this.authors = val;
        return this;
    }

    public AbstractCitationBuilder<T> citationXrefs(CitationXrefs xrefs) {
        this.xrefs = xrefs;
        return this;
    }

    public AbstractCitationBuilder<T> title(String title) {
        this.title = title;
        return this;
    }

    public AbstractCitationBuilder<T> publicationDate(PublicationDate publicationDate) {
        this.publicationDate = publicationDate;
        return this;
    }
}
