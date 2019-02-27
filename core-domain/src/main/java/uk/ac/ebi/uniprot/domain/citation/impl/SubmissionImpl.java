package uk.ac.ebi.uniprot.domain.citation.impl;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.citation.*;

import java.util.List;

import static java.util.Collections.emptyList;

public class SubmissionImpl extends AbstractCitationImpl implements Submission {
    private static final long serialVersionUID = 7406371948424303592L;
    private SubmissionDatabase submissionDatabase;

    private SubmissionImpl() {
        this(emptyList(), emptyList(), emptyList(), null, null, null);
    }

    public SubmissionImpl(List<String> authoringGroup, List<Author> authors, List<DBCrossReference<CitationXrefType>> citationXrefs,
                          String title, PublicationDate publicationDate, SubmissionDatabase submissionDatabase) {
        super(CitationType.SUBMISSION, authoringGroup, authors, citationXrefs, title, publicationDate);
        this.submissionDatabase = submissionDatabase;
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
        return submissionDatabase == other.submissionDatabase;
    }


}
