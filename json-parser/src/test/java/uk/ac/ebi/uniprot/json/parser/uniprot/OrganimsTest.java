package uk.ac.ebi.uniprot.json.parser.uniprot;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import uk.ac.ebi.uniprot.domain.taxonomy.Organism;
import uk.ac.ebi.uniprot.domain.taxonomy.builder.OrganismBuilder;
import uk.ac.ebi.uniprot.json.parser.ValidateJson;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
/**
 *
 * @author lgonzales
 */
public class OrganimsTest {

    @Test
    public void testOrganismSimple() {
        Organism organism = new OrganismBuilder()
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
        Organism organism = getOrganism();
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

        assertNotNull(jsonNode.get("lineage"));
        assertEquals(1,jsonNode.get("lineage").size());
        assertEquals("lineage 1",jsonNode.get("lineage").get(0).asText());

        assertNotNull(jsonNode.get("evidences"));
        assertEquals(1,jsonNode.get("evidences").size());
        ValidateJson.validateEvidence(jsonNode.get("evidences").get(0),"ECO:0000256","PIRNR","PIRNR001363");
    }

    public static Organism getOrganism() {
        return new OrganismBuilder()
                .taxonId(9606L)
                .scientificName("scientific name")
                .commonName("common name")
                .synonyms(Collections.singletonList("synonyms 1"))
                .lineage(Collections.singletonList("lineage 1"))
                .evidences(CreateUtils.createEvidenceList("ECO:0000256|PIRNR:PIRNR001363"))
                .build();
    }
}
