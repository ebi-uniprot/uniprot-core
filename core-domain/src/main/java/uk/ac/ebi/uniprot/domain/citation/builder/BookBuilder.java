package uk.ac.ebi.uniprot.domain.citation.builder;

import uk.ac.ebi.uniprot.domain.citation.Author;
import uk.ac.ebi.uniprot.domain.citation.Book;
import uk.ac.ebi.uniprot.domain.citation.CitationType;
import uk.ac.ebi.uniprot.domain.citation.CitationXrefs;
import uk.ac.ebi.uniprot.domain.citation.PublicationDate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    class BookImpl extends AbstractCitationImpl implements Book {
        private final String bookName;
        private final List<Author> editors;
        private final String firstPage;
        private final String lastPage;
        private final String volume;
        private final String publisher;
        private final String address;

         BookImpl(List<String> authoringGroups, List<Author> authors,
            CitationXrefs xrefs, String title,
            PublicationDate publicationDate,
            String bookName, List<Author> editors,
            String firstPage, String lastPage, String volume,
            String publisher, String address) {
            super(CitationType.BOOK, authoringGroups, authors, xrefs, title, publicationDate);
            this.bookName = bookName;
            if ((editors == null) || editors.isEmpty()) {
                this.editors = Collections.emptyList();
            } else {
                this.editors = Collections.unmodifiableList(editors);
            }
            this.firstPage = firstPage;
            this.lastPage = lastPage;
            this.volume = volume;
            this.publisher = publisher;
            this.address = address;
        }

        @Override
        public String getBookName() {
            return this.bookName;
        }

        @Override
        public List<Author> getEditors() {
            return this.editors;
        }

        @Override
        public String getFirstPage() {
            return this.firstPage;
        }

        @Override
        public String getLastPage() {
            return this.lastPage;
        }

        @Override
        public String getVolume() {
            return this.volume;
        }

        @Override
        public String getPublisher() {
            return this.publisher;
        }

        @Override
        public String getAddress() {
            return this.address;
        }
    }
}
