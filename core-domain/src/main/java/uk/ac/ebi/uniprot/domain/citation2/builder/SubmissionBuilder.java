package uk.ac.ebi.uniprot.domain.citation2.builder;

import uk.ac.ebi.uniprot.domain.citation2.Submission;
import uk.ac.ebi.uniprot.domain.citation2.SubmissionDatabase;
import uk.ac.ebi.uniprot.domain.citation2.impl.SubmissionImpl;

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
}
