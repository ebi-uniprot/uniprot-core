package org.uniprot.core.interpro.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.interpro.InterProMatch;
import org.uniprot.core.interpro.InterProMatchContainer;
import org.uniprot.core.interpro.MethodType;
import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionBuilder;

/**
 *
 * @author jluo
 * @date: 14 Apr 2021
 *
*/

class InterProMatchContainerBuilderTest {

	@Test
	void testFrom() {
		long id =10l;
		UniProtKBAccession uniprotKBAccession = new UniProtKBAccessionBuilder("P12345").build();
	
		InterProMatch match1 = new InterProMatchBuilder().methodType(MethodType.PANTHER).build();
		InterProMatch match2 = new InterProMatchBuilder().methodType(MethodType.GENE3D).build();
		List<InterProMatch> matches = List.of(match1, match2);
		InterProMatchContainer obj = new InterProMatchContainerBuilder().id(id)
				.uniProtAccession(uniprotKBAccession)
				.interProMatchesSet(matches)
				.build();
		assertEquals(id, obj.getId());
		assertEquals(uniprotKBAccession, obj.getUniProtAccession());
		assertEquals(matches, obj.getInterProMatches());
		InterProMatchContainer objFrom = 	InterProMatchContainerBuilder.from(obj).build();
		assertEquals(obj, objFrom);
		
	}

	@Test
	void testId() {
		long id =10l;
		InterProMatchContainer obj = new InterProMatchContainerBuilder().id(id).build();
		assertEquals(id, obj.getId());
		assertNull(obj.getUniProtAccession());
		assertTrue(obj.getInterProMatches().isEmpty());
	}

	@Test
	void testUniProtAccession() {
		long id =10l;
		UniProtKBAccession uniprotKBAccession = new UniProtKBAccessionBuilder("P12345").build();
		InterProMatchContainer obj = new InterProMatchContainerBuilder().id(id)
				.uniProtAccession(uniprotKBAccession).build();
		assertEquals(id, obj.getId());
		assertEquals(uniprotKBAccession, obj.getUniProtAccession());
		assertTrue(obj.getInterProMatches().isEmpty());
	}

	@Test
	void testInterProMatchesSet() {
		long id =10l;
		UniProtKBAccession uniprotKBAccession = new UniProtKBAccessionBuilder("P12345").build();
	
		InterProMatch match1 = new InterProMatchBuilder().methodType(MethodType.PANTHER).build();
		InterProMatch match2 = new InterProMatchBuilder().methodType(MethodType.GENE3D).build();
		List<InterProMatch> matches = List.of(match1, match2);
		InterProMatchContainer obj = new InterProMatchContainerBuilder().id(id)
				.uniProtAccession(uniprotKBAccession)
				.interProMatchesSet(matches)
				.build();
		assertEquals(id, obj.getId());
		assertEquals(uniprotKBAccession, obj.getUniProtAccession());
		assertEquals(matches, obj.getInterProMatches());
	}

	@Test
	void testInterProMatchesAdd() {
		long id =10l;
		UniProtKBAccession uniprotKBAccession = new UniProtKBAccessionBuilder("P12345").build();
	
		InterProMatch match1 = new InterProMatchBuilder().methodType(MethodType.PANTHER).build();

		List<InterProMatch> matches = List.of(match1);
		InterProMatchContainer obj = new InterProMatchContainerBuilder().id(id)
				.uniProtAccession(uniprotKBAccession)
				.interProMatchesAdd(match1)
				.build();
		assertEquals(id, obj.getId());
		assertEquals(uniprotKBAccession, obj.getUniProtAccession());
		assertEquals(matches, obj.getInterProMatches());
	}

}

