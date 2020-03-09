package org.uniprot.core.json.parser.uniprot.citation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.uniprot.core.citation.Citation;
import org.uniprot.core.citation.JournalArticle;
import org.uniprot.core.citation.impl.JournalArticleBuilder;
import org.uniprot.core.json.parser.ValidateJson;

import com.fasterxml.jackson.databind.JsonNode;

/** @author lgonzales */
public class JournalArticleTest {

    @Test
    void testJournalArticleSimple() {
        Citation citation = new JournalArticleBuilder().build();
        ValidateJson.verifyJsonRoundTripParser(citation);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(citation);
        assertNotNull(jsonNode.get("citationType"));
        assertEquals("journal article", jsonNode.get("citationType").asText());
    }

    @Test
    void testJournalArticleComplete() {
        Citation citation = getJournalArticle();
        ValidateJson.verifyJsonRoundTripParser(citation);
        ValidateJson.verifyEmptyFields(citation);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(citation);
        CitationUtil.validateCitation(jsonNode);

        assertNotNull(jsonNode.get("citationType"));
        assertEquals("journal article", jsonNode.get("citationType").asText());

        assertNotNull(jsonNode.get("journal"));
        assertEquals("journal name", jsonNode.get("journal").asText());

        assertNotNull(jsonNode.get("firstPage"));
        assertEquals("first page", jsonNode.get("firstPage").asText());

        assertNotNull(jsonNode.get("lastPage"));
        assertEquals("last page", jsonNode.get("lastPage").asText());

        assertNotNull(jsonNode.get("volume"));
        assertEquals("volume value", jsonNode.get("volume").asText());
    }

    public static JournalArticle getJournalArticle() {
        JournalArticleBuilder builder = new JournalArticleBuilder();
        CitationUtil.populateBasicCitation(builder);
        return builder.journalName("journal name")
                .firstPage("first page")
                .lastPage("last page")
                .volume("volume value")
                .build();
    }
}
