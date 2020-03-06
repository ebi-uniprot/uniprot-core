package org.uniprot.core.citation.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.uniprot.core.citation.CitationType;
import org.uniprot.core.citation.Submission;
import org.uniprot.core.citation.SubmissionDatabase;

class SubmissionBuilderTest extends AbstractCitationBuilderTest {
    @Test
    void testBuildAll() {
        SubmissionBuilder builder = new SubmissionBuilder();
        buildCitationParameters(builder);

        builder.submittedToDatabase(SubmissionDatabase.PDB);
        Submission citation = builder.build();
        assertEquals(SubmissionDatabase.PDB, citation.getSubmissionDatabase());
        verifyCitation(citation, CitationType.SUBMISSION);
    }

    @Test
    void testSubmittedToDatabase() {
        SubmissionBuilder builder = new SubmissionBuilder();
        builder.submittedToDatabase(SubmissionDatabase.EMBL_GENBANK_DDBJ);
        Submission citation = builder.build();
        assertEquals(CitationType.SUBMISSION, citation.getCitationType());
        assertEquals(SubmissionDatabase.EMBL_GENBANK_DDBJ, citation.getSubmissionDatabase());
    }
}
