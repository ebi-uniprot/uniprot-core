package uk.ac.ebi.uniprot.json.parser.uniprot;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtId;
import uk.ac.ebi.uniprot.domain.uniprot.builder.UniProtIdBuilder;
import uk.ac.ebi.uniprot.json.parser.ValidateJson;

import static org.junit.Assert.assertEquals;
/**
 *
 * @author lgonzales
 */
public class UniProtIdTest {

    @Test
    public void testUniProtAccession() {
        UniProtId uniProtId = new UniProtIdBuilder("uniprot id").build();
        ValidateJson.verifyJsonRoundTripParser(uniProtId);
        ValidateJson.verifyEmptyFields(uniProtId);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(uniProtId);
        assertEquals("uniprot id",jsonNode.asText());
    }
}
