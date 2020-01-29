package org.uniprot.core.uniref.builder;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Sequence;
import org.uniprot.core.builder.SequenceBuilder;
import org.uniprot.core.uniparc.UniParcId;
import org.uniprot.core.uniparc.builder.UniParcIdBuilder;
import org.uniprot.core.uniprot.UniProtAccession;
import org.uniprot.core.uniprot.builder.UniProtAccessionBuilder;
import org.uniprot.core.uniref.*;

/**
 * @author jluo
 * @date: 13 Aug 2019
 */
class RepresentativeMemberBuilderTest {

    @Test
    void testSequence() {
        String seq = "MVSWGRFICLVVVTMATLSLARPSFSLVED";
        Sequence sequence = new SequenceBuilder(seq).build();
        UniRefMemberIdType type = UniRefMemberIdType.UNIPARC;
        RepresentativeMember member =
                new RepresentativeMemberBuilder().memberIdType(type).sequence(sequence).build();
        assertEquals(type, member.getMemberIdType());
        assertEquals(sequence, member.getSequence());
    }

    @Test
    void testFrom() {
        String seq = "MVSWGRFICLVVVTMATLSLARPSFSLVED";
        Sequence sequence = new SequenceBuilder(seq).build();
        String memberId = "P12345";
        UniRefMemberIdType type = UniRefMemberIdType.UNIPROTKB;

        RepresentativeMember member =
                new RepresentativeMemberBuilder()
                        .memberIdType(type)
                        .memberId(memberId)
                        .organismName("Homo sapiens")
                        .organismTaxId(9606)
                        .sequence(sequence)
                        .build();
        RepresentativeMember member2 = RepresentativeMemberBuilder.from(member).build();
        assertEquals(member, member2);
    }

    @Test
    void testMemberIdType() {
        UniRefMemberIdType type = UniRefMemberIdType.UNIPARC;
        RepresentativeMember member = new RepresentativeMemberBuilder().memberIdType(type).build();
        assertEquals(type, member.getMemberIdType());
    }

    @Test
    void testMemberId() {
        String memberId = "P12345";
        UniRefMemberIdType type = UniRefMemberIdType.UNIPROTKB;
        RepresentativeMember member =
                new RepresentativeMemberBuilder().memberIdType(type).memberId(memberId).build();
        assertEquals(type, member.getMemberIdType());
        assertEquals(memberId, member.getMemberId());
    }

    @Test
    void testTaxonomy() {

        RepresentativeMember member =
                new RepresentativeMemberBuilder()
                        .organismName("Homo sapiens")
                        .organismTaxId(9606)
                        .build();
        assertEquals("Homo sapiens", member.getOrganismName());
        assertEquals(9606l, member.getOrganismTaxId());
    }

    @Test
    void testSequenceLength() {
        int length = 312;
        RepresentativeMember member =
                new RepresentativeMemberBuilder().sequenceLength(length).build();
        assertEquals(length, member.getSequenceLength());
    }

    @Test
    void testProteinName() {
        String pName = "some protein name";
        RepresentativeMember member = new RepresentativeMemberBuilder().proteinName(pName).build();
        assertEquals(pName, member.getProteinName());
    }

    @Test
    void testAccession() {
        UniProtAccession accession = new UniProtAccessionBuilder("P12345").build();
        RepresentativeMember member =
                new RepresentativeMemberBuilder().addAccession(accession).build();
        assertEquals(accession, member.getUniProtAccessions().get(0));
    }

    @Test
    void testUniref100Id() {
        UniRefEntryId refId = new UniRefEntryIdBuilder("UniRef100_P03923").build();
        RepresentativeMember member = new RepresentativeMemberBuilder().uniref100Id(refId).build();
        assertEquals(refId, member.getUniRef100Id());
    }

    @Test
    void testUniref90Id() {
        UniRefEntryId refId = new UniRefEntryIdBuilder("UniRef90_P03923").build();
        RepresentativeMember member = new RepresentativeMemberBuilder().uniref90Id(refId).build();
        assertEquals(refId, member.getUniRef90Id());
    }

    @Test
    void testUniref50Id() {
        UniRefEntryId refId = new UniRefEntryIdBuilder("UniRef50_P03923").build();
        RepresentativeMember member = new RepresentativeMemberBuilder().uniref50Id(refId).build();
        assertEquals(refId, member.getUniRef50Id());
    }

    @Test
    void testUniparcId() {
        String upi = "UPI0000083A08";
        UniParcId id = new UniParcIdBuilder(upi).build();
        RepresentativeMember member = new RepresentativeMemberBuilder().uniparcId(id).build();
        assertEquals(id, member.getUniParcId());
    }

    @Test
    void testOverlapRegion() {

        int start = 50;
        int end = 65;

        OverlapRegion overlapRegion = new OverlapRegionBuilder().start(start).end(end).build();

        RepresentativeMember member =
                new RepresentativeMemberBuilder().overlapRegion(overlapRegion).build();
        assertEquals(overlapRegion, member.getOverlapRegion());
    }

    @Test
    void testIsSeed() {
        RepresentativeMember member = new RepresentativeMemberBuilder().build();
        assertNull(member.isSeed());
        member = new RepresentativeMemberBuilder().isSeed(true).build();
        assertEquals(true, member.isSeed());
    }

    @Test
    void twoDifferentObjects_defaultBuild_equal() {
        RepresentativeMember rm1 = new RepresentativeMemberBuilder().build();
        RepresentativeMember rm2 = new RepresentativeMemberBuilder().build();
        assertTrue(rm1.equals(rm2) && rm2.equals(rm1));
        assertEquals(rm1.hashCode(), rm2.hashCode());
    }
}
