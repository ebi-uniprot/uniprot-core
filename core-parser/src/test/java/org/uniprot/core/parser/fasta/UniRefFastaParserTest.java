package org.uniprot.core.parser.fasta;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.LinkedHashSet;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Sequence;
import org.uniprot.core.cv.go.GoAspect;
import org.uniprot.core.cv.go.impl.GeneOntologyEntryBuilder;
import org.uniprot.core.impl.SequenceBuilder;
import org.uniprot.core.uniparc.impl.UniParcIdBuilder;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionBuilder;
import org.uniprot.core.uniref.*;
import org.uniprot.core.uniref.impl.*;

/**
 * @author jluo
 * @date: 22 Aug 2019
 */
class UniRefFastaParserTest {

    @Test
    void testFastaEntry() {
        UniRefEntry entry = createEntry();
        String fasta = UniRefFastaParser.toFasta(entry);
        String expected =
                ">UniRef50_P03923 AMP-binding enzyme family protein n=2 Tax=Homo sapiens"
                        + " TaxID=9606 RepID=P12345_HUMAN\n"
                        + "MVSWGRFICLVVVTMATLSLARPSFSLVEDDFSAGSADFAFWERDGDSDGFDSHSDJHET\n"
                        + "RHJREH";
        assertEquals(expected, fasta);
    }

    @Test
    void testFastaEntry2() {
        UniRefEntry entry = createEntry();
        UniRefEntry entry2 =
                UniRefEntryBuilder.from(entry).commonTaxonId(1L).commonTaxon("root").build();

        String fasta = UniRefFastaParser.toFasta(entry2);

        String expected =
                ">UniRef50_P03923 AMP-binding enzyme family protein n=2 RepID=P12345_HUMAN\n"
                        + "MVSWGRFICLVVVTMATLSLARPSFSLVEDDFSAGSADFAFWERDGDSDGFDSHSDJHET\n"
                        + "RHJREH";
        assertEquals(expected, fasta);
    }

    @Test
    void testFastaEntryLight() {
        UniRefEntryLight entry = createEntryLight();
        String fasta = UniRefFastaParser.toFasta(entry);
        String expected =
                ">UniRef50_P03923 protein n=3 Tax=tax TaxID=8 RepID=representativeId\n"
                        + "MVSWGRFICLVVVTMATLSLARPSFSLVEDDFSAGSADFAFWERDGDSDGFDSHSDJHET\n"
                        + "RHJREH";
        assertEquals(expected, fasta);
    }

    @Test
    void testFastaEntryLight2() {
        UniRefEntryLight entry = createEntryLight();
        UniRefEntryLight entry2 =
                UniRefEntryLightBuilder.from(entry).commonTaxonId(1L).commonTaxon("root").build();

        String fasta = UniRefFastaParser.toFasta(entry2);

        String expected =
                ">UniRef50_P03923 protein n=3 RepID=representativeId\n"
                        + "MVSWGRFICLVVVTMATLSLARPSFSLVEDDFSAGSADFAFWERDGDSDGFDSHSDJHET\n"
                        + "RHJREH";
        assertEquals(expected, fasta);
    }

    private UniRefEntryLight createEntryLight() {
        UniRefEntry entry = createEntry();
        return new UniRefEntryLightBuilder()
                .id("UniRef50_P03923")
                .name("Cluster: protein")
                .representativeId("representativeId")
                .sequence(entry.getRepresentativeMember().getSequence().getValue())
                .organismsSet(new LinkedHashSet<>(asList("organism1", "organism2")))
                .organismIdsSet(new LinkedHashSet<>(asList(1L, 2L)))
                .commonTaxonId(8L)
                .commonTaxon("tax")
                .memberCount(3)
                .build();
    }

    private UniRefEntry createEntry() {
        String id = "UniRef50_P03923";
        UniRefType type = UniRefType.UniRef100;
        String name = "Cluster: AMP-binding enzyme family protein";
        UniRefEntryId entryId = new UniRefEntryIdBuilder(id).build();
        return new UniRefEntryBuilder()
                .id(entryId)
                .name(name)
                .updated(LocalDate.now())
                .entryType(type)
                .commonTaxonId(9606L)
                .commonTaxon("Homo sapiens")
                .representativeMember(createRepresentativeMember())
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

    private RepresentativeMember createRepresentativeMember() {
        String seq = "MVSWGRFICLVVVTMATLSLARPSFSLVEDDFSAGSADFAFWERDGDSDGFDSHSDJHETRHJREH";
        Sequence sequence = new SequenceBuilder(seq).build();
        String memberId = "P12345_HUMAN";
        int length = 312;
        String pName = "AMP-binding enzyme family protein";
        String upi = "UPI0000083A08";

        UniRefMemberIdType type = UniRefMemberIdType.UNIPROTKB;

        return new RepresentativeMemberBuilder()
                .memberIdType(type)
                .memberId(memberId)
                .organismName("Homo sapiens")
                .organismTaxId(9606L)
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
        String memberId = "P12345_HUMAN";
        int length = 312;
        String pName = "AMP-binding enzyme family protein";
        String upi = "UPI0000083A08";

        UniRefMemberIdType type = UniRefMemberIdType.UNIPROTKB;
        return new UniRefMemberBuilder()
                .memberIdType(type)
                .memberId(memberId)
                .organismName("Homo sapiens")
                .organismTaxId(9606L)
                .sequenceLength(length)
                .proteinName(pName)
                .uniparcId(new UniParcIdBuilder(upi).build())
                .accessionsAdd(new UniProtKBAccessionBuilder("P12345").build())
                .uniref100Id(new UniRefEntryIdBuilder("UniRef100_P03923").build())
                .uniref90Id(new UniRefEntryIdBuilder("UniRef90_P03943").build())
                .uniref50Id(new UniRefEntryIdBuilder("UniRef50_P03973").build())
                .build();
    }
}
