package org.uniprot.core.json.parser.unirule;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.unirule.UniRuleEntry;
import org.uniprot.core.unirule.impl.UniRuleEntryBuilderTest;

import com.fasterxml.jackson.databind.ObjectMapper;

public class UniRuleJsonConfigTest {
    private UniRuleEntry entry;

    @BeforeEach
    void setUp() {
        this.entry = UniRuleEntryBuilderTest.createObject(1, true);
    }

    @Test
    void testFullUniRuleEntryJsonRoundTrip() {
        ValidateJson.verifyJsonRoundTripParser(
                UniRuleJsonConfig.getInstance().getFullObjectMapper(), this.entry);
    }

    @Test
    void testNoUnsetField() {
        ValidateJson.verifyEmptyFields(this.entry);
    }

    @Test
    void testSimpleObjectSerialization() {
        try {
            ObjectMapper mapper = UniRuleJsonConfig.getInstance().getSimpleObjectMapper();
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this.entry);
            assertNotNull(json);
            //            System.out.println(json); // uncomment the code to generate the sample
            // json
            //             of UniRule model.
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
