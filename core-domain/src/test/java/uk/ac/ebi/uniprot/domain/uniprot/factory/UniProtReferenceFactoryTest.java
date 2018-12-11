package uk.ac.ebi.uniprot.domain.uniprot.factory;

import org.junit.Test;
import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.citation.*;
import uk.ac.ebi.uniprot.domain.uniprot.ReferenceComment;
import uk.ac.ebi.uniprot.domain.uniprot.ReferenceCommentType;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtReference;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtReferences;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UniProtReferenceFactoryTest {

    @Test
    public void testCreateUniProtReferences() {
        List<UniProtReference<? extends Citation>> references = new ArrayList<>();
        List<Evidence> evidences = createEvidences();
        List<String>  referencePositions =
                Arrays.asList("Some position");
        Submission submission =
                UniProtReferenceFactory.INSTANCE.getCitationFactory()
                .createSubmissionBuilder()
                .submittedToDatabase(SubmissionDatabase.EMBL_GENBANK_DDBJ)
                .build();
        UniProtReference<Submission> subReference =UniProtReferenceFactory.INSTANCE.createUniProtReference(submission,
                referencePositions, null, evidences);
    
        JournalArticle ja =
                UniProtReferenceFactory.INSTANCE.getCitationFactory()
                .createJournalArticleBuilder()
                .journalName("some name")
                .title("some title")
                .build();
        
        UniProtReference<JournalArticle> jaReference =UniProtReferenceFactory.INSTANCE.createUniProtReference(ja,
                referencePositions, null, evidences);
        references.add(subReference);
        references.add(jaReference);
        UniProtReferences uniReferences= UniProtReferenceFactory.INSTANCE.createUniProtReferences(references);
        assertNotNull(uniReferences);
        assertEquals(2, uniReferences.getReferences().size());
        assertEquals(references, uniReferences.getReferences());
        assertEquals(1, uniReferences.getReferencesByType(CitationType.SUBMISSION).size());
        assertEquals(subReference, uniReferences.getReferencesByType(CitationType.SUBMISSION).get(0));
        TestHelper.verifyJson(uniReferences);
       
    }

    @Test
    public void testCreateUniProtReference() {
        Submission submission =
                UniProtReferenceFactory.INSTANCE.getCitationFactory()
                .createSubmissionBuilder()
                .submittedToDatabase(SubmissionDatabase.EMBL_GENBANK_DDBJ)
                .build();
        List<String>  referencePositions =
                Arrays.asList("Some position");
        List<Evidence> evidences = createEvidences();
        List<ReferenceComment> refComments = new ArrayList<>();
        refComments.add(UniProtReferenceFactory.INSTANCE.createReferenceComment(ReferenceCommentType.STRAIN, "S1", evidences));
        refComments.add(UniProtReferenceFactory.INSTANCE.createReferenceComment(ReferenceCommentType.TISSUE, "S11", evidences));
        UniProtReference<Submission> uniReference =UniProtReferenceFactory.INSTANCE.createUniProtReference(submission,
                referencePositions, refComments, evidences);
        assertEquals(submission, uniReference.getCitation());
        assertEquals(1, uniReference.getTypedReferenceComments(ReferenceCommentType.STRAIN).size());
        assertEquals(1, uniReference.getReferencePositions().size());
        
        TestHelper.verifyJson(uniReference);
    }

    @Test
    public void testCreateReferenceComment() {
        List<Evidence> evidences = createEvidences();
        ReferenceCommentType type = ReferenceCommentType.STRAIN;
        String value ="S2";
        ReferenceComment refComment =UniProtReferenceFactory.INSTANCE.createReferenceComment(type, value, evidences);
        assertEquals(type, refComment.getType());
        assertEquals(value, refComment.getValue());
        assertEquals(evidences, refComment.getEvidences());
        TestHelper.verifyJson(refComment);
    }

    @Test
    public void testGetCitationFactory() {
       CitationFactory citationFactory= UniProtReferenceFactory.INSTANCE.getCitationFactory();
       assertNotNull(citationFactory);
    }
    private List<Evidence> createEvidences() {
        List<Evidence> evidences = new ArrayList<>();
        evidences.add(UniProtFactory.INSTANCE.createEvidence("ECO:0000255|PROSITE-ProRule:PRU10028"));
        evidences.add(UniProtFactory.INSTANCE.createEvidence("ECO:0000256|PIRNR:PIRNR001361"));
        return evidences;
    }
}
