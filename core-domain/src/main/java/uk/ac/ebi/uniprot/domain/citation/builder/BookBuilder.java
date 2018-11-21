package uk.ac.ebi.uniprot.domain.citation.builder;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ebi.uniprot.domain.citation.Author;
import uk.ac.ebi.uniprot.domain.citation.Book;
import uk.ac.ebi.uniprot.domain.citation.impl.BookImpl;

public final class BookBuilder extends AbstractCitationBuilder<Book> {
    public static BookBuilder newInstance() {
        return new BookBuilder();
    }

    private String bookName;
    private List<Author> editors = new ArrayList<>();
    private String firstPage = "";
    private String lastPage = "";
    private String volume = "";
    private String publisher = "";
    private String address = "";

    public Book build() {
        return new BookImpl(authoringGroups, authors,
                xrefs, title, publicationDate,
                bookName, editors, firstPage, lastPage, volume,
                publisher, address);
    }

    public BookBuilder bookName(String bookName) {
        this.bookName = bookName;
        return this;
    }

    public BookBuilder editors(List<Author> val) {
        this.editors = val;
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

}
