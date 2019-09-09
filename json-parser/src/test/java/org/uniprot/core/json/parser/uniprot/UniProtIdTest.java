package org.uniprot.core.json.parser.uniprot;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.uniprot.UniProtId;
import org.uniprot.core.uniprot.builder.UniProtIdBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
