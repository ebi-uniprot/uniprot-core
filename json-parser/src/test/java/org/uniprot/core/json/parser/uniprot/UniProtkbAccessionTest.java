package org.uniprot.core.json.parser.uniprot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.uniprotkb.UniProtkbAccession;
import org.uniprot.core.uniprotkb.impl.UniProtkbAccessionBuilder;

import com.fasterxml.jackson.databind.JsonNode;

/** @author lgonzales */
public class UniProtkbAccessionTest {

    @Test
    void testUniProtAccession() {
        UniProtkbAccession uniProtkbAccession = getUniProtAccession();
        ValidateJson.verifyJsonRoundTripParser(uniProtkbAccession);
        ValidateJson.verifyEmptyFields(uniProtkbAccession);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(uniProtkbAccession);
        assertEquals("P21802", jsonNode.asText());
    }

    public static UniProtkbAccession getUniProtAccession() {
        return new UniProtkbAccessionBuilder("P21802").build();
    }
}
