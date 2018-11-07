package uk.ac.ebi.uniprot.domain.uniprot.evidence;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

class EvidenceTypesTest {

	@Test
	void testEvidenceTypes() {
		assertNotNull(EvidenceTypes.INSTANCE);
	}

	@Test
	void testGetAllTypes() {
		List<EvidenceType> types = EvidenceTypes.INSTANCE.getAllTypes();
		assertEquals(41, types.size());
	}

	@Test
	void testGetTypeEMBL() {
		Optional<EvidenceType> opType = EvidenceTypes.INSTANCE.getType("EMBL");
		verify(opType, true, "EMBL", "EMBL", "https://www.ebi.ac.uk/ena/data/view/%value");
	}
	
	@Test
	void testGetTypeHamapRule() {
		Optional<EvidenceType> opType = EvidenceTypes.INSTANCE.getType("HAMAP-Rule");
		verify(opType, true, "HAMAP-Rule", "HAMAP-Rule", "https://www.uniprot.org/unirule/%value");
	}
	@Test
	void testGetTypePubMed() {
		Optional<EvidenceType> opType = EvidenceTypes.INSTANCE.getType("PubMed");
		verify(opType, true, "PubMed", "PubMed", "https://www.uniprot.org/citations/%value");
	}
	
	
	@Test
	void testGetTypeReference() {
		Optional<EvidenceType> opType = EvidenceTypes.INSTANCE.getType("Reference");
		verify(opType, false, "HAMAP-Rule", "HAMAP-Rule", "https://www.uniprot.org/unirule/%value");
	}
	
	private void verify(Optional<EvidenceType> opType, boolean isPresent, String name, String displayName, String url) {
		assertEquals(isPresent, opType.isPresent());
		if(isPresent) {
			EvidenceType type = opType.get();
			assertEquals(name, type.getName());
			assertEquals(displayName, type.getDisplayName());
			assertEquals(url, type.getUriLink());
		}
	}

}
