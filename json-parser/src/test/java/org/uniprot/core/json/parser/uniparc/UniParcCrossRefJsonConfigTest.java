package org.uniprot.core.json.parser.uniparc;

import static org.junit.jupiter.api.Assertions.fail;
import static org.uniprot.core.json.parser.uniparc.UniParcEntryTest.getCompleteUniParcEntry;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.uniparc.UniParcCrossReference;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.uniprot.core.uniparc.UniParcEntry;
import org.uniprot.core.uniparc.impl.UniParcCrossReferencePair;
import org.uniprot.core.util.Pair;
import org.uniprot.core.util.PairImpl;

import java.util.ArrayList;
import java.util.List;

class UniParcCrossRefJsonConfigTest {
    @Test
    void test() {
        UniParcEntry completeUniParcEntry = getCompleteUniParcEntry();
        Pair<String, List<UniParcCrossReference>> entry = new UniParcCrossReferencePair(completeUniParcEntry.getUniParcId().getValue(), completeUniParcEntry.getUniParcCrossReferences());
/*
        ValidateJson.verifyJsonRoundTripParser(
                UniParcCrossRefJsonConfig.getInstance().getFullObjectMapper(), completeUniParcEntry.getUniParcCrossReferences().get(0));

        ValidateJson.verifyJsonRoundTripParser(
                UniParcCrossRefJsonConfig.getInstance().getFullObjectMapper(), new ArrayList<>(completeUniParcEntry.getUniParcCrossReferences()));
*/

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
