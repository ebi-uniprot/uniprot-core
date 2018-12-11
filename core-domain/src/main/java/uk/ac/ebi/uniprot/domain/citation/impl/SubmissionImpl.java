package uk.ac.ebi.uniprot.domain.citation.impl;

import uk.ac.ebi.uniprot.domain.citation.*;

import java.util.Collections;
import java.util.List;

public class SubmissionImpl extends AbstractCitationImpl implements Submission {

	private  SubmissionDatabase submissionDatabase;

    private SubmissionImpl(){
		super(CitationType.SUBMISSION, Collections.emptyList(), Collections.emptyList(),
				null, null, null);
	}
    public SubmissionImpl(List<String> authoringGroup, List<Author> authors, CitationXrefs citationXrefs,
			String title, PublicationDate publicationDate, SubmissionDatabase submissionDatabase) {
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
