package uk.ac.ebi.uniprot.domain.uniprot.feature.impl;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.uniprot.feature.AlternativeSequence;

class AlternativeSequenceImplTest {

	@Test
	void testFull() {

		AlternativeSequence as =new AlternativeSequenceImpl("AB", Arrays.asList("DC", "SDGASS"));

		assertEquals("AB", as.getOriginalSequence());
		assertEquals(Arrays.asList("DC", "SDGASS"), as.getAlternativeSequences());
		TestHelper.verifyJson(as);
	}

	@Test
	void testMissing() {

		AlternativeSequence as =new AlternativeSequenceImpl("AB", Collections.emptyList());

		assertEquals("AB", as.getOriginalSequence());
		assertTrue(as.getAlternativeSequences().isEmpty());

		
		TestHelper.verifyJson(as);
	}

}
