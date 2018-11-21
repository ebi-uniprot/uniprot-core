package uk.ac.ebi.uniprot.domain.citation.impl;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import uk.ac.ebi.uniprot.domain.citation.Author;
import uk.ac.ebi.uniprot.domain.citation.CitationType;
import uk.ac.ebi.uniprot.domain.citation.CitationXrefs;
import uk.ac.ebi.uniprot.domain.citation.PublicationDate;
import uk.ac.ebi.uniprot.domain.citation.Submission;
import uk.ac.ebi.uniprot.domain.citation.SubmissionDatabase;
@JsonInclude(JsonInclude.Include.NON_EMPTY)  
public class SubmissionImpl extends AbstractCitationImpl implements Submission {
    private final SubmissionDatabase submissionDatabase;
    @JsonCreator
    public SubmissionImpl(@JsonProperty("authoringGroup") List<String> authoringGroup,
			@JsonProperty("authors") List<Author> authors, @JsonProperty("citationXrefs") CitationXrefs citationXrefs,
			@JsonProperty("title") String title, @JsonProperty("publicationDate") PublicationDate publicationDate,
			@JsonProperty("submissionDatabase") 
        SubmissionDatabase submissionDatabase) {
        super(CitationType.SUBMISSION, authoringGroup, authors, citationXrefs, title, publicationDate);
        this.submissionDatabase = submissionDatabase;
    }

    @Override
    public SubmissionDatabase getSubmissionDatabase() {
       return submissionDatabase;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((submissionDatabase == null) ? 0 : submissionDatabase.hashCode());
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
		SubmissionImpl other = (SubmissionImpl) obj;
		if (submissionDatabase != other.submissionDatabase)
			return false;
		return true;
	}
    
    

}
