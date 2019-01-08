package uk.ac.ebi.uniprot.json.parser.uniprot;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtId;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;
import uk.ac.ebi.uniprot.json.parser.ValidateJson;

import static org.junit.Assert.assertEquals;
/**
 *
 * @author lgonzales
 */
public class UniProtIdTest {

    @Test
    public void testUniProtAccession() {
        UniProtId uniProtId = UniProtFactory.INSTANCE.createUniProtId("uniprot id");
        ValidateJson.verifyJsonRoundTripParser(uniProtId);
        ValidateJson.verifyEmptyFields(uniProtId);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(uniProtId);
        assertEquals("uniprot id",jsonNode.asText());
    }
}
