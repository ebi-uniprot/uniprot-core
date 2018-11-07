package uk.ac.ebi.uniprot.domain.uniprot.evidence;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.EnumSet;

import org.junit.jupiter.api.Test;

class EvidenceCodeTest {

	@Test
	void testAuto() {
		EvidenceCode code = EvidenceCode.ECO_0000256;
		assertEquals("ECO:0000256", code.getCode());
		assertEquals("Sequence model", code.getName());
		assertEquals(EnumSet.of(EvidenceCode.Category.AUTOMATIC), code.getCategories());
	}

	@Test
	void testManual() {
		EvidenceCode code = EvidenceCode.ECO_0000244;
		assertEquals("ECO:0000244", code.getCode());
		assertEquals("Sequence model", code.getName());
		assertEquals(EnumSet.of(EvidenceCode.Category.AUTOMATIC), code.getCategories());
	}
}
