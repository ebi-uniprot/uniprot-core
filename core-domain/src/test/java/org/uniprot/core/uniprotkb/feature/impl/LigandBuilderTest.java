package org.uniprot.core.uniprotkb.feature.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.feature.Ligand;
import org.uniprot.core.uniprotkb.feature.LigandPart;

class LigandBuilderTest {

	@Test
	void canSetName() {
		Ligand Ligand = new LigandBuilder().name("Some name").build();
		assertEquals("Some name", Ligand.getName());
	}

	@Test
	void canSetId() {
		Ligand Ligand = new LigandBuilder().id("ChEBI:CHEBI:3234").build();
		assertEquals("ChEBI:CHEBI:3234", Ligand.getId());
	}

	@Test
	void canSetLabel() {
		Ligand Ligand = new LigandBuilder().label("Some label").build();
		assertEquals("Some label", Ligand.getLabel());
	}

	@Test
	void canSetNote() {
		Ligand Ligand = new LigandBuilder().note("some note").build();
		assertEquals("some note", Ligand.getNote());
	}


	
	@Test
	void canCreateBuilderFromInstance() {
		Ligand obj = new LigandBuilder().build();
		LigandBuilder builder = LigandBuilder.from(obj);
		assertNotNull(builder);
	}

	@Test
	void defaultBuild_objsAreEqual() {
		Ligand obj = new LigandBuilder().build();
		Ligand obj2 = new LigandBuilder().build();
		assertTrue(obj.equals(obj2) && obj2.equals(obj));
		assertEquals(obj.hashCode(), obj2.hashCode());
	}

}
