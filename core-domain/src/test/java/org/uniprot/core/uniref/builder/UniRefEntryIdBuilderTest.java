package org.uniprot.core.uniref.builder;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniref.UniRefEntryId;

/**
 *
 * @author jluo
 * @date: 12 Aug 2019
 *
*/

class UniRefEntryIdBuilderTest {

	@Test
	void testUniRefEntryIdBuilderValid() {
		String id = "UniRef50_P03923";
		UniRefEntryId entryId = new UniRefEntryIdBuilder(id).build();
		assertEquals(id, entryId.getValue());
		assertTrue(entryId.isValidId());
		
		id = "UniRef90_P03923";
		 entryId = new UniRefEntryIdBuilder(id).build();
		assertEquals(id, entryId.getValue());
		assertTrue(entryId.isValidId());
		
		id = "UniRef100_P03923";
		 entryId = new UniRefEntryIdBuilder(id).build();
		assertEquals(id, entryId.getValue());
		assertTrue(entryId.isValidId());
	}
	@Test
	void testUniRefEntryIdBuilderInvalid() {
		String id = "UniRef55_P03923";
		UniRefEntryId entryId = new UniRefEntryIdBuilder(id).build();
		assertEquals(id, entryId.getValue());
		assertFalse(entryId.isValidId());
		
		id = "UniRef90_P0392";
		 entryId = new UniRefEntryIdBuilder(id).build();
		assertEquals(id, entryId.getValue());
		assertFalse(entryId.isValidId());
	}
}

