package uk.ac.ebi.uniprot.domain.uniprot.evidence;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.EnumSet;

import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidenceCode.Category;

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
		assertEquals("Combinatorial", code.getName());
		assertEquals(EnumSet.of(EvidenceCode.Category.MANUAL), code.getCategories());
	}
	@Test
	void testExperimenalManual() {
		EvidenceCode code = EvidenceCode.ECO_0000303;
		assertEquals("ECO:0000303", code.getCode());
		assertEquals("Non-traceable author statement", code.getName());
		assertEquals(EnumSet.of(Category.EXPERMIENTAL, Category.MANUAL), code.getCategories());
	}
}
