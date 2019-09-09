package org.uniprot.core.json.parser.uniref;

import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Sequence;
import org.uniprot.core.builder.SequenceBuilder;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.uniparc.builder.UniParcIdBuilder;
import org.uniprot.core.uniprot.builder.UniProtAccessionBuilder;
import org.uniprot.core.uniprot.taxonomy.Taxonomy;
import org.uniprot.core.uniprot.taxonomy.builder.TaxonomyBuilder;
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

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 * @author jluo
 * @date: 14 Aug 2019
 *
*/

class UniRefTest {
	@Test
	void testUniRefMember() {
		UniRefMember member= createMember();
		ValidateJson.verifyJsonRoundTripParser(UniRefEntryJsonConfig.getInstance().getFullObjectMapper(), member);
		
		try {
	           ObjectMapper mapper = UniRefEntryJsonConfig.getInstance().getSimpleObjectMapper();
	           String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(member);
	           System.out.println(json);
	       }catch(Exception e) {
	           fail(e.getMessage());
	       }
		
	}
	private UniRefMember createMember() {
		String memberId = "P12345_HUMAN";
		int length=312;
		String pName ="some protein name"; 
		String upi = "UPI0000083A08";
		
		UniRefMemberIdType type =UniRefMemberIdType.UNIPROTKB;
		UniRefMember member = new UniRefMemberBuilder()
				.memberIdType(type).memberId(memberId)
				.organismName("Homo sapiens")
				.organismTaxId(9606)
				.sequenceLength(length)
				.proteinName(pName)
				.uniparcId(new UniParcIdBuilder(upi).build())
				.accession(new UniProtAccessionBuilder("P12345").build())
				.uniref100Id(new UniRefEntryIdBuilder("UniRef100_P03923").build())
				.uniref90Id(new UniRefEntryIdBuilder("UniRef90_P03943").build())
				.uniref50Id(new UniRefEntryIdBuilder("UniRef50_P03973").build())
			
				.build();
		return member;
	}
	@Test
	void testRepresentativeMember() {
		RepresentativeMember member= createReprestativeMember();
		ValidateJson.verifyJsonRoundTripParser(UniRefEntryJsonConfig.getInstance().getFullObjectMapper(), member);
		
		try {
	           ObjectMapper mapper = UniRefEntryJsonConfig.getInstance().getSimpleObjectMapper();
	           String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(member);
	           System.out.println(json);
	       }catch(Exception e) {
	           fail(e.getMessage());
	       }
	}
	private RepresentativeMember createReprestativeMember() {
		String seq = "MVSWGRFICLVVVTMATLSLARPSFSLVEDDFSAGSADFAFWERDGDSDGFDSHSDJHETRHJREH";
		Sequence sequence = new SequenceBuilder(seq).build();
		String memberId = "P12345_HUMAN";
		int length=312;
		String pName ="some protein name"; 
		String upi = "UPI0000083A08";
		
		UniRefMemberIdType type =UniRefMemberIdType.UNIPROTKB;
		
		RepresentativeMember member = new RepresentativeMemberBuilder()
				.memberIdType(type).memberId(memberId)
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
	@Test
	void testUniRefEntry() {
		String id = "UniRef50_P03923";
		UniRefType type = UniRefType.UniRef100;
	
		UniRefEntryId entryId = new UniRefEntryIdBuilder(id).build();
		
		UniRefEntry entry = new UniRefEntryBuilder()
				.id(entryId)
				.name("Some UniRef Name")
				.updated(LocalDate.now())
				.entryType(type)
				.commonTaxonId(9606l)
				.commonTaxon("Homo sapiens")
				.representativeMember(createReprestativeMember())
				.addMember(createMember())
				.addGoTerm(new GoTermBuilder().type(GoTermType.COMPONENT).id("GO:0044444").build())
				.addGoTerm(new GoTermBuilder().type(GoTermType.FUNCTION).id("GO:0044459").build())
				.addGoTerm(new GoTermBuilder().type(GoTermType.PROCESS).id("GO:0032459").build())
				.memberCount(2)
				.build();
		ValidateJson.verifyJsonRoundTripParser(UniRefEntryJsonConfig.getInstance().getFullObjectMapper(), entry);
		
		try {
	           ObjectMapper mapper = UniRefEntryJsonConfig.getInstance().getSimpleObjectMapper();
	           String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(entry);
	           System.out.println(json);
	       }catch(Exception e) {
	           fail(e.getMessage());
	       }
	}
}

