package uk.ac.ebi.uniprot.domain.citation.impl;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import uk.ac.ebi.uniprot.domain.citation.Author;
import uk.ac.ebi.uniprot.domain.citation.CitationType;
import uk.ac.ebi.uniprot.domain.citation.CitationXrefs;
import uk.ac.ebi.uniprot.domain.citation.Patent;
import uk.ac.ebi.uniprot.domain.citation.PublicationDate;
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PatentImpl extends AbstractCitationImpl implements Patent {
    private final String patentNumber;
	@JsonCreator
    public PatentImpl(
    		@JsonProperty("authoringGroup") List<String> authoringGroup,
			@JsonProperty("authors") List<Author> authors, @JsonProperty("citationXrefs") CitationXrefs citationXrefs,
			@JsonProperty("title") String title, @JsonProperty("publicationDate") PublicationDate publicationDate,
			@JsonProperty("patentNumber") 
    		String patentNumber) {
        super(CitationType.PATENT, authoringGroup, authors, citationXrefs, title, publicationDate);
        this.patentNumber = resetNull( patentNumber);
    }

    @Override
    public String getPatentNumber() {
        return patentNumber;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((patentNumber == null) ? 0 : patentNumber.hashCode());
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
		PatentImpl other = (PatentImpl) obj;
		if (patentNumber == null) {
			if (other.patentNumber != null)
				return false;
		} else if (!patentNumber.equals(other.patentNumber))
			return false;
		return true;
	}
    

}
