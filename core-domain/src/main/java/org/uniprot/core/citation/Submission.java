package org.uniprot.core.citation;

public interface Submission extends Citation {
    SubmissionDatabase getSubmissionDatabase();

    boolean hasSubmissionDatabase();
}
