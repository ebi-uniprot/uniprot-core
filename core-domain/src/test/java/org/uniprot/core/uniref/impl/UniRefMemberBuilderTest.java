package org.uniprot.core.uniref.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniparc.UniParcId;
import org.uniprot.core.uniparc.impl.UniParcIdBuilder;
import org.uniprot.core.uniprotkb.UniProtkbAccession;
import org.uniprot.core.uniprotkb.impl.UniProtkbAccessionBuilder;
import org.uniprot.core.uniref.OverlapRegion;
import org.uniprot.core.uniref.UniRefEntryId;
import org.uniprot.core.uniref.UniRefMember;
import org.uniprot.core.uniref.UniRefMemberIdType;

/**
 * @author jluo
 * @date: 12 Aug 2019
 */
class UniRefMemberBuilderTest {

    @Test
    void testFrom() {
        String memberId = "P12345";
        UniRefMemberIdType type = UniRefMemberIdType.UNIPROTKB;

        UniRefMember member =
                new UniRefMemberBuilder()
                        .memberIdType(type)
                        .memberId(memberId)
                        .organismName("Homo sapiens")
                        .organismTaxId(9606)
                        .build();

        UniRefMember member2 = UniRefMemberBuilder.from(member).build();
        assertEquals(member, member2);
    }

    @Test
    void testMemberIdType() {
        UniRefMemberIdType type = UniRefMemberIdType.UNIPARC;
        UniRefMember member = new UniRefMemberBuilder().memberIdType(type).build();
        assertEquals(type, member.getMemberIdType());
    }

    @Test
    void testMemberId() {
        String memberId = "P12345";
        UniRefMemberIdType type = UniRefMemberIdType.UNIPROTKB;
        UniRefMember member =
                new UniRefMemberBuilder().memberIdType(type).memberId(memberId).build();
        assertEquals(type, member.getMemberIdType());
        assertEquals(memberId, member.getMemberId());
    }

    @Test
    void testTaxonomy() {

        UniRefMember member =
                new UniRefMemberBuilder().organismName("Homo sapiens").organismTaxId(9606).build();
        assertEquals("Homo sapiens", member.getOrganismName());
        assertEquals(9606l, member.getOrganismTaxId());
    }

    @Test
    void testSequenceLength() {
        int length = 312;
        UniRefMember member = new UniRefMemberBuilder().sequenceLength(length).build();
        assertEquals(length, member.getSequenceLength());
    }

    @Test
    void testProteinName() {
        String pName = "some protein name";
        UniRefMember member = new UniRefMemberBuilder().proteinName(pName).build();
        assertEquals(pName, member.getProteinName());
    }

    @Test
    void testAddAccession() {
        UniProtkbAccession accession = new UniProtkbAccessionBuilder("P12345").build();
        UniRefMember member = new UniRefMemberBuilder().accessionsAdd(accession).build();
        assertEquals(1, member.getUniProtAccessions().size());
        assertEquals(accession, member.getUniProtAccessions().get(0));
    }

    @Test
    void testAccessions() {
        UniProtkbAccession accession1 = new UniProtkbAccessionBuilder("P12345").build();
        UniProtkbAccession accession2 = new UniProtkbAccessionBuilder("P12346").build();
        List<UniProtkbAccession> accessions = Arrays.asList(accession1, accession2);
        UniRefMember member = new UniRefMemberBuilder().accessionsSet(accessions).build();
        assertEquals(2, member.getUniProtAccessions().size());
        assertEquals(accession1, member.getUniProtAccessions().get(0));
        assertEquals(accession2, member.getUniProtAccessions().get(1));
    }

    @Test
    void testUniref100Id() {
        UniRefEntryId refId = new UniRefEntryIdBuilder("UniRef100_P03923").build();
        UniRefMember member = new UniRefMemberBuilder().uniref100Id(refId).build();
        assertEquals(refId, member.getUniRef100Id());
    }

    @Test
    void testUniref90Id() {
        UniRefEntryId refId = new UniRefEntryIdBuilder("UniRef90_P03923").build();
        UniRefMember member = new UniRefMemberBuilder().uniref90Id(refId).build();
        assertEquals(refId, member.getUniRef90Id());
    }

    @Test
    void testUniref50Id() {
        UniRefEntryId refId = new UniRefEntryIdBuilder("UniRef50_P03923").build();
        UniRefMember member = new UniRefMemberBuilder().uniref50Id(refId).build();
        assertEquals(refId, member.getUniRef50Id());
    }

    @Test
    void testUniparcId() {
        String upi = "UPI0000083A08";
        UniParcId id = new UniParcIdBuilder(upi).build();
        UniRefMember member = new UniRefMemberBuilder().uniparcId(id).build();
        assertEquals(id, member.getUniParcId());
    }

    @Test
    void testOverlapRegion() {
        int start = 50;
        int end = 65;

        OverlapRegion overlapRegion = new OverlapRegionBuilder().start(start).end(end).build();

        UniRefMember member = new UniRefMemberBuilder().overlapRegion(overlapRegion).build();
        assertEquals(overlapRegion, member.getOverlapRegion());
    }

    @Test
    void testIsSeed() {
        UniRefMember member = new UniRefMemberBuilder().build();
        assertNull(member.isSeed());
        member = new UniRefMemberBuilder().isSeed(true).build();
        assertEquals(true, member.isSeed());
    }

    @Test
    void twoDifferentObjects_defaultBuild_equal() {
        UniRefMember m1 = new UniRefMemberBuilder().build();
        UniRefMember m2 = new UniRefMemberBuilder().build();
        assertTrue(m1.equals(m2) && m2.equals(m1));
        assertTrue(m1.hashCode() == m2.hashCode());
    }
}
