package org.uniprot.core.json.parser.uniprot.citation;

import com.fasterxml.jackson.databind.JsonNode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
/**
 *
 * @author lgonzales
 */
public class CitationUtil {

    static void validateCitation(JsonNode jsonNode){
        assertNotNull(jsonNode.get("publicationDate"));
        assertEquals("date value", jsonNode.get("publicationDate").asText());

        assertNotNull(jsonNode.get("authoringGroup"));
        assertEquals(1,jsonNode.get("authoringGroup").size());
        assertEquals("auth group",jsonNode.get("authoringGroup").get(0).asText());

        assertNotNull(jsonNode.get("authors"));
        assertEquals(1,jsonNode.get("authors").size());
        assertEquals("author Leo",jsonNode.get("authors").get(0).asText());

        assertNotNull(jsonNode.get("title"));
        assertEquals("Leo book tittle", jsonNode.get("title").asText());

        assertNotNull(jsonNode.get("citationXrefs"));
        assertEquals(1,jsonNode.get("citationXrefs").size());
        JsonNode citationXrefs = jsonNode.get("citationXrefs").get(0);

        assertNotNull(citationXrefs.get("databaseType"));
        assertEquals("PubMed", citationXrefs.get("databaseType").asText());

        assertNotNull(citationXrefs.get("id"));
        assertEquals("somepID1", citationXrefs.get("id").asText());
    }
}
