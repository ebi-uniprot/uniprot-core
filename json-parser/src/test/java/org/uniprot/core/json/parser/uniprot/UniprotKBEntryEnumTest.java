package org.uniprot.core.json.parser.uniprot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.uniprotkb.ProteinExistence;
import org.uniprot.core.uniprotkb.UniProtKBEntryType;

import com.fasterxml.jackson.databind.JsonNode;

/** @author lgonzales */
class UniprotKBEntryEnumTest {

    @Test
    void testProteinExistenceEnum() {
        ProteinExistence proteinExistence = ProteinExistence.PROTEIN_LEVEL;
        ValidateJson.verifyJsonRoundTripParser(proteinExistence);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(proteinExistence);
        assertEquals("1: Evidence at protein level", jsonNode.asText());
    }

    @Test
    void testEntryTypeEnum() {
        UniProtKBEntryType entryType = UniProtKBEntryType.SWISSPROT;
        ValidateJson.verifyJsonRoundTripParser(entryType);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(entryType);
        assertEquals("Swiss-Prot", jsonNode.asText());
    }
}
