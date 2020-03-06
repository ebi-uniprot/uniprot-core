package org.uniprot.core.json.parser.uniprot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.uniprot.taxonomy.Organism;
import org.uniprot.core.uniprot.taxonomy.impl.OrganismBuilder;

import com.fasterxml.jackson.databind.JsonNode;

/** @author lgonzales */
public class OrganimsTest {

    @Test
    void testOrganismSimple() {
        Organism organism =
                new OrganismBuilder().taxonId(9606L).scientificName("scientific name").build();

        ValidateJson.verifyJsonRoundTripParser(organism);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(organism);
        assertEquals(9606, jsonNode.get("taxonId").asInt());
        assertNotNull(jsonNode.get("scientificName"));
        assertEquals("scientific name", jsonNode.get("scientificName").asText());
    }

    @Test
    void testOrganismComplete() {
        Organism organism = getOrganism();
        ValidateJson.verifyJsonRoundTripParser(organism);
        ValidateJson.verifyEmptyFields(organism);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(organism);
        assertEquals(9606, jsonNode.get("taxonId").asInt());
        assertNotNull(jsonNode.get("scientificName"));
        assertEquals("scientific name", jsonNode.get("scientificName").asText());
        assertNotNull(jsonNode.get("commonName"));
        assertEquals("common name", jsonNode.get("commonName").asText());
        assertNotNull(jsonNode.get("synonyms"));
        assertEquals(1, jsonNode.get("synonyms").size());
        assertEquals("synonyms 1", jsonNode.get("synonyms").get(0).asText());

        assertNotNull(jsonNode.get("lineage"));
        assertEquals(1, jsonNode.get("lineage").size());
        assertEquals("lineage 1", jsonNode.get("lineage").get(0).asText());

        assertNotNull(jsonNode.get("evidences"));
        assertEquals(1, jsonNode.get("evidences").size());
        ValidateJson.validateEvidence(
                jsonNode.get("evidences").get(0), "ECO:0000256", "PIRNR", "PIRNR001363");
    }

    public static Organism getOrganism() {
        return new OrganismBuilder()
                .taxonId(9606L)
                .scientificName("scientific name")
                .commonName("common name")
                .synonymsSet(Collections.singletonList("synonyms 1"))
                .lineagesSet(Collections.singletonList("lineage 1"))
                .evidencesSet(CreateUtils.createEvidenceList("ECO:0000256|PIRNR:PIRNR001363"))
                .build();
    }
}
