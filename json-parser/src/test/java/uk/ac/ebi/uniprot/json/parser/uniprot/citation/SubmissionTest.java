package uk.ac.ebi.uniprot.json.parser.uniprot.citation;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import org.uniprot.core.DBCrossReference;
import org.uniprot.core.builder.DBCrossReferenceBuilder;
import org.uniprot.core.citation.Citation;
import org.uniprot.core.citation.CitationXrefType;
import org.uniprot.core.citation.Submission;
import org.uniprot.core.citation.SubmissionDatabase;
import org.uniprot.core.citation.builder.SubmissionBuilder;

import uk.ac.ebi.uniprot.json.parser.ValidateJson;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
/**
 *
 * @author lgonzales
 */
public class SubmissionTest {

    @Test
    public void testSubmissionSimple() {
        Citation citation =  new SubmissionBuilder().build();
        ValidateJson.verifyJsonRoundTripParser(citation);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(citation);
        assertNotNull(jsonNode.get("citationType"));
        assertEquals("submission", jsonNode.get("citationType").asText());
    }

    @Test
    public void testSubmissionComplete() {
        Citation citation = getSubmission();
        ValidateJson.verifyJsonRoundTripParser(citation);
        ValidateJson.verifyEmptyFields(citation);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(citation);
        CitationUtil.validateCitation(jsonNode);

        assertNotNull(jsonNode.get("citationType"));
        assertEquals("submission", jsonNode.get("citationType").asText());

        assertNotNull(jsonNode.get("submissionDatabase"));
        assertEquals("PIR data bank", jsonNode.get("submissionDatabase").asText());

    }

    public static Submission getSubmission(){
        DBCrossReference<CitationXrefType> xref = new DBCrossReferenceBuilder<CitationXrefType>()
                .databaseType(CitationXrefType.PUBMED)
                .id("somepID1").build();
        return new SubmissionBuilder()
                .submittedToDatabase(SubmissionDatabase.PIR)
                .publicationDate("date value")
                .addAuthorGroup("auth group")
                .addAuthor("author Leo")
                .title("Leo book tittle")
                .citationXrefs(Collections.singletonList(xref))
                .build();
    }
}
