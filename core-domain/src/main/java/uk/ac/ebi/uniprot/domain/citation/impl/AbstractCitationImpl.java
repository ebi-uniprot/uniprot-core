package uk.ac.ebi.uniprot.domain.citation.impl;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import uk.ac.ebi.uniprot.domain.citation.Author;
import uk.ac.ebi.uniprot.domain.citation.Citation;
import uk.ac.ebi.uniprot.domain.citation.CitationType;
import uk.ac.ebi.uniprot.domain.citation.CitationXrefs;
import uk.ac.ebi.uniprot.domain.citation.PublicationDate;
import uk.ac.ebi.uniprot.domain.util.Utils;
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public abstract class AbstractCitationImpl implements Citation {
	private final CitationType citationType;
	private final List<String> authoringGroup;
	private final List<Author> authors;
	private final CitationXrefs citationXrefs;
	private final String title;
	private final PublicationDate publicationDate;
	@JsonCreator
	public AbstractCitationImpl(
			@JsonProperty("citationType")  CitationType citationType, 
			@JsonProperty("authoringGroup")  List<String> authoringGroup, 
			@JsonProperty("authors")  List<Author> authors,
			@JsonProperty("citationXrefs")  CitationXrefs citationXrefs, 
			@JsonProperty("title")  String title, 
			@JsonProperty("publicationDate")  PublicationDate publicationDate) {
		this.citationType = citationType;
		this.authoringGroup =Utils.unmodifierList(authoringGroup);

		this.authors =Utils.unmodifierList(authors);
		
		this.citationXrefs = citationXrefs;
		this.title = Utils.resetNull(title);
		this.publicationDate = publicationDate;
	}

	@Override
	public CitationXrefs getCitationXrefs() {
		return citationXrefs;
	}

	@Override
	public boolean hasCitationXrefs() {
		return citationXrefs != null;
	}

	@Override
	public List<String> getAuthoringGroup() {
		return this.authoringGroup;
	}

	@Override
	public List<Author> getAuthors() {
		return this.authors;
	}

	@Override
	public CitationType getCitationType() {
		return citationType;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public boolean hasTitle() {
		return ((title != null) && !title.isEmpty());
	}

	@Override
	public PublicationDate getPublicationDate() {
		return publicationDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((authoringGroup == null) ? 0 : authoringGroup.hashCode());
		result = prime * result + ((authors == null) ? 0 : authors.hashCode());
		result = prime * result + ((citationType == null) ? 0 : citationType.hashCode());
		result = prime * result + ((citationXrefs == null) ? 0 : citationXrefs.hashCode());
		result = prime * result + ((publicationDate == null) ? 0 : publicationDate.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractCitationImpl other = (AbstractCitationImpl) obj;
		if (authoringGroup == null) {
			if (other.authoringGroup != null)
				return false;
		} else if (!authoringGroup.equals(other.authoringGroup))
			return false;
		if (authors == null) {
			if (other.authors != null)
				return false;
		} else if (!authors.equals(other.authors))
			return false;
		if (citationType != other.citationType)
			return false;
		if (citationXrefs == null) {
			if (other.citationXrefs != null)
				return false;
		} else if (!citationXrefs.equals(other.citationXrefs))
			return false;
		if (publicationDate == null) {
			if (other.publicationDate != null)
				return false;
		} else if (!publicationDate.equals(other.publicationDate))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}


}
