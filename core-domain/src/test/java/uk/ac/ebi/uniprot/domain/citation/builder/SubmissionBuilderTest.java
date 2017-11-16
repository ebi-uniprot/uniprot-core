package uk.ac.ebi.uniprot.domain.citation.builder;

import uk.ac.ebi.uniprot.domain.citation.CitationType;
import uk.ac.ebi.uniprot.domain.citation.Submission;
import uk.ac.ebi.uniprot.domain.citation.SubmissionDatabase;

import org.junit.Test;

import static org.junit.Assert.*;

public class SubmissionBuilderTest extends AbstractCitationBuilderTest{

    @Test
    public void testBuildAll() {
        SubmissionBuilder builder  = SubmissionBuilder.newInstance();
        builderCitationParamters(builder);
        
        builder.submittedToDatabase(SubmissionDatabase.PDB);
        Submission citation = builder.build();
        assertEquals(SubmissionDatabase.PDB, citation.getSubmittedToDatabase());
        verifyCitation(citation, CitationType.SUBMISSION);   
    }

    @Test
    public void testSubmittedToDatabase() {
        SubmissionBuilder builder  = SubmissionBuilder.newInstance();
        builder.submittedToDatabase(SubmissionDatabase.EMBL_GENBANK_DDBJ);
        Submission citation = builder.build();
        assertEquals(CitationType.SUBMISSION, citation.getCitationType());
        assertEquals(SubmissionDatabase.EMBL_GENBANK_DDBJ, citation.getSubmittedToDatabase());
    }

}
