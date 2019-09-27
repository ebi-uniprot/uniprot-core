package org.uniprot.core.parser.tsv.uniref;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Sequence;
import org.uniprot.core.builder.SequenceBuilder;
import org.uniprot.core.uniparc.builder.UniParcIdBuilder;
import org.uniprot.core.uniprot.builder.UniProtAccessionBuilder;
import org.uniprot.core.uniref.GoTermType;
import org.uniprot.core.uniref.RepresentativeMember;
import org.uniprot.core.uniref.UniRefEntry;
import org.uniprot.core.uniref.UniRefEntryId;
import org.uniprot.core.uniref.UniRefMember;
import org.uniprot.core.uniref.UniRefMemberIdType;
import org.uniprot.core.uniref.UniRefType;
import org.uniprot.core.uniref.builder.GoTermBuilder;
import org.uniprot.core.uniref.builder.RepresentativeMemberBuilder;
import org.uniprot.core.uniref.builder.UniRefEntryBuilder;
import org.uniprot.core.uniref.builder.UniRefEntryIdBuilder;
import org.uniprot.core.uniref.builder.UniRefMemberBuilder;

/**
 * @author jluo
 * @date: 22 Aug 2019
 */
class UniRefEntryMapTest {

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
                        "created",
                        "length",
                        "sequence");
        UniRefEntryMap entryMap = new UniRefEntryMap(entry, fields);

        List<String> data = entryMap.getData();

        List<String> expected =
                Arrays.asList(
                        "UniRef50_P03923",
                        "Cluster: AMP-binding enzyme family protein",
                        "Homo",
                        "9605",
                        "2",
                        "2018-06-21",
                        "312",
                        "MVSWGRFICLVVVTMATLSLARPSFSLVEDDFSAGSADFAFWERDGDSDGFDSHSDJHETRHJREH");
        assertEquals(expected, data);
    }

    @Test
    void testGetOrganism() {
        UniRefEntry entry = createEntry();
        String organsms = UniRefEntryMap.getOrganisms(entry);
        assertEquals("Homo sapiens; Streptomyces sp. NWU49", organsms);
    }

    @Test
    void testGetOrganismIDs() {
        UniRefEntry entry = createEntry();
        String organsmTaxID = UniRefEntryMap.getOrganismTaxId(entry);
        assertEquals("9606; 2201153", organsmTaxID);
    }

    @Test
    void testGetMembers() {
        UniRefEntry entry = createEntry();
        UniRefEntry entry2 =
                new UniRefEntryBuilder().from(entry).addMember(createMember2()).build();

        String members = UniRefEntryMap.getMembers(entry2);
        assertEquals("P12345; P12347; UPI0000E5B23D", members);
    }

    private UniRefEntry createEntry() {
        String id = "UniRef50_P03923";
        UniRefType type = UniRefType.UniRef100;
        String name = "Cluster: AMP-binding enzyme family protein";
        UniRefEntryId entryId = new UniRefEntryIdBuilder(id).build();
        LocalDate created = LocalDate.of(2018, 6, 21);
        UniRefEntry entry =
                new UniRefEntryBuilder()
                        .id(entryId)
                        .name(name)
                        .updated(created)
                        .entryType(type)
                        .commonTaxonId(9605l)
                        .commonTaxon("Homo")
                        .representativeMember(createReprestativeMember())
                        .addMember(createMember())
                        .addGoTerm(
                                new GoTermBuilder()
                                        .type(GoTermType.COMPONENT)
                                        .id("GO:0044444")
                                        .build())
                        .addGoTerm(
                                new GoTermBuilder()
                                        .type(GoTermType.FUNCTION)
                                        .id("GO:0044459")
                                        .build())
                        .addGoTerm(
                                new GoTermBuilder()
                                        .type(GoTermType.PROCESS)
                                        .id("GO:0032459")
                                        .build())
                        .build();
        return entry;
    }

    private RepresentativeMember createReprestativeMember() {
        String seq = "MVSWGRFICLVVVTMATLSLARPSFSLVEDDFSAGSADFAFWERDGDSDGFDSHSDJHETRHJREH";
        Sequence sequence = new SequenceBuilder(seq).build();
        String memberId = "P12345_HUMAN";
        int length = 312;
        String pName = "some protein name";
        String upi = "UPI0000083A08";

        UniRefMemberIdType type = UniRefMemberIdType.UNIPROTKB;

        RepresentativeMember member =
                new RepresentativeMemberBuilder()
                        .memberIdType(type)
                        .memberId(memberId)
                        .organismName("Homo sapiens")
                        .organismTaxId(9606)
                        .sequenceLength(length)
                        .proteinName(pName)
                        .uniparcId(new UniParcIdBuilder(upi).build())
                        .accession(new UniProtAccessionBuilder("P12345").build())
                        .uniref100Id(new UniRefEntryIdBuilder("UniRef100_P03923").build())
                        .uniref90Id(new UniRefEntryIdBuilder("UniRef90_P03943").build())
                        .uniref50Id(new UniRefEntryIdBuilder("UniRef50_P03973").build())
                        .isSeed(true)
                        .sequence(sequence)
                        .build();
        return member;
    }

    private UniRefMember createMember() {
        String memberId = "P12347_HUMAN";
        int length = 312;
        String pName = "some protein name";
        String upi = "UPI0000083A08";

        UniRefMemberIdType type = UniRefMemberIdType.UNIPROTKB;
        UniRefMember member =
                new UniRefMemberBuilder()
                        .memberIdType(type)
                        .memberId(memberId)
                        .organismName("Streptomyces sp. NWU49")
                        .organismTaxId(2201153)
                        .sequenceLength(length)
                        .proteinName(pName)
                        .uniparcId(new UniParcIdBuilder(upi).build())
                        .accession(new UniProtAccessionBuilder("P12347").build())
                        .uniref100Id(new UniRefEntryIdBuilder("UniRef100_P03923").build())
                        .uniref90Id(new UniRefEntryIdBuilder("UniRef90_P03943").build())
                        .uniref50Id(new UniRefEntryIdBuilder("UniRef50_P03973").build())
                        .build();
        return member;
    }

    private UniRefMember createMember2() {
        String memberId = "UPI0000E5B23D";
        int length = 312;
        String pName = "some protein name";
        String upi = "UPI0000083A08";

        UniRefMemberIdType type = UniRefMemberIdType.UNIPARC;
        UniRefMember member =
                new UniRefMemberBuilder()
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
        return member;
    }
}
