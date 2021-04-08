package org.uniprot.core.cv.go.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.go.Go;
import org.uniprot.core.cv.go.GoAspect;
import org.uniprot.core.cv.go.GoEvidenceType;

/**
 *
 * @author jluo
 * @date: 8 Apr 2021
 *
*/

class GoImplTest {

	@Test
	void defaultConstructorForJsonDeserialization() {
		GoImpl go = new GoImpl();
		assertNotNull(go);
	}

	@Test
	void fullConstructorSameAsBuildFrom() {
		String goId="GO:0001212";
		String name ="some name";
		GoAspect aspect = GoAspect.FUNCTION;
		GoEvidenceType goEvidenceType = GoEvidenceType.HDA;
		String goEvidenceSource ="UniProtKB";
		GoImpl go = new GoImpl( goId,  name,  aspect,  goEvidenceType,  goEvidenceSource);
		assertEquals(goId, go.getId());
		assertEquals(name, go.getName());
		assertEquals(aspect, go.getAspect());
		assertEquals(goEvidenceType, go.getGoEvidenceType());
		assertEquals(goEvidenceSource, go.getGoEvidenceSource());
		Go gofrom = GoBuilder.from(go).build();
		assertEquals(go, gofrom);
	}

}

