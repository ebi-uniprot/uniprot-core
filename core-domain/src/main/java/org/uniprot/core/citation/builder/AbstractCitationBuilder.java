package org.uniprot.core.citation.builder;

import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.modifiableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import org.uniprot.core.DBCrossReference;
import org.uniprot.core.Value;
import org.uniprot.core.citation.Author;
import org.uniprot.core.citation.Citation;
import org.uniprot.core.citation.CitationXrefType;
import org.uniprot.core.citation.PublicationDate;
import org.uniprot.core.citation.impl.AuthorImpl;
import org.uniprot.core.citation.impl.PublicationDateImpl;

public abstract class AbstractCitationBuilder<
                B extends AbstractCitationBuilder<B, T>, T extends Citation>
        implements CitationBuilder<B, T> {
    protected List<String> authoringGroups = new ArrayList<>();
    protected List<Author> authors = new ArrayList<>();
    protected List<DBCrossReference<CitationXrefType>> xrefs = new ArrayList<>();
    protected String title = "";
    protected PublicationDate publicationDate;

    public @Nonnull B authoringGroups(List<String> groups) {
        this.authoringGroups = modifiableList(groups);
        return getThis();
    }

    public @Nonnull B addAuthorGroup(String group) {
        addOrIgnoreNull(group, this.authoringGroups);
        return getThis();
    }

    public @Nonnull B authors(List<Author> authors) {
        this.authors = modifiableList(authors);
        return getThis();
    }

    public @Nonnull B authors(Collection<String> authors) {
        this.authors.addAll(authors.stream().map(AuthorImpl::new).collect(Collectors.toList()));
        return getThis();
    }

    public @Nonnull B addAuthor(String author) {
        if (author != null) this.authors.add(new AuthorImpl(author));
        return getThis();
    }

    public @Nonnull B addAuthor(Author author) {
        addOrIgnoreNull(author, this.authors);
        return getThis();
    }

    public @Nonnull B citationXrefs(List<DBCrossReference<CitationXrefType>> citationXrefs) {
        this.xrefs = citationXrefs;
        return getThis();
    }

    public @Nonnull B addCitationXrefs(DBCrossReference<CitationXrefType> citationXref) {
        addOrIgnoreNull(citationXref, this.xrefs);
        return getThis();
    }

    public @Nonnull B title(String title) {
        this.title = title;
        return getThis();
    }

    public @Nonnull B publicationDate(String publicationDate) {
        this.publicationDate = new PublicationDateImpl(publicationDate);
        return getThis();
    }

    public @Nonnull B publicationDate(PublicationDate publicationDate) {
        this.publicationDate = publicationDate;
        return getThis();
    }

    protected static <B extends AbstractCitationBuilder<B, T>, T extends Citation> void init(B builder, T instance) {
        builder.citationXrefs(instance.getCitationXrefs())
                .title(instance.getTitle())
                .publicationDate(instance.getPublicationDate())
                .authors(
                        instance.getAuthors().stream()
                                .map(Value::getValue)
                                .collect(Collectors.toList()))
                .authoringGroups(instance.getAuthoringGroup());
    }

    protected abstract @Nonnull B getThis();
}
