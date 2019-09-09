package org.uniprot.core.citation.builder;

import org.junit.jupiter.api.Test;

import org.uniprot.core.citation.CitationType;
import org.uniprot.core.citation.Submission;
import org.uniprot.core.citation.SubmissionDatabase;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SubmissionBuilderTest extends AbstractCitationBuilderTest {
    @Test
    public void testBuildAll() {
        SubmissionBuilder builder = new SubmissionBuilder();
        buildCitationParameters(builder);

        builder.submittedToDatabase(SubmissionDatabase.PDB);
        Submission citation = builder.build();
        assertEquals(SubmissionDatabase.PDB, citation.getSubmissionDatabase());
        verifyCitation(citation, CitationType.SUBMISSION);
    }

    @Test
    public void testSubmittedToDatabase() {
        SubmissionBuilder builder = new SubmissionBuilder();
        builder.submittedToDatabase(SubmissionDatabase.EMBL_GENBANK_DDBJ);
        Submission citation = builder.build();
        assertEquals(CitationType.SUBMISSION, citation.getCitationType());
        assertEquals(SubmissionDatabase.EMBL_GENBANK_DDBJ, citation.getSubmissionDatabase());
    }
}
