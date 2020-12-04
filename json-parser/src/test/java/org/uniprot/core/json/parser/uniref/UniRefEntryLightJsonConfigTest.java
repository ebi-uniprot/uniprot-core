package org.uniprot.core.json.parser.uniref;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.go.GoAspect;
import org.uniprot.core.cv.go.impl.GeneOntologyEntryBuilder;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.json.parser.uniprot.CreateUtils;
import org.uniprot.core.uniprotkb.taxonomy.Organism;
import org.uniprot.core.uniprotkb.taxonomy.impl.OrganismBuilder;
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
        Organism organism10 =
                new OrganismBuilder().taxonId(10L).scientificName("organism 10").build();

        Organism organism30 =
                new OrganismBuilder().taxonId(30L).scientificName("organism 30").build();

        Organism organism12 =
                new OrganismBuilder().taxonId(12L).scientificName("organism 12").build();

        Organism organism11 =
                new OrganismBuilder().taxonId(11L).scientificName("organism 11").build();
        UniRefEntryLight entry =
                new UniRefEntryLightBuilder()
                        .organismsAdd(organism10)
                        .organismsAdd(organism30)
                        .organismsAdd(organism12)
                        .organismsAdd(organism11)
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
            assertTrue(json.contains("\"representativeId\" : \"P12345\""));
            assertTrue(json.contains("\"seedId\" : \"P12345\""));
            // System.out.println(json);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    public static UniRefEntryLight getCompleteUniRefEntryLight() {
        Organism organism =
                new OrganismBuilder()
                        .taxonId(9606L)
                        .scientificName("Human")
                        .commonName("common")
                        .synonymsAdd("syn")
                        .lineagesAdd("lineage")
                        .evidencesAdd(
                                CreateUtils.createEvidence("ECO:0000255|PROSITE-ProRule:PRU10025"))
                        .build();

        return new UniRefEntryLightBuilder()
                .id("UniRef50_P12345")
                .membersAdd("P12345")
                .organismsAdd(organism)
                .memberIdTypesAdd(UniRefMemberIdType.UNIPARC)
                .representativeId("P12345_HUMAN,P12345")
                .name("Cluster: protein")
                .sequence("AAAAA")
                .updated(LocalDate.now())
                .commonTaxon(organism)
                .entryType(UniRefType.UniRef50)
                .memberCount(5)
                .seedId("P12345_HUMAN,P12345")
                .goTermsAdd(
                        new GeneOntologyEntryBuilder()
                                .id("GO1")
                                .name("goName")
                                .aspect(GoAspect.COMPONENT)
                                .build())
                .build();
    }
}
