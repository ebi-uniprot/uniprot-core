package org.uniprot.core.json.parser.uniprot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.JsonNode;

import org.junit.jupiter.api.Test;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionBuilder;

/** @author lgonzales */
public class UniProtKBAccessionTest {

    @Test
    void testUniProtAccession() {
        UniProtKBAccession uniProtkbAccession = getUniProtAccession();
        ValidateJson.verifyJsonRoundTripParser(uniProtkbAccession);
        ValidateJson.verifyEmptyFields(uniProtkbAccession);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(uniProtkbAccession);
        assertEquals("P21802", jsonNode.asText());
    }

    public static UniProtKBAccession getUniProtAccession() {
        return new UniProtKBAccessionBuilder("P21802").build();
    }
}
