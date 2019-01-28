package uk.ac.ebi.uniprot.domain.citation.builder;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.Value;
import uk.ac.ebi.uniprot.domain.citation.*;
import uk.ac.ebi.uniprot.domain.citation.impl.AuthorImpl;
import uk.ac.ebi.uniprot.domain.citation.impl.PublicationDateImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static uk.ac.ebi.uniprot.domain.util.Utils.nonNullAddAll;

public abstract class AbstractCitationBuilder<B extends AbstractCitationBuilder<B, T>, T extends Citation> implements CitationBuilder<B, T> {
    protected List<String> authoringGroups = new ArrayList<>();
    protected List<Author> authors = new ArrayList<>();
    protected List<DBCrossReference<CitationXrefType>> xrefs = new ArrayList<>();
    protected String title = "";
    protected PublicationDate publicationDate;

    public B authoringGroups(List<String> groups) {
        nonNullAddAll(groups, this.authoringGroups);
        return getThis();
    }

    public B addAuthorGroup(String group) {
        this.authoringGroups.add(group);
        return getThis();
    }

    public B authors(Collection<Author> authors) {
        nonNullAddAll(authors, this.authors);
        return getThis();
    }

    public B authors(List<String> authors) {
        this.authors.addAll(authors.stream()
                                    .map(AuthorImpl::new)
                                    .collect(Collectors.toList()));
        return getThis();
    }

    public B addAuthor(String author) {
        this.authors.add(new AuthorImpl(author));
        return getThis();
    }

    public B addAuthor(Author author) {
        this.authors.add(author);
        return getThis();
    }

//    public B citationXrefs(CitationXrefs xrefs) {
//        this.xrefs = xrefs;
//        return getThis();
//    }

    public B citationXrefs(List<DBCrossReference<CitationXrefType>> citationXrefs) {
        this.xrefs = citationXrefs;
        return getThis();
    }

    public B title(String title) {
        this.title = title;
        return getThis();
    }

    public B publicationDate(String publicationDate) {
        this.publicationDate = new PublicationDateImpl(publicationDate);
        return getThis();
    }

    public List<String> getAuthoringGroups() {
        return authoringGroups;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public List<DBCrossReference<CitationXrefType>> getXrefs() {
        return xrefs;
    }

    public String getTitle() {
        return title;
    }

    public PublicationDate getPublicationDate() {
        return publicationDate;
    }

    protected void init(T instance) {
        this.citationXrefs(instance.getCitationXrefs())
                .title(instance.getTitle())
                .publicationDate(instance.getPublicationDate().getValue())
                .authors(instance.getAuthors().stream().map(Value::getValue).collect(Collectors.toList()))
                .authoringGroups(instance.getAuthoringGroup());
    }

    protected abstract B getThis();
}
