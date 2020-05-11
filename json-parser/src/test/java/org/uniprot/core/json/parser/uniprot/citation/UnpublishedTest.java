package org.uniprot.core.json.parser.uniprot.citation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.fasterxml.jackson.databind.JsonNode;

import org.junit.jupiter.api.Test;
import org.uniprot.core.citation.Citation;
import org.uniprot.core.citation.Unpublished;
import org.uniprot.core.citation.impl.UnpublishedBuilder;
import org.uniprot.core.json.parser.ValidateJson;

/** @author lgonzales */
public class UnpublishedTest {

    @Test
    void testUnpublishedSimple() {
        Citation citation = new UnpublishedBuilder().build();
        ValidateJson.verifyJsonRoundTripParser(citation);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(citation);
        assertNotNull(jsonNode.get("citationType"));
        assertEquals("unpublished observations", jsonNode.get("citationType").asText());
    }

    @Test
    void testUnpublishedComplete() {
        Citation citation = getUnpublished();
        ValidateJson.verifyJsonRoundTripParser(citation);
        ValidateJson.verifyEmptyFields(citation);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(citation);
        CitationUtil.validateCitation(jsonNode);

        assertNotNull(jsonNode.get("citationType"));
        assertEquals("unpublished observations", jsonNode.get("citationType").asText());
    }

    public static Unpublished getUnpublished() {
        UnpublishedBuilder builder = new UnpublishedBuilder();
        CitationUtil.populateBasicCitation(builder);
        return builder.build();
    }
}
