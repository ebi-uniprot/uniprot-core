package uk.ac.ebi.uniprot.domain.citation.impl;

import uk.ac.ebi.uniprot.domain.citation.Author;
import uk.ac.ebi.uniprot.domain.citation.Book;
import uk.ac.ebi.uniprot.domain.citation.CitationType;
import uk.ac.ebi.uniprot.domain.citation.CitationXrefs;
import uk.ac.ebi.uniprot.domain.citation.PublicationDate;

import java.util.Collections;
import java.util.List;

public class BookImpl extends CitationImpl implements Book {
    private final String bookName;
    private final List<Author> editors;
    private final String firstPage;
    private final String lastPage;
    private final String volume;
    private final String publisher;
    private final String address;

    public BookImpl(List<String> authoringGroups, List<Author> authors,
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
