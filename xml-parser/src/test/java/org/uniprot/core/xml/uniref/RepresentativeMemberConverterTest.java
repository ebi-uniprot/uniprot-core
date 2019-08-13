package org.uniprot.core.xml.uniref;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Sequence;
import org.uniprot.core.builder.SequenceBuilder;
import org.uniprot.core.uniparc.builder.UniParcIdBuilder;
import org.uniprot.core.uniprot.builder.UniProtAccessionBuilder;
import org.uniprot.core.uniprot.taxonomy.Taxonomy;
import org.uniprot.core.uniprot.taxonomy.builder.TaxonomyBuilder;
import org.uniprot.core.uniref.RepresentativeMember;
import org.uniprot.core.uniref.UniRefMemberIdType;
import org.uniprot.core.uniref.builder.RepresentativeMemberBuilder;
import org.uniprot.core.uniref.builder.UniRefEntryIdBuilder;
import org.uniprot.core.xml.jaxb.uniref.MemberType;

/**
 *
 * @author jluo
 * @date: 13 Aug 2019
 *
*/

class RepresentativeMemberConverterTest {

	@Test
	void testToXml() {
		String seq = "MVSWGRFICLVVVTMATLSLARPSFSLVED";
		Sequence sequence = new SequenceBuilder(seq).build();
		String memberId = "P12345";
		int length=312;
		String pName ="some protein name"; 
		String upi = "UPI0000083A08";
		
		UniRefMemberIdType type =UniRefMemberIdType.UNIPROT;
		Taxonomy taxonomy = TaxonomyBuilder.newInstance().taxonId(9606).scientificName("Homo sapiens").build();
		RepresentativeMember member = new RepresentativeMemberBuilder()
				.memberIdType(type).memberId(memberId).taxonomy(taxonomy)
				.sequenceLength(length)
				.proteinName(pName)
				.uniparcId(new UniParcIdBuilder(upi).build())
				.accession(new UniProtAccessionBuilder(memberId).build())
				.uniref100Id(new UniRefEntryIdBuilder("UniRef100_P03923").build())
				.uniref90Id(new UniRefEntryIdBuilder("UniRef90_P03943").build())
				.uniref50Id(new UniRefEntryIdBuilder("UniRef50_P03973").build())
				.isSeed(true)
				.sequence(sequence)
				.build();
		RepresentativeMemberConverter converter = new RepresentativeMemberConverter();
		MemberType xmlMember = converter.toXml(member);
		assertEquals(memberId, xmlMember.getDbReference().getId());
		RepresentativeMember converted = converter.fromXml(xmlMember);
		assertEquals(member, converted);
	}

}

