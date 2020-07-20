package org.uniprot.core.json.parser.uniref;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.go.GoAspect;
import org.uniprot.core.cv.go.impl.GeneOntologyEntryBuilder;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.uniref.UniRefEntryLight;
import org.uniprot.core.uniref.UniRefMemberIdType;
import org.uniprot.core.uniref.UniRefType;
import org.uniprot.core.uniref.impl.UniRefEntryLightBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

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

    /* We need to keep the insert order and not duplicated values for organismId and organismName
       This is why we need to use LinkedHashSet in the UniRefEntryLight interface...
       When we use Set in the interface, Jackson will assume that it can be a HashSet
       during deserialization and we will loose the order
    */
    @Test
    void testFullUniRuleEntryJsonRoundTripOrganismOrder() {
        UniRefEntryLight entry =
                new UniRefEntryLightBuilder()
                        .organismIdsAdd(10L)
                        .organismIdsAdd(30L)
                        .organismIdsAdd(12L)
                        .organismIdsAdd(11L)
                        .organismsAdd("First organism")
                        .organismsAdd("Second organism")
                        .organismsAdd("Third organism")
                        .organismsAdd("Other organism")
                        .organismsAdd("Last organism")
                        .build();
        ValidateJson.verifyJsonRoundTripParser(
                UniRefEntryLightJsonConfig.getInstance().getFullObjectMapper(), entry);
    }

    @Test
    void testNoUnsetField() {
        UniRefEntryLight entry = getCompleteUniRefEntryLight();
        ValidateJson.verifyEmptyFields(entry, "ancestors");
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
                .name("Cluster: protein")
                .sequence("AAAAA")
                .updated(LocalDate.now())
                .commonTaxon("Rat")
                .commonTaxonId(10116L)
                .entryType(UniRefType.UniRef50)
                .memberCount(5)
                .seedId("P12345")
                .goTermsAdd(
                        new GeneOntologyEntryBuilder()
                                .id("GO1")
                                .name("goName")
                                .aspect(GoAspect.COMPONENT)
                                .build())
                .build();
    }
}
