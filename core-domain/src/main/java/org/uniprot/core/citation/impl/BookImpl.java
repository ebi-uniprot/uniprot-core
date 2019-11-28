package org.uniprot.core.citation.impl;

import static java.util.Collections.emptyList;

import java.util.List;
import java.util.Objects;

import org.uniprot.core.DBCrossReference;
import org.uniprot.core.citation.*;
import org.uniprot.core.util.Utils;

public class BookImpl extends AbstractCitationImpl implements Book {
    private static final long serialVersionUID = 7240686919749710678L;
    private String bookName;
    private List<Author> editors;
    private String firstPage;
    private String lastPage;
    private String volume;
    private String publisher;
    private String address;

    // no arg constructor for JSON deserialization
    BookImpl() {
        this(
                emptyList(),
                emptyList(),
                emptyList(),
                null,
                null,
                null,
                emptyList(),
                null,
                null,
                null,
                null,
                null);
    }

    public BookImpl(
            List<String> authoringGroup,
            List<Author> authors,
            List<DBCrossReference<CitationXrefType>> citationXrefs,
            String title,
            PublicationDate publicationDate,
            String bookName,
            List<Author> editors,
            String firstPage,
            String lastPage,
            String volume,
            String publisher,
            String address) {
        super(CitationType.BOOK, authoringGroup, authors, citationXrefs, title, publicationDate);
        this.bookName = Utils.emptyOrString(bookName);
        this.editors = Utils.unmodifiableList(editors);
        this.firstPage = Utils.emptyOrString(firstPage);
        this.lastPage = Utils.emptyOrString(lastPage);
        this.volume = Utils.emptyOrString(volume);
        this.publisher = Utils.emptyOrString(publisher);
        this.address = Utils.emptyOrString(address);
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

    @Override
    public boolean hasBookName() {
        return Utils.notNullOrEmpty(this.bookName);
    }

    @Override
    public boolean hasEditors() {
        return Utils.notNullOrEmpty(this.editors);
    }

    @Override
    public boolean hasFirstPage() {
        return Utils.notNullOrEmpty(this.firstPage);
    }

    @Override
    public boolean hasLastPage() {
        return Utils.notNullOrEmpty(this.lastPage);
    }

    @Override
    public boolean hasVolume() {
        return Utils.notNullOrEmpty(this.volume);
    }

    @Override
    public boolean hasPublisher() {
        return Utils.notNullOrEmpty(this.publisher);
    }

    @Override
    public boolean hasAddress() {
        return Utils.notNullOrEmpty(this.address);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((address == null) ? 0 : address.hashCode());
        result = prime * result + ((bookName == null) ? 0 : bookName.hashCode());
        result = prime * result + ((editors == null) ? 0 : editors.hashCode());
        result = prime * result + ((firstPage == null) ? 0 : firstPage.hashCode());
        result = prime * result + ((lastPage == null) ? 0 : lastPage.hashCode());
        result = prime * result + ((publisher == null) ? 0 : publisher.hashCode());
        result = prime * result + ((volume == null) ? 0 : volume.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        BookImpl other = (BookImpl) obj;
        return Objects.equals(this.address, other.address)
                && Objects.equals(this.bookName, other.bookName)
                && Objects.equals(this.editors, other.editors)
                && Objects.equals(this.firstPage, other.firstPage)
                && Objects.equals(this.lastPage, other.lastPage)
                && Objects.equals(this.publisher, other.publisher)
                && Objects.equals(this.volume, other.volume);
    }
}
