package uk.ac.ebi.uniprot.domain.citation2.builder;

import uk.ac.ebi.uniprot.domain.Value;
import uk.ac.ebi.uniprot.domain.citation2.Author;
import uk.ac.ebi.uniprot.domain.citation2.Citation;
import uk.ac.ebi.uniprot.domain.citation2.CitationXrefs;
import uk.ac.ebi.uniprot.domain.citation2.PublicationDate;
import uk.ac.ebi.uniprot.domain.citation2.impl.AuthorImpl;
import uk.ac.ebi.uniprot.domain.citation2.impl.PublicationDateImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractCitationBuilder<B extends AbstractCitationBuilder<B, T>, T extends Citation> implements CitationBuilder<B, T> {
    protected List<String> authoringGroups = new ArrayList<>();
    protected List<Author> authors = new ArrayList<>();
    protected CitationXrefs xrefs;
    protected String title = "";
    protected PublicationDate publicationDate;

    public B authoringGroups(List<String> groups) {
        this.authoringGroups.addAll(groups);
        return (B) this;
    }

    public B addAuthorGroup(String group) {
        this.authoringGroups.add(group);
        return (B) this;
    }

    public B authors(List<String> authors) {
        this.authors = authors.stream()
                .map(AuthorImpl::new)
                .collect(Collectors.toList());
        return (B) this;
    }

    public B addAuthor(String author) {
        this.authors.add(new AuthorImpl(author));
        return (B) this;
    }

    public B citationXrefs(CitationXrefs xrefs) {
        this.xrefs = xrefs;
        return (B) this;
    }

    public B title(String title) {
        this.title = title;
        return (B) this;
    }

    public B publicationDate(String publicationDate) {
        this.publicationDate = new PublicationDateImpl(publicationDate);
        return (B) this;
    }

    public List<String> getAuthoringGroups() {
        return authoringGroups;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public CitationXrefs getXrefs() {
        return xrefs;
    }

    public String getTitle() {
        return title;
    }

    public PublicationDate getPublicationDate() {
        return publicationDate;
    }

    protected void init(AbstractCitationBuilder<B, T> builder, T instance) {
        builder.citationXrefs(instance.getCitationXrefs())
                .title(instance.getTitle())
                .publicationDate(instance.getPublicationDate().getValue())
                .authors(instance.getAuthors().stream().map(Value::getValue).collect(Collectors.toList()))
                .authoringGroups(instance.getAuthoringGroup());
    }
}
