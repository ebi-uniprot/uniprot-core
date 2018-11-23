package uk.ac.ebi.uniprot.domain.uniprot.description.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.uniprot.description.Flag;
import uk.ac.ebi.uniprot.domain.uniprot.description.FlagType;

class FlagImplTest {

	@Test
	void testFlagImpl() {
		FlagType type = FlagType.FRAGMENT;
		Flag flag = new FlagImpl(type);
		assertEquals(type, flag.getType());
		TestHelper.verifyJson(flag);
		
	}

}
