package org.uniprot.core.json.parser.uniprot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.uniprotkb.UniProtkbId;
import org.uniprot.core.uniprotkb.impl.UniProtkbIdBuilder;

import com.fasterxml.jackson.databind.JsonNode;

/** @author lgonzales */
class UniProtkbIdTest {

    @Test
    void testUniProtAccession() {
        UniProtkbId uniProtkbId = new UniProtkbIdBuilder("uniprot id").build();
        ValidateJson.verifyJsonRoundTripParser(uniProtkbId);
        ValidateJson.verifyEmptyFields(uniProtkbId);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(uniProtkbId);
        assertEquals("uniprot id", jsonNode.asText());
    }
}
