package org.uniprot.core.json.parser.uniprot.citation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.uniprot.core.citation.Citation;
import org.uniprot.core.citation.Submission;
import org.uniprot.core.citation.SubmissionDatabase;
import org.uniprot.core.citation.builder.SubmissionBuilder;
import org.uniprot.core.json.parser.ValidateJson;

import com.fasterxml.jackson.databind.JsonNode;

/** @author lgonzales */
public class SubmissionTest {

    @Test
    void testSubmissionSimple() {
        Citation citation = new SubmissionBuilder().build();
        ValidateJson.verifyJsonRoundTripParser(citation);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(citation);
        assertNotNull(jsonNode.get("citationType"));
        assertEquals("submission", jsonNode.get("citationType").asText());
    }

    @Test
    void testSubmissionComplete() {
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

    public static Submission getSubmission() {
        SubmissionBuilder builder = new SubmissionBuilder();
        CitationUtil.populateBasicCitation(builder);
        return builder.submittedToDatabase(SubmissionDatabase.PIR).build();
    }
}
