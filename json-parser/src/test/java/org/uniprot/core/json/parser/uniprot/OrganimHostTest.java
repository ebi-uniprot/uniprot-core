package org.uniprot.core.json.parser.uniprot;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.uniprot.taxonomy.OrganismHost;
import org.uniprot.core.uniprot.taxonomy.builder.OrganismHostBuilder;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class OrganimHostTest {


    @Test
    void testOrganismSimple() {
        OrganismHost organism = new OrganismHostBuilder()
                .taxonId(9606L)
                .scientificName("scientific name")
                .build();

        ValidateJson.verifyJsonRoundTripParser(organism);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(organism);
        assertEquals(9606,jsonNode.get("taxonId").asInt());
        assertNotNull(jsonNode.get("scientificName"));
        assertEquals("scientific name",jsonNode.get("scientificName").asText());
    }

    @Test
    void testOrganismComplete() {
        OrganismHost organism = getOrganismHost();
        ValidateJson.verifyJsonRoundTripParser(organism);
        ValidateJson.verifyEmptyFields(organism);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(organism);
        assertEquals(9606,jsonNode.get("taxonId").asInt());
        assertNotNull(jsonNode.get("scientificName"));
        assertEquals("scientific name",jsonNode.get("scientificName").asText());
        assertNotNull(jsonNode.get("commonName"));
        assertEquals("common name",jsonNode.get("commonName").asText());
        assertNotNull(jsonNode.get("synonyms"));
        assertEquals(1,jsonNode.get("synonyms").size());
        assertEquals("synonyms 1",jsonNode.get("synonyms").get(0).asText());
    }

    public static OrganismHost getOrganismHost() {
        return new OrganismHostBuilder()
                .taxonId(9606L)
                .scientificName("scientific name")
                .commonName("common name")
                .synonyms(Collections.singletonList("synonyms 1"))
                .build();
    }
}
