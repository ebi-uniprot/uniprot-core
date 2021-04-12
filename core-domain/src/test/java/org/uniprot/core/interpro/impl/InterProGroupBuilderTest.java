package org.uniprot.core.interpro.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.interpro.Abstract;
import org.uniprot.core.interpro.InterProAc;
import org.uniprot.core.interpro.InterProGroup;
import org.uniprot.core.interpro.InterProType;
import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionBuilder;

/**
 *
 * @author jluo
 * @date: 12 Apr 2021
 *
*/

class InterProGroupBuilderTest {


	@Test
	void testFrom() {
		InterProType type = InterProType.BINDING_SITE;
		String name ="some name";
		String shortName ="some short name";
		Abstract entryAbstract =new AbstractBuilder("some abstract").build();
		InterProAc entryId = new InterProAcBuilder("IPR011992").build();
		UniProtKBAccession accession = new UniProtKBAccessionBuilder("P12345").build();
		InterProGroup obj = new InterProGroupBuilder().entryAbstract(entryAbstract)
				.name(name)
				.shortName(shortName)
				.interProAc(entryId)
				.uniprotAccession(accession)
				.type(type)
				.build();
		InterProGroup obj1 =InterProGroupBuilder.from(obj).build();
		assertEquals(obj, obj1);
	}

	@Test
	void testEntryAbstract() {
		Abstract entryAbstract =new AbstractBuilder("some abstract").build();
		InterProGroup obj = new InterProGroupBuilder().entryAbstract(entryAbstract).build();
		assertEquals(entryAbstract, obj.getEntryAbstract());
		assertNull(obj.getInterProAc());
		assertNull(obj.getName());
		assertNull(obj.getShortName());
		assertNull(obj.getType());
		assertNull(obj.getUniProtAccession());
	}

	@Test
	void testName() {
		String name ="some name";
		Abstract entryAbstract =new AbstractBuilder("some abstract").build();
		InterProGroup obj = new InterProGroupBuilder().entryAbstract(entryAbstract)
				.name(name)
				.build();
		assertEquals(entryAbstract, obj.getEntryAbstract());
		assertNull(obj.getInterProAc());
		assertEquals(name, obj.getName());
		assertNull(obj.getShortName());
		assertNull(obj.getType());
		assertNull(obj.getUniProtAccession());
	}

	@Test
	void testShortName() {
		String name ="some name";
		String shortName ="some short name";
		Abstract entryAbstract =new AbstractBuilder("some abstract").build();
		InterProGroup obj = new InterProGroupBuilder().entryAbstract(entryAbstract)
				.name(name)
				.shortName(shortName)
				.build();
		assertEquals(entryAbstract, obj.getEntryAbstract());
		assertNull(obj.getInterProAc());
		assertEquals(name, obj.getName());
		assertEquals(shortName, obj.getShortName());
		assertNull(obj.getType());
		assertNull(obj.getUniProtAccession());
	}

	@Test
	void testInterProAc() {
		String name ="some name";
		String shortName ="some short name";
		Abstract entryAbstract =new AbstractBuilder("some abstract").build();
		InterProAc entryId = new InterProAcBuilder("IPR011992").build();

		InterProGroup obj = new InterProGroupBuilder().entryAbstract(entryAbstract)
				.name(name)
				.shortName(shortName)
				.interProAc(entryId)
				.build();
		assertEquals(entryAbstract, obj.getEntryAbstract());
		assertEquals(entryId, obj.getInterProAc());
		assertEquals(name, obj.getName());
		assertEquals(shortName, obj.getShortName());
		assertNull(obj.getType());
		assertNull(obj.getUniProtAccession());
	}

	@Test
	void testUniprotAccession() {
		String name ="some name";
		String shortName ="some short name";
		Abstract entryAbstract =new AbstractBuilder("some abstract").build();
		InterProAc entryId = new InterProAcBuilder("IPR011992").build();
		UniProtKBAccession accession = new UniProtKBAccessionBuilder("P12345").build();
		InterProGroup obj = new InterProGroupBuilder().entryAbstract(entryAbstract)
				.name(name)
				.shortName(shortName)
				.interProAc(entryId)
				.uniprotAccession(accession)
				.build();
		assertEquals(entryAbstract, obj.getEntryAbstract());
		assertEquals(entryId, obj.getInterProAc());
		assertEquals(name, obj.getName());
		assertEquals(shortName, obj.getShortName());
		assertNull(obj.getType());
		assertEquals(accession, obj.getUniProtAccession());
	}

	@Test
	void testType() {
		InterProType type = InterProType.BINDING_SITE;
		String name ="some name";
		String shortName ="some short name";
		Abstract entryAbstract =new AbstractBuilder("some abstract").build();
		InterProAc entryId = new InterProAcBuilder("IPR011992").build();
		UniProtKBAccession accession = new UniProtKBAccessionBuilder("P12345").build();
		InterProGroup obj = new InterProGroupBuilder().entryAbstract(entryAbstract)
				.name(name)
				.shortName(shortName)
				.interProAc(entryId)
				.uniprotAccession(accession)
				.type(type)
				.build();
		assertEquals(entryAbstract, obj.getEntryAbstract());
		assertEquals(entryId, obj.getInterProAc());
		assertEquals(name, obj.getName());
		assertEquals(shortName, obj.getShortName());
		assertEquals(type, obj.getType());
		assertEquals(accession, obj.getUniProtAccession());
	}

}

