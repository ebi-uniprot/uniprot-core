package org.uniprot.core.json.parser.uniprot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.uniprot.UniProtId;
import org.uniprot.core.uniprot.impl.UniProtIdBuilder;

import com.fasterxml.jackson.databind.JsonNode;

/** @author lgonzales */
class UniProtIdTest {

    @Test
    void testUniProtAccession() {
        UniProtId uniProtId = new UniProtIdBuilder("uniprot id").build();
        ValidateJson.verifyJsonRoundTripParser(uniProtId);
        ValidateJson.verifyEmptyFields(uniProtId);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(uniProtId);
        assertEquals("uniprot id", jsonNode.asText());
    }
}
