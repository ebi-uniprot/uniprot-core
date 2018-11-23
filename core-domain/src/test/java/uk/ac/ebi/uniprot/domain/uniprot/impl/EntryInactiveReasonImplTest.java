package uk.ac.ebi.uniprot.domain.uniprot.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.uniprot.EntryInactiveReason;
import uk.ac.ebi.uniprot.domain.uniprot.InactiveReasonType;

class EntryInactiveReasonImplTest {

	@Test
	void testEntryInactiveReasonImplDefMerge() {
		EntryInactiveReason reason = new EntryInactiveReasonImpl(InactiveReasonType.MERGED, Arrays.asList("P12345"));
		assertEquals(InactiveReasonType.MERGED, reason.getInactiveReasonType());
		assertEquals(Arrays.asList("P12345"), reason.getMergeDemergeTo());
		TestHelper.verifyJson(reason);
	}
	
	@Test
	void testEntryInactiveReasonImplMerge() {
		EntryInactiveReason reason = new EntryInactiveReasonImpl(InactiveReasonType.DEMERGED, Arrays.asList("P12345", "P12347"));
		assertEquals(InactiveReasonType.DEMERGED, reason.getInactiveReasonType());
		assertEquals(Arrays.asList("P12345",  "P12347"), reason.getMergeDemergeTo());
		TestHelper.verifyJson(reason);
	}

	@Test
	void testEntryInactiveReasonImplDelete() {
		EntryInactiveReason reason = new EntryInactiveReasonImpl(InactiveReasonType.DELETED, null);
		assertEquals(InactiveReasonType.DELETED, reason.getInactiveReasonType());
		assertTrue( reason.getMergeDemergeTo().isEmpty());
		TestHelper.verifyJson(reason);
	}
}
