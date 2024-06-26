package org.uniprot.core.parser.tsv.uniref;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uniprot.core.Sequence;
import org.uniprot.core.cv.go.GoAspect;
import org.uniprot.core.cv.go.impl.GeneOntologyEntryBuilder;
import org.uniprot.core.impl.SequenceBuilder;
import org.uniprot.core.uniparc.impl.UniParcIdBuilder;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionBuilder;
import org.uniprot.core.uniprotkb.taxonomy.Organism;
import org.uniprot.core.uniprotkb.taxonomy.impl.OrganismBuilder;
import org.uniprot.core.uniref.*;
import org.uniprot.core.uniref.impl.RepresentativeMemberBuilder;
import org.uniprot.core.uniref.impl.UniRefEntryBuilder;
import org.uniprot.core.uniref.impl.UniRefEntryIdBuilder;
import org.uniprot.core.uniref.impl.UniRefMemberBuilder;

/**
 * @author jluo
 * @date: 22 Aug 2019
 */
class UniRefEntryValueMapperTest {

    private UniRefEntryValueMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new UniRefEntryValueMapper();
    }

    @Test
    void testGetData() {

        UniRefEntry entry = createEntry();
        List<String> fields =
                Arrays.asList(
                        "id",
                        "name",
                        "common_taxon",
                        "common_taxonid",
                        "count",
                        "updated",
                        "length",
                        "sequence",
                        "identity",
                        "types");
        Map<String, String> entryMap = mapper.mapEntity(entry, fields);

        assertEquals("UniRef50_P03923", entryMap.get("id"));
        assertEquals("Cluster: AMP-binding enzyme family protein", entryMap.get("name"));
        assertEquals("Homo", entryMap.get("common_taxon"));
        assertEquals("9605", entryMap.get("common_taxonid"));
        assertEquals("2", entryMap.get("count"));
        assertEquals("2018-06-21", entryMap.get("updated"));
        assertEquals("312", entryMap.get("length"));
        assertEquals(
                "MVSWGRFICLVVVTMATLSLARPSFSLVEDDFSAGSADFAFWERDGDSDGFDSHSDJHETRHJREH",
                entryMap.get("sequence"));
        assertEquals("1.0", entryMap.get("identity"));
        assertEquals("UniProtKB ID, UniParc", entryMap.get("types"));
    }

    @Test
    void testNoCommonNumbers() {
        LocalDate updated = LocalDate.now();
        UniRefEntry entry =
                new UniRefEntryBuilder()
                        .id("UniRef50_P03923")
                        .updated(updated)
                        .entryType(UniRefType.UniRef50)
                        .representativeMember(createReprestativeMember())
                        .build();

        List<String> fields =
                Arrays.asList(
                        "id",
                        "name",
                        "common_taxon",
                        "common_taxonid",
                        "count",
                        "updated",
                        "length",
                        "sequence",
                        "identity",
                        "types");

        Map<String, String> entryMap = mapper.mapEntity(entry, fields);

        assertEquals("UniRef50_P03923", entryMap.get("id"));
        assertEquals("", entryMap.get("name"));
        assertEquals("", entryMap.get("common_taxon"));
        assertEquals("", entryMap.get("common_taxonid"));
        assertEquals("", entryMap.get("count"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        assertEquals(updated.format(formatter), entryMap.get("updated"));
        assertEquals(
                "MVSWGRFICLVVVTMATLSLARPSFSLVEDDFSAGSADFAFWERDGDSDGFDSHSDJHETRHJREH",
                entryMap.get("sequence"));
        assertEquals("0.5", entryMap.get("identity"));
        assertEquals("UniProtKB ID", entryMap.get("types"));
    }

    @Test
    void testGetOrganism() {
        UniRefEntry entry = createEntry();
        String organsms = mapper.getOrganisms(entry);
        assertEquals("Homo sapiens; Streptomyces sp. NWU49", organsms);
    }

    @Test
    void testGetOrganismIDs() {
        UniRefEntry entry = createEntry();
        String organsmTaxID = mapper.getOrganismTaxId(entry);
        assertEquals("9606; 2201153", organsmTaxID);
    }

    @Test
    void testGetMembers() {
        UniRefEntry entry = createEntry();
        UniRefEntry entry2 = UniRefEntryBuilder.from(entry).membersAdd(createMember2()).build();

        String members = mapper.getMembers(entry2);
        assertEquals("P12345_HUMAN; P12347_HUMAN; UPI0000E5B23D", members);
    }

    private UniRefEntry createEntry() {
        String id = "UniRef50_P03923";
        UniRefType type = UniRefType.UniRef100;
        String name = "Cluster: AMP-binding enzyme family protein";
        UniRefEntryId entryId = new UniRefEntryIdBuilder(id).build();
        LocalDate created = LocalDate.of(2018, 6, 21);
        Organism organism = new OrganismBuilder().taxonId(9605L).scientificName("Homo").build();

        return new UniRefEntryBuilder()
                .id(entryId)
                .name(name)
                .updated(created)
                .entryType(type)
                .commonTaxon(organism)
                .representativeMember(createReprestativeMember())
                .memberCount(2)
                .membersAdd(createMember())
                .goTermsAdd(
                        new GeneOntologyEntryBuilder()
                                .aspect(GoAspect.COMPONENT)
                                .id("GO:0044444")
                                .build())
                .goTermsAdd(
                        new GeneOntologyEntryBuilder()
                                .aspect(GoAspect.FUNCTION)
                                .id("GO:0044459")
                                .build())
                .goTermsAdd(
                        new GeneOntologyEntryBuilder()
                                .aspect(GoAspect.PROCESS)
                                .id("GO:0032459")
                                .build())
                .build();
    }

    private RepresentativeMember createReprestativeMember() {
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
                .uniref100Id(new UniRefEntryIdBuilder("UniRef100_P03923").build())
                .uniref90Id(new UniRefEntryIdBuilder("UniRef90_P03943").build())
                .uniref50Id(new UniRefEntryIdBuilder("UniRef50_P03973").build())
                .isSeed(true)
                .sequence(sequence)
                .build();
    }

    private UniRefMember createMember() {
        String memberId = "P12347_HUMAN";
        int length = 312;
        String pName = "some protein name";
        String upi = "UPI0000083A08";

        UniRefMemberIdType type = UniRefMemberIdType.UNIPARC;
        return new UniRefMemberBuilder()
                .memberIdType(type)
                .memberId(memberId)
                .organismName("Streptomyces sp. NWU49")
                .organismTaxId(2201153)
                .sequenceLength(length)
                .proteinName(pName)
                .uniparcId(new UniParcIdBuilder(upi).build())
                .accessionsAdd(new UniProtKBAccessionBuilder("P12347").build())
                .uniref100Id(new UniRefEntryIdBuilder("UniRef100_P03923").build())
                .uniref90Id(new UniRefEntryIdBuilder("UniRef90_P03943").build())
                .uniref50Id(new UniRefEntryIdBuilder("UniRef50_P03973").build())
                .build();
    }

    private UniRefMember createMember2() {
        String memberId = "UPI0000E5B23D";
        int length = 312;
        String pName = "some protein name";
        String upi = "UPI0000083A08";

        UniRefMemberIdType type = UniRefMemberIdType.UNIPARC;
        return new UniRefMemberBuilder()
                .memberIdType(type)
                .memberId(memberId)
                .organismName("Streptomyces sp. NWU49")
                .organismTaxId(2201153)
                .sequenceLength(length)
                .proteinName(pName)
                .uniparcId(new UniParcIdBuilder(upi).build())
                .uniref100Id(new UniRefEntryIdBuilder("UniRef100_P03923").build())
                .uniref90Id(new UniRefEntryIdBuilder("UniRef90_P03943").build())
                .uniref50Id(new UniRefEntryIdBuilder("UniRef50_P03973").build())
                .build();
    }
}
