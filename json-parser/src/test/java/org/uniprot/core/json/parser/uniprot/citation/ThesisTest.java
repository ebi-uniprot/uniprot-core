package org.uniprot.core.json.parser.uniprot.citation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.uniprot.core.citation.Citation;
import org.uniprot.core.citation.Thesis;
import org.uniprot.core.citation.impl.ThesisBuilder;
import org.uniprot.core.json.parser.ValidateJson;

import com.fasterxml.jackson.databind.JsonNode;

/** @author lgonzales */
public class ThesisTest {

    @Test
    void testThesisSimple() {
        Citation citation = new ThesisBuilder().build();
        ValidateJson.verifyJsonRoundTripParser(citation);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(citation);
        assertNotNull(jsonNode.get("citationType"));
        assertEquals("thesis", jsonNode.get("citationType").asText());
    }

    @Test
    void testThesisComplete() {
        Citation citation = getThesis();
        ValidateJson.verifyJsonRoundTripParser(citation);
        ValidateJson.verifyEmptyFields(citation);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(citation);
        CitationUtil.validateCitation(jsonNode);

        assertNotNull(jsonNode.get("citationType"));
        assertEquals("thesis", jsonNode.get("citationType").asText());

        assertNotNull(jsonNode.get("institute"));
        assertEquals("thesis institute", jsonNode.get("institute").asText());

        assertNotNull(jsonNode.get("address"));
        assertEquals("thesis address", jsonNode.get("address").asText());
    }

    public static Thesis getThesis() {
        ThesisBuilder builder = new ThesisBuilder();
        CitationUtil.populateBasicCitation(builder);
        return builder.address("thesis address").institute("thesis institute").build();
    }
}
