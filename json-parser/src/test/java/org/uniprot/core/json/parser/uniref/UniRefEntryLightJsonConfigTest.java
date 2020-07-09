package org.uniprot.core.json.parser.uniref;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.uniref.UniRefEntryLight;
import org.uniprot.core.uniref.UniRefMemberIdType;
import org.uniprot.core.uniref.UniRefType;
import org.uniprot.core.uniref.impl.UniRefEntryLightBuilder;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author lgonzales
 * @since 07/07/2020
 */
class UniRefEntryLightJsonConfigTest {

    @Test
    void testFullUniRuleEntryJsonRoundTrip() {
        UniRefEntryLight entry = getCompleteUniRefEntryLight();
        ValidateJson.verifyJsonRoundTripParser(
                UniRefEntryLightJsonConfig.getInstance().getFullObjectMapper(), entry);
    }

    @Test
    void testNoUnsetField() {
        UniRefEntryLight entry = getCompleteUniRefEntryLight();
        ValidateJson.verifyEmptyFields(entry);
    }

    @Test
    void testSimpleObjectSerialization() {
        try {
            UniRefEntryLight entry = getCompleteUniRefEntryLight();
            ObjectMapper mapper = UniRefEntryLightJsonConfig.getInstance().getSimpleObjectMapper();
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(entry);
            // uncomment the code to generate samplejson for UniRefEntryLight model.
            assertNotNull(json);
            // System.out.println(json);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    public static UniRefEntryLight getCompleteUniRefEntryLight() {
        return new UniRefEntryLightBuilder()
                .id("UniRef50_P12345")
                .membersAdd("P12345")
                .organismsAdd("Human")
                .organismIdsAdd(9606L)
                .memberIdTypesAdd(UniRefMemberIdType.UNIPARC)
                .representativeId("id")
                .representativeProteinName("protein")
                .representativeSequence("AAAAA")
                .updated(LocalDate.now())
                .commonTaxon("Rat")
                .commonTaxonId(10116L)
                .entryType(UniRefType.UniRef50)
                .memberCount(5)
                .build();
    }
}
