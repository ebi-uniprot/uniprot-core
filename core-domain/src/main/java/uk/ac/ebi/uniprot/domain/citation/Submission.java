package uk.ac.ebi.uniprot.domain.citation;

public interface Submission extends Citation {
    SubmissionDatabase getSubmissionDatabase();

    boolean hasSubmissionDatabase();
}
