package org.uniprot.core.json.parser.uniparc;

import static org.junit.jupiter.api.Assertions.fail;
import static org.uniprot.core.json.parser.uniparc.UniParcEntryTest.getCompleteUniParcEntry;

import org.junit.jupiter.api.Test;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.uniparc.UniParcCrossReference;

import com.fasterxml.jackson.databind.ObjectMapper;

class UniParcCrossRefJsonConfigTest {
    @Test
    void test() {
        UniParcCrossReference entry = getCompleteUniParcEntry().getUniParcCrossReferences().get(0);
        ValidateJson.verifyJsonRoundTripParser(
                UniParcCrossRefJsonConfig.getInstance().getFullObjectMapper(), entry);
        ValidateJson.verifyEmptyFields(entry);
        try {
            ObjectMapper mapper = UniParcCrossRefJsonConfig.getInstance().getSimpleObjectMapper();
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(entry);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
