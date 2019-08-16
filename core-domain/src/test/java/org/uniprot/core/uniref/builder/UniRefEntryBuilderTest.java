package org.uniprot.core.uniref.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Sequence;
import org.uniprot.core.builder.SequenceBuilder;
import org.uniprot.core.uniref.GoTerm;
import org.uniprot.core.uniref.GoTermType;
import org.uniprot.core.uniref.RepresentativeMember;
import org.uniprot.core.uniref.UniRefEntry;
import org.uniprot.core.uniref.UniRefEntryId;
import org.uniprot.core.uniref.UniRefMember;
import org.uniprot.core.uniref.UniRefMemberIdType;
import org.uniprot.core.uniref.UniRefType;

/**
 *
 * @author jluo
 * @date: 13 Aug 2019
 *
*/

class UniRefEntryBuilderTest {

	@Test
	void testFrom() {
		String id = "UniRef50_P03923";
		UniRefType type = UniRefType.UniRef100;
	
		UniRefEntryId entryId = new UniRefEntryIdBuilder(id).build();
		String seq = "MVSWGRFICLVVVTMATLSLARPSFSLVED";
		Sequence sequence = new SequenceBuilder(seq).build();
		UniRefMemberIdType umtype =UniRefMemberIdType.UNIPARC;
		RepresentativeMember member = new RepresentativeMemberBuilder()
				.memberIdType(umtype)
				.sequence(sequence)
				.build();
		UniRefEntry entry = new UniRefEntryBuilder()
				.id(entryId)
				.name("Some Name")
				.updated(LocalDate.now())
				.entryType(type)
				.commonTaxonId(9606l)
				.commonTaxonName("Homo sapiens")
				.representativeMember(member)
				.build();
		
		UniRefEntry entry2 =new UniRefEntryBuilder().from(entry).build();
		assertEquals(entry, entry2);
	}

	@Test
	void testIdUniRefEntryId() {
		String id = "UniRef50_P03923";
		UniRefType type = UniRefType.UniRef50;
		UniRefEntryId entryId = new UniRefEntryIdBuilder(id).build();
		UniRefEntry entry = new UniRefEntryBuilder()
				.id(entryId)
				.entryType(type)
				.build();
		assertEquals(entryId, entry.getId());
		
		assertEquals(type, entry.getEntryType());

	}

	@Test
	void testName() {
		String name ="Ref name";
		UniRefEntry entry = new UniRefEntryBuilder()
				.name(name)
				.build();
		assertEquals(name, entry.getName());
	}

	@Test
	void testUpdated() {
		LocalDate update = LocalDate.now();
	
		UniRefEntry entry = new UniRefEntryBuilder()
				.updated(update)
				.build();
		assertEquals(update, entry.getUpdated());
	}

	

	@Test
	void testCommonTaxonId() {
		long commonTax = 9606l;
		
		UniRefEntry entry = new UniRefEntryBuilder()
				.commonTaxonId(commonTax)
				.build();
		assertEquals(commonTax, entry.getCommonTaxonId());
	}

	@Test
	void testCommonTaxonName() {
		String commonTax = "Homo sapiens";
		
		UniRefEntry entry = new UniRefEntryBuilder()
				.commonTaxonName(commonTax)
				.build();
		assertEquals(commonTax, entry.getCommonTaxonName());
	}

	@Test
	void testGoTerms() {
		GoTermType type = GoTermType.COMPONENT;
		String id = "GO:0044444";
		GoTerm goTerm = new GoTermBuilder().type(type).id(id).build();
		
		GoTermType type2 = GoTermType.PROCESS;
		String id2 = "GO:0044435";
		GoTerm goTerm2 = new GoTermBuilder().type(type2).id(id2).build();
		
		List<GoTerm> goTerms = Arrays.asList(goTerm, goTerm2);
		
		UniRefEntry entry = new UniRefEntryBuilder()
				.goTerms(goTerms)
				.build();
		assertEquals(goTerms, entry.getGoTerms());
		
		
	}

	@Test
	void testAddGoTerm() {
		GoTermType type = GoTermType.COMPONENT;
		String id = "GO:0044444";
		GoTerm goTerm = new GoTermBuilder().type(type).id(id).build();
		
		GoTermType type2 = GoTermType.PROCESS;
		String id2 = "GO:0044435";
		GoTerm goTerm2 = new GoTermBuilder().type(type2).id(id2).build();
		List<GoTerm> goTerms = Arrays.asList(goTerm, goTerm2);
		UniRefEntry entry = new UniRefEntryBuilder()
					.addGoTerm(goTerm)
					.addGoTerm(goTerm2)
					.build();
			assertEquals(goTerms, entry.getGoTerms());
	}

	@Test
	void testRepresentativeMember() {
		String seq = "MVSWGRFICLVVVTMATLSLARPSFSLVED";
		Sequence sequence = new SequenceBuilder(seq).build();
		UniRefMemberIdType type =UniRefMemberIdType.UNIPARC;
		RepresentativeMember member = new RepresentativeMemberBuilder()
				.memberIdType(type)
				.sequence(sequence)
				.build();
		UniRefEntry entry = new UniRefEntryBuilder()
				.representativeMember(member)
				.build();
		assertEquals(member, entry.getRepresentativeMember());
		
	}

	@Test
	void testUnirefMembers() {
		String memberId = "P12345";
		UniRefMemberIdType type =UniRefMemberIdType.UNIPROTKB;
		
		UniRefMember member = new UniRefMemberBuilder()
				.memberIdType(type).memberId(memberId).organismName("Homo sapiens").organismTaxId(9606).build();
		
		UniRefMember member2 = new UniRefMemberBuilder()
				.memberIdType(UniRefMemberIdType.UNIPARC).memberId("UPI0000321").organismName("Homo sapiens").organismTaxId(9606).build();
		
		List<UniRefMember> members = Arrays.asList(member, member2);
		UniRefEntry entry = new UniRefEntryBuilder()
				.members(members)
				.build();
		assertEquals(members, entry.getMembers());
	}

	@Test
	void testAddUnirefMember() {
		String memberId = "P12345";
		UniRefMemberIdType type =UniRefMemberIdType.UNIPROTKB;
		
		UniRefMember member = new UniRefMemberBuilder()
				.memberIdType(type).memberId(memberId).organismName("Homo sapiens").organismTaxId(9606).build();
		
		UniRefEntry entry = new UniRefEntryBuilder()
				.addMember(member)
				.build();
		assertEquals(Arrays.asList(member), entry.getMembers());
	}

}

