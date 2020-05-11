package org.uniprot.core.citation.impl;

import javax.annotation.Nonnull;

import org.uniprot.core.citation.Submission;
import org.uniprot.core.citation.SubmissionDatabase;

public final class SubmissionBuilder
        extends AbstractCitationBuilder<SubmissionBuilder, Submission> {
    private SubmissionDatabase submissionDb;

    public @Nonnull Submission build() {
        return new SubmissionImpl(
                authoringGroups,
                authors,
                citationCrossReferences,
                title,
                publicationDate,
                submissionDb);
    }

    public static @Nonnull SubmissionBuilder from(Submission instance) {
        SubmissionBuilder builder = new SubmissionBuilder();
        init(builder, instance);
        return builder.submittedToDatabase(instance.getSubmissionDatabase());
    }

    public @Nonnull SubmissionBuilder submittedToDatabase(SubmissionDatabase submissionDb) {
        this.submissionDb = submissionDb;
        return this;
    }

    @Override
    protected @Nonnull SubmissionBuilder getThis() {
        return this;
    }
}
