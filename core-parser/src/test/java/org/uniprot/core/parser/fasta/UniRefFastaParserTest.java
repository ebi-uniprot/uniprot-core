package org.uniprot.core.parser.fasta;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

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
class UniRefFastaParserTest {

    @Test
    void testFastaEntry() {
        UniRefEntry entry = createEntry();
        String fasta = UniRefFastaParser.toFasta(entry);
        String expected =
                ">UniRef50_P03923 AMP-binding enzyme family protein n=2 Tax=Homo sapiens TaxID=9606 RepID=P12345_HUMAN\n"
                        + "MVSWGRFICLVVVTMATLSLARPSFSLVEDDFSAGSADFAFWERDGDSDGFDSHSDJHET\n"
                        + "RHJREH";
        assertEquals(expected, fasta);
    }

    @Test
    void testFastaEntry2() {
        UniRefEntry entry = createEntry();
        UniRefEntry entry2 =
                UniRefEntryBuilder.from(entry).commonTaxonId(1l).commonTaxon("root").build();

        String fasta = UniRefFastaParser.toFasta(entry2);

        String expected =
                ">UniRef50_P03923 AMP-binding enzyme family protein n=2 RepID=P12345_HUMAN\n"
                        + "MVSWGRFICLVVVTMATLSLARPSFSLVEDDFSAGSADFAFWERDGDSDGFDSHSDJHET\n"
                        + "RHJREH";
        assertEquals(expected, fasta);
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
                .commonTaxonId(9606l)
                .commonTaxon("Homo sapiens")
                .representativeMember(createReprestativeMember())
                .membersAdd(createMember())
                .goTermsAdd(new GoTermBuilder().type(GoTermType.COMPONENT).id("GO:0044444").build())
                .goTermsAdd(new GoTermBuilder().type(GoTermType.FUNCTION).id("GO:0044459").build())
                .goTermsAdd(new GoTermBuilder().type(GoTermType.PROCESS).id("GO:0032459").build())
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

        RepresentativeMember member =
                new RepresentativeMemberBuilder()
                        .memberIdType(type)
                        .memberId(memberId)
                        .organismName("Homo sapiens")
                        .organismTaxId(9606)
                        .sequenceLength(length)
                        .proteinName(pName)
                        .uniparcId(new UniParcIdBuilder(upi).build())
                        .accessionsAdd(new UniProtAccessionBuilder("P12345").build())
                        .uniref100Id(new UniRefEntryIdBuilder("UniRef100_P03923").build())
                        .uniref90Id(new UniRefEntryIdBuilder("UniRef90_P03943").build())
                        .uniref50Id(new UniRefEntryIdBuilder("UniRef50_P03973").build())
                        .isSeed(true)
                        .sequence(sequence)
                        .build();
        return member;
    }

    private UniRefMember createMember() {
        String memberId = "P12345_HUMAN";
        int length = 312;
        String pName = "some protein name";
        String upi = "UPI0000083A08";

        UniRefMemberIdType type = UniRefMemberIdType.UNIPROTKB;
        UniRefMember member =
                new UniRefMemberBuilder()
                        .memberIdType(type)
                        .memberId(memberId)
                        .organismName("Homo sapiens")
                        .organismTaxId(9606)
                        .sequenceLength(length)
                        .proteinName(pName)
                        .uniparcId(new UniParcIdBuilder(upi).build())
                        .accessionsAdd(new UniProtAccessionBuilder("P12345").build())
                        .uniref100Id(new UniRefEntryIdBuilder("UniRef100_P03923").build())
                        .uniref90Id(new UniRefEntryIdBuilder("UniRef90_P03943").build())
                        .uniref50Id(new UniRefEntryIdBuilder("UniRef50_P03973").build())
                        .build();
        return member;
    }
}
