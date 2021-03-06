package org.uniprot.core.citation.impl;

import static java.util.Collections.emptyList;

import java.util.List;

import org.uniprot.core.CrossReference;
import org.uniprot.core.citation.*;

public class SubmissionImpl extends AbstractCitationImpl implements Submission {
    private static final long serialVersionUID = 8471181839577113515L;
    private final SubmissionDatabase submissionDatabase;

    // no arg constructor for JSON deserialization
    SubmissionImpl() {
        this(emptyList(), emptyList(), emptyList(), null, null, null);
    }

    SubmissionImpl(
            List<String> authoringGroup,
            List<Author> authors,
            List<CrossReference<CitationDatabase>> citationCrossReferences,
            String title,
            PublicationDate publicationDate,
            SubmissionDatabase submissionDatabase) {
        super(
                CitationType.SUBMISSION,
                authoringGroup,
                authors,
                citationCrossReferences,
                title,
                publicationDate);
        this.submissionDatabase = submissionDatabase;
        super.id = generateId();
    }

    @Override
    public SubmissionDatabase getSubmissionDatabase() {
        return submissionDatabase;
    }

    @Override
    public boolean hasSubmissionDatabase() {
        return this.submissionDatabase != null;
    }

    @Override
    protected String getHashInput() {
        String hashInput = super.getHashInput();
        if (hasSubmissionDatabase()) {
            hashInput += SUBMISSION_DATABASE_PREFIX + submissionDatabase.name();
        }
        return hashInput;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result =
                prime * result + ((submissionDatabase == null) ? 0 : submissionDatabase.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        SubmissionImpl other = (SubmissionImpl) obj;
        return submissionDatabase == other.submissionDatabase;
    }
}
