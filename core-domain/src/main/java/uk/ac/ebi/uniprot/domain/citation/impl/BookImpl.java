package uk.ac.ebi.uniprot.domain.citation.impl;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.citation.*;

import java.util.List;

import org.uniprot.core.common.Utils;

import static java.util.Collections.emptyList;


public class BookImpl extends AbstractCitationImpl implements Book {
    private static final long serialVersionUID = 7240686919749710678L;
    private String bookName;
    private List<Author> editors;
    private String firstPage;
    private String lastPage;
    private String volume;
    private String publisher;
    private String address;

    private BookImpl() {
        this(emptyList(), emptyList(), emptyList(), null, null, null, emptyList(), null, null, null, null, null);
    }

    public BookImpl(List<String> authoringGroup, List<Author> authors, List<DBCrossReference<CitationXrefType>> citationXrefs,
                    String title, PublicationDate publicationDate, String bookName, List<Author> editors,
                    String firstPage, String lastPage, String volume, String publisher, String address) {
        super(CitationType.BOOK, authoringGroup, authors, citationXrefs, title, publicationDate);
        this.bookName = Utils.nullToEmpty(bookName);
        this.editors = Utils.nonNullUnmodifiableList(editors);
        this.firstPage = Utils.nullToEmpty(firstPage);
        this.lastPage = Utils.nullToEmpty(lastPage);
        this.volume = Utils.nullToEmpty(volume);
        this.publisher = Utils.nullToEmpty(publisher);
        this.address = Utils.nullToEmpty(address);
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
        return Utils.notEmpty(this.bookName);
    }

    @Override
    public boolean hasEditors() {
        return Utils.notEmpty(this.editors);
    }

    @Override
    public boolean hasFirstPage() {
        return Utils.notEmpty(this.firstPage);
    }

    @Override
    public boolean hasLastPage() {
        return Utils.notEmpty(this.lastPage);
    }

    @Override
    public boolean hasVolume() {
        return Utils.notEmpty(this.volume);
    }

    @Override
    public boolean hasPublisher() {
        return Utils.notEmpty(this.publisher);
    }

    @Override
    public boolean hasAddress() {
        return Utils.notEmpty(this.address);
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
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        BookImpl other = (BookImpl) obj;
        if (address == null) {
            if (other.address != null)
                return false;
        } else if (!address.equals(other.address))
            return false;
        if (bookName == null) {
            if (other.bookName != null)
                return false;
        } else if (!bookName.equals(other.bookName))
            return false;
        if (editors == null) {
            if (other.editors != null)
                return false;
        } else if (!editors.equals(other.editors))
            return false;
        if (firstPage == null) {
            if (other.firstPage != null)
                return false;
        } else if (!firstPage.equals(other.firstPage))
            return false;
        if (lastPage == null) {
            if (other.lastPage != null)
                return false;
        } else if (!lastPage.equals(other.lastPage))
            return false;
        if (publisher == null) {
            if (other.publisher != null)
                return false;
        } else if (!publisher.equals(other.publisher))
            return false;
        if (volume == null) {
            return other.volume == null;
        } else return volume.equals(other.volume);
    }
}
