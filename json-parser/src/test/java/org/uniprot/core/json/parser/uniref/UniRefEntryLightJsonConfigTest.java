package org.uniprot.core.json.parser.uniref;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Sequence;
import org.uniprot.core.cv.go.GoAspect;
import org.uniprot.core.cv.go.impl.GeneOntologyEntryBuilder;
import org.uniprot.core.impl.SequenceBuilder;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.json.parser.uniprot.CreateUtils;
import org.uniprot.core.uniparc.impl.UniParcIdBuilder;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionBuilder;
import org.uniprot.core.uniprotkb.taxonomy.Organism;
import org.uniprot.core.uniprotkb.taxonomy.impl.OrganismBuilder;
import org.uniprot.core.uniref.RepresentativeMember;
import org.uniprot.core.uniref.UniRefEntryLight;
import org.uniprot.core.uniref.UniRefMemberIdType;
import org.uniprot.core.uniref.UniRefType;
import org.uniprot.core.uniref.impl.OverlapRegionBuilder;
import org.uniprot.core.uniref.impl.RepresentativeMemberBuilder;
import org.uniprot.core.uniref.impl.UniRefEntryIdBuilder;
import org.uniprot.core.uniref.impl.UniRefEntryLightBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author lgonzales
 * @since 07/07/2020
 */
class UniRefEntryLightJsonConfigTest {

    @Test
    void testFullUniRefEntryJsonRoundTrip() {
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
    void testFullUniRefEntryJsonRoundTripOrganismOrder() {
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
            assertTrue(json.contains("\"seedId\" : \"P12345\""));
            // log.debug(json);
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
                .representativeMember(createReprestativeMember())
                .name("Cluster: protein")
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

    private static RepresentativeMember createReprestativeMember() {
        String seq = "MVSWGRFICLVVVTMATLSLARPSFSLVEDDFSAGSADFAFWERDGDSDGFDSHSDJHETRHJREH";
        Sequence sequence = new SequenceBuilder(seq).build();
        String memberId = "P12345_HUMAN";
        int length = 312;
        String pName = "some protein name";
        String upi = "UPI0000083A08";

        UniRefMemberIdType type = UniRefMemberIdType.UNIPROTKB;

        return new RepresentativeMemberBuilder()
                .memberIdType(type)
                .memberId(memberId)
                .organismName("Homo sapiens")
                .organismTaxId(9606)
                .sequenceLength(length)
                .proteinName(pName)
                .uniparcId(new UniParcIdBuilder(upi).build())
                .accessionsAdd(new UniProtKBAccessionBuilder("P12345").build())
                .accessionsAdd(new UniProtKBAccessionBuilder("P12346").build())
                .uniref100Id(new UniRefEntryIdBuilder("UniRef100_P03923").build())
                .uniref90Id(new UniRefEntryIdBuilder("UniRef90_P03943").build())
                .uniref50Id(new UniRefEntryIdBuilder("UniRef50_P03973").build())
                .overlapRegion(new OverlapRegionBuilder().start(10).end(20).build())
                .isSeed(true)
                .sequence(sequence)
                .build();
    }
}
