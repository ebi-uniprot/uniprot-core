package org.uniprot.core.citation.builder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.uniprot.core.citation.Author;
import org.uniprot.core.citation.Book;
import org.uniprot.core.citation.impl.AuthorImpl;
import org.uniprot.core.citation.impl.BookImpl;

public final class BookBuilder extends AbstractCitationBuilder<BookBuilder, Book> {
    private String bookName;
    private List<Author> editors = new ArrayList<>();
    private String firstPage = "";
    private String lastPage = "";
    private String volume = "";
    private String publisher = "";
    private String address = "";

    public Book build() {
        return new BookImpl(authoringGroups, authors, xrefs, title, publicationDate, bookName, editors,
                            firstPage, lastPage, volume, publisher, address);
    }

    @Override
    public BookBuilder from(Book instance) {
        init(instance);
        return this
                .address(instance.getAddress())
                .bookName(instance.getBookName())
                .editors(instance.getEditors().stream().map(Author::getValue).collect(Collectors.toList()))
                .firstPage(instance.getFirstPage())
                .lastPage(instance.getLastPage())
                .publisher(instance.getPublisher())
                .volume(instance.getVolume());
    }

    public BookBuilder bookName(String bookName) {
        this.bookName = bookName;
        return this;
    }

    public BookBuilder editors(List<String> editors) {
        List<Author> newEditors = new ArrayList<>();
        if (editors != null) {
            newEditors = editors.stream().map(AuthorImpl::new).collect(Collectors.toList());
        }
        this.editors = newEditors;
        return this;
    }

    public BookBuilder editors(Collection<Author> editors) {
        List<Author> newEditors = new ArrayList<>();
        if (editors != null) {
            newEditors.addAll(editors);
        }
        this.editors = newEditors;
        return this;
    }

    public BookBuilder addEditor(String editor) {
        this.editors.add(new AuthorImpl(editor));
        return this;
    }

    public BookBuilder firstPage(String firstPage) {
        this.firstPage = firstPage;
        return this;
    }

    public BookBuilder lastPage(String lastPage) {
        this.lastPage = lastPage;
        return this;
    }

    public BookBuilder volume(String volume) {
        this.volume = volume;
        return this;
    }

    public BookBuilder publisher(String publisher) {
        this.publisher = publisher;
        return this;
    }

    public BookBuilder address(String address) {
        this.address = address;
        return this;
    }

    @Override
    protected BookBuilder getThis() {
        return this;
    }
}
