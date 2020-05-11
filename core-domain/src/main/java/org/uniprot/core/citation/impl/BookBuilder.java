package org.uniprot.core.citation.impl;

import org.uniprot.core.citation.Author;
import org.uniprot.core.citation.Book;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

public final class BookBuilder extends AbstractCitationBuilder<BookBuilder, Book> {
    private String bookName;
    private List<Author> editors = new ArrayList<>();
    private String firstPage = "";
    private String lastPage = "";
    private String volume = "";
    private String publisher = "";
    private String address = "";

    public @Nonnull Book build() {
        return new BookImpl(
                authoringGroups,
                authors,
                citationCrossReferences,
                title,
                publicationDate,
                bookName,
                editors,
                firstPage,
                lastPage,
                volume,
                publisher,
                address);
    }

    public static @Nonnull BookBuilder from(@Nonnull Book instance) {
        BookBuilder builder = new BookBuilder();
        init(builder, instance);
        return builder.address(instance.getAddress())
                .bookName(instance.getBookName())
                .editorsSet(
                        instance.getEditors().stream()
                                .map(Author::getValue)
                                .collect(Collectors.toList()))
                .firstPage(instance.getFirstPage())
                .lastPage(instance.getLastPage())
                .publisher(instance.getPublisher())
                .volume(instance.getVolume());
    }

    public @Nonnull BookBuilder bookName(String bookName) {
        this.bookName = bookName;
        return this;
    }

    public @Nonnull BookBuilder editorsSet(List<String> editors) {
        List<Author> newEditors = new ArrayList<>();
        if (editors != null) {
            newEditors = editors.stream().map(AuthorImpl::new).collect(Collectors.toList());
        }
        this.editors = newEditors;
        return this;
    }

    public @Nonnull BookBuilder editorsSet(Collection<Author> editors) {
        List<Author> newEditors = new ArrayList<>();
        if (editors != null) {
            newEditors.addAll(editors);
        }
        this.editors = newEditors;
        return this;
    }

    public @Nonnull BookBuilder editorsAdd(String editor) {
        if (editor != null) this.editors.add(new AuthorImpl(editor));
        return this;
    }

    public @Nonnull BookBuilder firstPage(String firstPage) {
        this.firstPage = firstPage;
        return this;
    }

    public @Nonnull BookBuilder lastPage(String lastPage) {
        this.lastPage = lastPage;
        return this;
    }

    public @Nonnull BookBuilder volume(String volume) {
        this.volume = volume;
        return this;
    }

    public @Nonnull BookBuilder publisher(String publisher) {
        this.publisher = publisher;
        return this;
    }

    public @Nonnull BookBuilder address(String address) {
        this.address = address;
        return this;
    }

    @Override
    protected @Nonnull BookBuilder getThis() {
        return this;
    }
}
