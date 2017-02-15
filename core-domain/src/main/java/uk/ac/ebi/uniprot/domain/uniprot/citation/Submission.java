package uk.ac.ebi.uniprot.domain.uniprot.citation;

public interface Submission extends Citation {
    public SubmissionDatabase getSubmittedToDatabase();
}
