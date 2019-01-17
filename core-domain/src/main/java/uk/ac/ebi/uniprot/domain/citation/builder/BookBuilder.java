package uk.ac.ebi.uniprot.domain.citation.builder;

import uk.ac.ebi.uniprot.domain.citation.Author;
import uk.ac.ebi.uniprot.domain.citation.Book;
import uk.ac.ebi.uniprot.domain.citation.impl.AuthorImpl;
import uk.ac.ebi.uniprot.domain.citation.impl.BookImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class BookBuilder extends AbstractCitationBuilder<BookBuilder, Book> {
    private String bookName;
    private List<Author> editors = new ArrayList<>();
    private String firstPage = "";
    private String lastPage = "";
    private String volume = "";
    private String publisher = "";
    private String address = "";

    public Book build() {
        return new BookImpl(this);
    }

    @Override
    public BookBuilder from(Book instance) {
        BookBuilder bookBuilder = new BookBuilder();
        init(bookBuilder, instance);
        return bookBuilder
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
        this.editors = editors.stream().map(AuthorImpl::new).collect(Collectors.toList());
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

    public String getBookName() {
        return bookName;
    }

    public List<Author> getEditors() {
        return editors;
    }

    public String getFirstPage() {
        return firstPage;
    }

    public String getLastPage() {
        return lastPage;
    }

    public String getVolume() {
        return volume;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getAddress() {
        return address;
    }

    @Override
    protected BookBuilder getThis() {
        return this;
    }
}
