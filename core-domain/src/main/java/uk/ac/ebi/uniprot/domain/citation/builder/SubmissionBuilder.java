package uk.ac.ebi.uniprot.domain.citation.builder;

import uk.ac.ebi.uniprot.domain.citation.Submission;
import uk.ac.ebi.uniprot.domain.citation.SubmissionDatabase;
import uk.ac.ebi.uniprot.domain.citation.impl.SubmissionImpl;

public final class SubmissionBuilder extends AbstractCitationBuilder<SubmissionBuilder, Submission> {
    private SubmissionDatabase submissionDb;

    public Submission build() {
        return new SubmissionImpl(this);
    }

    @Override
    public SubmissionBuilder from(Submission instance) {
        SubmissionBuilder builder = new SubmissionBuilder();
        init(builder, instance);
        return builder.submittedToDatabase(instance.getSubmissionDatabase());
    }

    public SubmissionBuilder submittedToDatabase(SubmissionDatabase submissionDb) {
        this.submissionDb = submissionDb;
        return this;
    }

    public SubmissionDatabase getSubmissionDb() {
        return submissionDb;
    }

    @Override
    protected SubmissionBuilder getThis() {
        return this;
    }
}
