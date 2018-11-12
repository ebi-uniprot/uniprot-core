package uk.ac.ebi.uniprot.domain.uniprot.description.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.uniprot.description.Name;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidenceCode;
import uk.ac.ebi.uniprot.domain.uniprot.impl.EvidenceImpl;

class NameImplTest {

	@Test
	void testNameImpl() {
		String val ="some value";
		List<Evidence> evidences =new ArrayList<>();
        evidences.add(new EvidenceImpl(
        		EvidenceCode.ECO_0000313,  "Ensembl", "ENSP0001324"
                ));
        evidences.add(new EvidenceImpl(
        		EvidenceCode.ECO_0000256, "PIRNR", "PIRNR001361"
                ));
        
		Name nameObj =new NameImpl(val, evidences);
		assertEquals(val, nameObj.getValue());
		assertTrue(nameObj.isValid());
		assertEquals(evidences, nameObj.getEvidences());
		TestHelper.verifyJson(nameObj);
	}

	@Test
	void testNameImplNoEv() {
		String val ="some value";
		List<Evidence> evidences =new ArrayList<>();
      
		Name nameObj =new NameImpl(val, evidences);
		assertEquals(val, nameObj.getValue());
		assertTrue(nameObj.isValid());
		assertEquals(evidences, nameObj.getEvidences());
		TestHelper.verifyJson(nameObj);
	}
	@Test
	void testNameImplNoEvInValid() {
		String val ="";
		List<Evidence> evidences =new ArrayList<>();
      
		Name nameObj =new NameImpl(val, evidences);
		assertEquals(val, nameObj.getValue());
		assertFalse(nameObj.isValid());
		assertEquals(evidences, nameObj.getEvidences());
		TestHelper.verifyJson(nameObj);
	}

}
