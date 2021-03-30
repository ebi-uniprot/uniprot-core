package org.uniprot.core.json.parser.uniparc;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.uniparc.UniParcCrossReference;

import static org.junit.jupiter.api.Assertions.fail;
import static org.uniprot.core.json.parser.uniparc.UniParcEntryTest.getCompleteUniParcEntry;

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
            System.out.println(json);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
