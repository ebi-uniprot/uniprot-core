package uk.ac.ebi.uniprot.domain.citation.impl;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import uk.ac.ebi.uniprot.domain.citation.Author;
import uk.ac.ebi.uniprot.domain.citation.Book;
import uk.ac.ebi.uniprot.domain.citation.CitationType;
import uk.ac.ebi.uniprot.domain.citation.CitationXrefs;
import uk.ac.ebi.uniprot.domain.citation.PublicationDate;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BookImpl extends AbstractCitationImpl implements Book {
	private final String bookName;
	private final List<Author> editors;
	private final String firstPage;
	private final String lastPage;
	private final String volume;
	private final String publisher;
	private final String address;

	@JsonCreator
	public BookImpl(@JsonProperty("authoringGroup") List<String> authoringGroup,
			@JsonProperty("authors") List<Author> authors, @JsonProperty("citationXrefs") CitationXrefs citationXrefs,
			@JsonProperty("title") String title, @JsonProperty("publicationDate") PublicationDate publicationDate,
			@JsonProperty("bookName") String bookName, @JsonProperty("editors") List<Author> editors,
			@JsonProperty("firstPage") String firstPage, @JsonProperty("lastPage") String lastPage,
			@JsonProperty("volume") String volume, @JsonProperty("publisher") String publisher,
			@JsonProperty("address") String address) {
		super(CitationType.BOOK, authoringGroup, authors, citationXrefs, title, publicationDate);
		this.bookName = bookName;
		this.editors = copyList(editors);
		
		this.firstPage = resetNull(firstPage);
		this.lastPage = resetNull(lastPage);
		this.volume =  resetNull(volume);
		this.publisher =  resetNull(publisher);
		this.address =  resetNull(address);
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
			if (other.volume != null)
				return false;
		} else if (!volume.equals(other.volume))
			return false;
		return true;
	}


}
