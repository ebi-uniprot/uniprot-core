package uk.ac.ebi.uniprot.domain.citation.impl;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import uk.ac.ebi.uniprot.domain.citation.Author;
import uk.ac.ebi.uniprot.domain.citation.CitationType;
import uk.ac.ebi.uniprot.domain.citation.CitationXrefs;
import uk.ac.ebi.uniprot.domain.citation.PublicationDate;
import uk.ac.ebi.uniprot.domain.citation.Thesis;
@JsonInclude(JsonInclude.Include.NON_EMPTY)  
public class ThesisImpl extends AbstractCitationImpl implements Thesis {
    private final String institute;
    private final String address;
    @JsonCreator
    public ThesisImpl(
    		@JsonProperty("authoringGroup") List<String> authoringGroup,
			@JsonProperty("authors") List<Author> authors, @JsonProperty("citationXrefs") CitationXrefs citationXrefs,
			@JsonProperty("title") String title, @JsonProperty("publicationDate") PublicationDate publicationDate,
			 @JsonProperty("institute") String institute,  @JsonProperty("address") String address) {
        super(CitationType.THESIS, authoringGroup, authors, citationXrefs, title, publicationDate);
        this.institute = this.resetNull(institute);
        this.address = this.resetNull(address);
    }

    @Override
    public String getInstitute() {
        return institute;
    }

    @Override
    public String getAddress() {
        return address;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((institute == null) ? 0 : institute.hashCode());
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
		ThesisImpl other = (ThesisImpl) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (institute == null) {
			if (other.institute != null)
				return false;
		} else if (!institute.equals(other.institute))
			return false;
		return true;
	}


}
