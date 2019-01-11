package uk.ac.ebi.uniprot.domain.citation2.impl;

import uk.ac.ebi.uniprot.domain.citation2.CitationType;
import uk.ac.ebi.uniprot.domain.citation2.Submission;
import uk.ac.ebi.uniprot.domain.citation2.SubmissionDatabase;
import uk.ac.ebi.uniprot.domain.citation2.builder.SubmissionBuilder;

import java.util.Collections;

public class SubmissionImpl extends AbstractCitationImpl implements Submission {

    private SubmissionDatabase submissionDatabase;

    private SubmissionImpl() {
        super(CitationType.SUBMISSION, Collections.emptyList(), Collections.emptyList(),
              null, null, null);
    }

    public SubmissionImpl(SubmissionBuilder builder) {
        super(CitationType.SUBMISSION, builder.getAuthoringGroups(), builder.getAuthors(), builder.getXrefs(),
              builder.getTitle(), builder.getPublicationDate());
        this.submissionDatabase = builder.getSubmissionDb();
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
