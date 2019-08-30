package org.uniprot.core.uniprot.evidence;

import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;

/**
 *
 * @author jluo
 * @date: 30 Aug 2019
 *
*/

public class EvidenceTypesTest {

	@Test
	void testGetType() {
		String name ="EMBL";
		EvidenceTypeDetail emblEvidence =EvidenceTypes.INSTANCE.getType(name);
		assertNotNull(emblEvidence);
	}
}

