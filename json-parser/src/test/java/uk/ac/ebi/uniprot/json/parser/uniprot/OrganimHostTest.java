package uk.ac.ebi.uniprot.json.parser.uniprot;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import uk.ac.ebi.uniprot.domain.uniprot.taxonomy.OrganismHost;
import uk.ac.ebi.uniprot.domain.uniprot.taxonomy.builder.OrganismHostBuilder;
import uk.ac.ebi.uniprot.json.parser.ValidateJson;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class OrganimHostTest {


    @Test
    public void testOrganismSimple() {
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
    public void testOrganismComplete() {
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
