package org.uniprot.core.citation.builder;

import org.uniprot.core.citation.Submission;
import org.uniprot.core.citation.SubmissionDatabase;
import org.uniprot.core.citation.impl.SubmissionImpl;

public final class SubmissionBuilder extends AbstractCitationBuilder<SubmissionBuilder, Submission> {
    private SubmissionDatabase submissionDb;

    public Submission build() {
        return new SubmissionImpl(authoringGroups, authors, xrefs, title, publicationDate, submissionDb);
    }

    @Override
    public SubmissionBuilder from(Submission instance) {
        init(instance);
        return this
                .submittedToDatabase(instance.getSubmissionDatabase());
    }

    public SubmissionBuilder submittedToDatabase(SubmissionDatabase submissionDb) {
        this.submissionDb = submissionDb;
        return this;
    }

    @Override
    protected SubmissionBuilder getThis() {
        return this;
    }
}
