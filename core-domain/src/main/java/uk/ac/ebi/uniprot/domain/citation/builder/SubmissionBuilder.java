package uk.ac.ebi.uniprot.domain.citation.builder;

import uk.ac.ebi.uniprot.domain.citation.Submission;
import uk.ac.ebi.uniprot.domain.citation.SubmissionDatabase;
import uk.ac.ebi.uniprot.domain.citation.impl.SubmissionImpl;

public final class SubmissionBuilder extends AbstractCitationBuilder<Submission> {
    public static SubmissionBuilder newInstance() {
    	
        return new SubmissionBuilder();
    }

    private SubmissionDatabase submissionDb;

    public Submission build() {
        return new SubmissionImpl(authoringGroups, authors,
                xrefs, title, publicationDate, submissionDb);
    }

    public SubmissionBuilder submittedToDatabase(SubmissionDatabase submissionDb) {
        this.submissionDb = submissionDb;
        return this;
    }
    
}
