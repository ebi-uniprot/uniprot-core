package org.uniprot.core.json.parser.uniprot.citation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.fasterxml.jackson.databind.JsonNode;

import org.junit.jupiter.api.Test;
import org.uniprot.core.citation.Citation;
import org.uniprot.core.citation.ElectronicArticle;
import org.uniprot.core.citation.impl.ElectronicArticleBuilder;
import org.uniprot.core.json.parser.ValidateJson;

/** @author lgonzales */
public class ElectronicArticleTest {

    @Test
    void testElectronicArticleSimple() {
        Citation citation = new ElectronicArticleBuilder().build();
        ValidateJson.verifyJsonRoundTripParser(citation);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(citation);
        assertNotNull(jsonNode.get("citationType"));
        assertEquals("online journal article", jsonNode.get("citationType").asText());
    }

    @Test
    void testElectronicArticleComplete() {
        Citation citation = getElectronicArticle();
        ValidateJson.verifyJsonRoundTripParser(citation);
        ValidateJson.verifyEmptyFields(citation);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(citation);
        CitationUtil.validateCitation(jsonNode);

        assertNotNull(jsonNode.get("citationType"));
        assertEquals("online journal article", jsonNode.get("citationType").asText());

        assertNotNull(jsonNode.get("journal"));
        assertEquals("journal name", jsonNode.get("journal").asText());

        assertNotNull(jsonNode.get("locator"));
        assertEquals("locator value", jsonNode.get("locator").asText());
    }

    public static ElectronicArticle getElectronicArticle() {
        ElectronicArticleBuilder builder = new ElectronicArticleBuilder();
        CitationUtil.populateBasicCitation(builder);
        return builder.journalName("journal name").locator("locator value").build();
    }
}
