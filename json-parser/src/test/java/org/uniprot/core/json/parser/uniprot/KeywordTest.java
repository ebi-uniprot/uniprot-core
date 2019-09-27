package org.uniprot.core.json.parser.uniprot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.keyword.KeywordCategory;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.uniprot.Keyword;
import org.uniprot.core.uniprot.builder.KeywordBuilder;

import com.fasterxml.jackson.databind.JsonNode;

/** @author lgonzales */
public class KeywordTest {

    @Test
    void testKeywordSimple() {
        Keyword keyword = new KeywordBuilder().build();
        ValidateJson.verifyJsonRoundTripParser(keyword);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(keyword);

        assertNotNull(jsonNode.get("id"));
        assertEquals("KW-00000", jsonNode.get("id").asText());
    }

    @Test
    void testKeywordComplete() {
        Keyword keyword = getKeyword();
        ValidateJson.verifyJsonRoundTripParser(keyword);
        ValidateJson.verifyEmptyFields(keyword);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(keyword);

        assertNotNull(jsonNode.get("value"));
        assertEquals("keyword value", jsonNode.get("value").asText());

        assertNotNull(jsonNode.get("id"));
        assertEquals("KW-11111", jsonNode.get("id").asText());

        assertNotNull(jsonNode.get("evidences"));
        assertEquals(1, jsonNode.get("evidences").size());
        ValidateJson.validateEvidence(
                jsonNode.get("evidences").get(0), "ECO:0000255", "PROSITE-ProRule", "PRU10025");
    }

    public static Keyword getKeyword() {
        return new KeywordBuilder()
                .id("KW-11111")
                .value("keyword value")
                .category(KeywordCategory.DOMAIN)
                .evidences(CreateUtils.createEvidenceList("ECO:0000255|PROSITE-ProRule:PRU10025"))
                .build();
    }
}
