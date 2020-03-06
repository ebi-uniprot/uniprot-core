package org.uniprot.core.json.parser.uniprot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.uniprot.UniProtAccession;
import org.uniprot.core.uniprot.impl.UniProtAccessionBuilder;

import com.fasterxml.jackson.databind.JsonNode;

/** @author lgonzales */
public class UniProtAccessionTest {

    @Test
    void testUniProtAccession() {
        UniProtAccession uniProtAccession = getUniProtAccession();
        ValidateJson.verifyJsonRoundTripParser(uniProtAccession);
        ValidateJson.verifyEmptyFields(uniProtAccession);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(uniProtAccession);
        assertEquals("P21802", jsonNode.asText());
    }

    public static UniProtAccession getUniProtAccession() {
        return new UniProtAccessionBuilder("P21802").build();
    }
}
