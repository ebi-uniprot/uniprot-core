package uk.ac.ebi.uniprot.json.parser.uniprot;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import uk.ac.ebi.uniprot.domain.uniprot.GeneEncodingType;
import uk.ac.ebi.uniprot.domain.uniprot.GeneLocation;
import uk.ac.ebi.uniprot.domain.uniprot.builder.GeneLocationBuilder;
import uk.ac.ebi.uniprot.json.parser.ValidateJson;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
/**
 *
 * @author lgonzales
 */
public class GeneLocationTest {


    @Test
    public void testGeneLocationSimple() {
        GeneLocation geneLocation = new GeneLocationBuilder().build();
        ValidateJson.verifyJsonRoundTripParser(geneLocation);
    }

    @Test
    public void testGeneLocationComplete() {
        GeneLocation geneLocation = getGeneLocation();
        ValidateJson.verifyJsonRoundTripParser(geneLocation);
        ValidateJson.verifyEmptyFields(geneLocation);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(geneLocation);

        assertNotNull(jsonNode.get("value"));
        assertEquals("geneLocation value",jsonNode.get("value").asText());

        assertNotNull(jsonNode.get("geneEncodingType"));
        assertEquals("Cyanelle",jsonNode.get("geneEncodingType").asText());

        assertNotNull(jsonNode.get("evidences"));
        assertEquals(1,jsonNode.get("evidences").size());
        ValidateJson.validateEvidence(jsonNode.get("evidences").get(0),"ECO:0000255","PROSITE-ProRule","PRU10025");
    }

    public static GeneLocation getGeneLocation() {
        return new GeneLocationBuilder()
                .geneEncodingType(GeneEncodingType.CYANELLE_PLASTID)
                .value("geneLocation value")
                .addEvidence(CreateUtils.createEvidence("ECO:0000255|PROSITE-ProRule:PRU10025"))
                .build();
    }

}
