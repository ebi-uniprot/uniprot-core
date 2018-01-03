package uk.ac.ebi.uniprot.domain.citation.builder;

import uk.ac.ebi.uniprot.domain.citation.Author;
import uk.ac.ebi.uniprot.domain.citation.CitationType;
import uk.ac.ebi.uniprot.domain.citation.CitationXrefs;
import uk.ac.ebi.uniprot.domain.citation.PublicationDate;
import uk.ac.ebi.uniprot.domain.citation.Submission;
import uk.ac.ebi.uniprot.domain.citation.SubmissionDatabase;

import java.util.List;

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
    
    class SubmissionImpl extends AbstractCitationImpl implements Submission {
        private final SubmissionDatabase submissionDb;
        public SubmissionImpl(List<String> authoringGroups, 
                List<Author> authors, CitationXrefs xrefs,
            String title, PublicationDate publicationDate,
            SubmissionDatabase submissionDb) {
            super(CitationType.SUBMISSION, authoringGroups, authors, xrefs, title, publicationDate);
            this.submissionDb = submissionDb;
        }

        @Override
        public SubmissionDatabase getSubmittedToDatabase() {
           return submissionDb;
        }

    }
       
}
