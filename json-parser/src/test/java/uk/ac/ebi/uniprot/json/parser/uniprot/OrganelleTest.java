package uk.ac.ebi.uniprot.json.parser.uniprot;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import uk.ac.ebi.uniprot.domain.uniprot.GeneEncodingType;
import uk.ac.ebi.uniprot.domain.uniprot.Organelle;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;
import uk.ac.ebi.uniprot.json.parser.ValidateJson;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
/**
 *
 * @author lgonzales
 */
public class OrganelleTest {


    @Test
    public void testOrganelleSimple() {
        Organelle organelle = UniProtFactory.INSTANCE.createOrganelle(null,null,null);
        ValidateJson.verifyJsonRoundTripParser(organelle);
    }

    @Test
    public void testOrganelleComplete() {
        Organelle organelle = getOrganelle();
        ValidateJson.verifyJsonRoundTripParser(organelle);
        ValidateJson.verifyEmptyFields(organelle);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(organelle);

        assertNotNull(jsonNode.get("value"));
        assertEquals("organelle value",jsonNode.get("value").asText());

        assertNotNull(jsonNode.get("geneEncodingType"));
        assertEquals("Cyanelle",jsonNode.get("geneEncodingType").asText());

        assertNotNull(jsonNode.get("evidences"));
        assertEquals(1,jsonNode.get("evidences").size());
        ValidateJson.validateEvidence(jsonNode.get("evidences").get(0),"ECO:0000255","PROSITE-ProRule","PRU10025");
    }

    static Organelle getOrganelle() {
        return UniProtFactory.INSTANCE.createOrganelle(GeneEncodingType.CYANELLE_PLASTID,"organelle value",createEvidences());
    }

    private static List<Evidence> createEvidences() {
        List<Evidence> evidences = new ArrayList<>();
        evidences.add(UniProtFactory.INSTANCE.createEvidence("ECO:0000255|PROSITE-ProRule:PRU10025"));
        return evidences;
    }

}
