package org.uniprot.core.uniref.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Sequence;
import org.uniprot.core.cv.go.GeneOntologyEntry;
import org.uniprot.core.cv.go.GoAspect;
import org.uniprot.core.cv.go.impl.GeneOntologyEntryBuilder;
import org.uniprot.core.impl.SequenceBuilder;
import org.uniprot.core.uniref.RepresentativeMember;
import org.uniprot.core.uniref.UniRefEntry;
import org.uniprot.core.uniref.UniRefEntryId;
import org.uniprot.core.uniref.UniRefMember;
import org.uniprot.core.uniref.UniRefMemberIdType;
import org.uniprot.core.uniref.UniRefType;

/**
 * @author jluo
 * @date: 13 Aug 2019
 */
class UniRefEntryBuilderTest {

    @Test
    void testFrom() {
        String id = "UniRef50_P03923";
        UniRefType type = UniRefType.UniRef100;

        UniRefEntryId entryId = new UniRefEntryIdBuilder(id).build();
        String seq = "MVSWGRFICLVVVTMATLSLARPSFSLVED";
        Sequence sequence = new SequenceBuilder(seq).build();
        UniRefMemberIdType umtype = UniRefMemberIdType.UNIPARC;
        RepresentativeMember member =
                new RepresentativeMemberBuilder().memberIdType(umtype).sequence(sequence).build();
        UniRefEntry entry =
                new UniRefEntryBuilder()
                        .id(entryId)
                        .name("Some Name")
                        .updated(LocalDate.now())
                        .entryType(type)
                        .commonTaxonId(9606L)
                        .seedId("seedId")
                        .commonTaxon("Homo sapiens")
                        .representativeMember(member)
                        .build();

        UniRefEntry entry2 = UniRefEntryBuilder.from(entry).build();
        assertEquals(entry, entry2);
    }

    @Test
    void testIdUniRefEntryId() {
        String id = "UniRef50_P03923";
        UniRefType type = UniRefType.UniRef50;
        UniRefEntryId entryId = new UniRefEntryIdBuilder(id).build();
        UniRefEntry entry = new UniRefEntryBuilder().id(entryId).entryType(type).build();
        assertEquals(entryId, entry.getId());

        assertEquals(type, entry.getEntryType());
    }

    @Test
    void testName() {
        String name = "Ref name";
        UniRefEntry entry = new UniRefEntryBuilder().name(name).build();
        assertEquals(name, entry.getName());
    }

    @Test
    void testUpdated() {
        LocalDate update = LocalDate.now();

        UniRefEntry entry = new UniRefEntryBuilder().updated(update).build();
        assertEquals(update, entry.getUpdated());
    }

    @Test
    void testCommonTaxonId() {
        long commonTax = 9606L;

        UniRefEntry entry = new UniRefEntryBuilder().commonTaxonId(commonTax).build();
        assertEquals(commonTax, entry.getCommonTaxonId());
    }

    @Test
    void testCommonTaxonName() {
        String commonTax = "Homo sapiens";

        UniRefEntry entry = new UniRefEntryBuilder().commonTaxon(commonTax).build();
        assertEquals(commonTax, entry.getCommonTaxon());
    }

    @Test
    void testSeedId() {
        String seedId = "P21802";

        UniRefEntry entry = new UniRefEntryBuilder().seedId(seedId).build();
        assertEquals(seedId, entry.getSeedId());
    }

    @Test
    void testGoTerms() {
        GoAspect type = GoAspect.COMPONENT;
        String id = "GO:0044444";
        GeneOntologyEntry goTerm = new GeneOntologyEntryBuilder().aspect(type).id(id).build();

        GoAspect type2 = GoAspect.PROCESS;
        String id2 = "GO:0044435";
        GeneOntologyEntry goTerm2 = new GeneOntologyEntryBuilder().aspect(type2).id(id2).build();

        List<GeneOntologyEntry> goTerms = Arrays.asList(goTerm, goTerm2);

        UniRefEntry entry = new UniRefEntryBuilder().goTermsSet(goTerms).build();
        assertEquals(goTerms, entry.getGoTerms());
    }

    @Test
    void testAddGoTerm() {
        GoAspect type = GoAspect.COMPONENT;
        String id = "GO:0044444";
        GeneOntologyEntry goTerm = new GeneOntologyEntryBuilder().aspect(type).id(id).build();

        GoAspect type2 = GoAspect.PROCESS;
        String id2 = "GO:0044435";
        GeneOntologyEntry goTerm2 = new GeneOntologyEntryBuilder().aspect(type2).id(id2).build();
        List<GeneOntologyEntry> goTerms = Arrays.asList(goTerm, goTerm2);
        UniRefEntry entry = new UniRefEntryBuilder().goTermsAdd(goTerm).goTermsAdd(goTerm2).build();
        assertEquals(goTerms, entry.getGoTerms());
    }

    @Test
    void testRepresentativeMember() {
        String seq = "MVSWGRFICLVVVTMATLSLARPSFSLVED";
        Sequence sequence = new SequenceBuilder(seq).build();
        UniRefMemberIdType type = UniRefMemberIdType.UNIPARC;
        RepresentativeMember member =
                new RepresentativeMemberBuilder().memberIdType(type).sequence(sequence).build();
        UniRefEntry entry = new UniRefEntryBuilder().representativeMember(member).build();
        assertEquals(member, entry.getRepresentativeMember());
    }

    @Test
    void testUnirefMembers() {
        String memberId = "P12345";
        UniRefMemberIdType type = UniRefMemberIdType.UNIPROTKB;

        UniRefMember member =
                new UniRefMemberBuilder()
                        .memberIdType(type)
                        .memberId(memberId)
                        .organismName("Homo sapiens")
                        .organismTaxId(9606)
                        .build();

        UniRefMember member2 =
                new UniRefMemberBuilder()
                        .memberIdType(UniRefMemberIdType.UNIPARC)
                        .memberId("UPI0000321")
                        .organismName("Homo sapiens")
                        .organismTaxId(9606)
                        .build();

        List<UniRefMember> members = Arrays.asList(member, member2);
        UniRefEntry entry = new UniRefEntryBuilder().membersSet(members).build();
        assertEquals(members, entry.getMembers());
    }

    @Test
    void testAddUnirefMember() {
        String memberId = "P12345";
        UniRefMemberIdType type = UniRefMemberIdType.UNIPROTKB;

        UniRefMember member =
                new UniRefMemberBuilder()
                        .memberIdType(type)
                        .memberId(memberId)
                        .organismName("Homo sapiens")
                        .organismTaxId(9606)
                        .build();

        UniRefEntry entry = new UniRefEntryBuilder().membersAdd(member).build();
        assertEquals(Arrays.asList(member), entry.getMembers());
    }

    @Test
    void twoDifferentObjects_defaultBuild_equal() {
        UniRefEntry e1 = new UniRefEntryBuilder().build();
        UniRefEntry e2 = new UniRefEntryBuilder().build();
        assertTrue(e1.equals(e2) && e2.equals(e1));
        assertEquals(e1.hashCode(), e2.hashCode());
    }

    @Test
    void whenNoMemberCount_returnNull() {
        UniRefEntry e1 = new UniRefEntryBuilder().build();
        assertNull(e1.getMemberCount());
    }

    @Test
    void whenMemberCount_returnMemberCount() {
        UniRefEntry e1 = new UniRefEntryBuilder().memberCount(5).build();
        assertEquals(5, e1.getMemberCount());
    }

    @Test
    void canGetId_setInBuilder() {
        UniRefEntry e1 = new UniRefEntryBuilder().id("101").build();
        assertNotNull(e1.getId());
        assertEquals("101", e1.getId().getValue());
    }
}
