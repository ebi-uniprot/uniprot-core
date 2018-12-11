package uk.ac.ebi.uniprot.domain.uniprot.description.impl;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.uniprot.description.EC;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidenceCode;
import uk.ac.ebi.uniprot.domain.uniprot.impl.EvidenceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ECImplTest {

	@Test
	void testECImpl() {
		String ec ="4.6.1.2";
		List<Evidence> evidences =new ArrayList<>();
        evidences.add(new EvidenceImpl(
        		EvidenceCode.ECO_0000313,  "Ensembl", "ENSP0001324"
                ));
        evidences.add(new EvidenceImpl(
        		EvidenceCode.ECO_0000256, "PIRNR", "PIRNR001361"
                ));
        
		EC ecObj =new ECImpl(ec, evidences);
		assertEquals(ec, ecObj.getValue());
		assertTrue(ecObj.isValid());
		assertEquals(evidences, ecObj.getEvidences());
		TestHelper.verifyJson(ecObj);
	}
	@Test
	void testECImplNoEv() {
		String ec ="4.6.1.2";
		List<Evidence> evidences =new ArrayList<>();
     
		EC ecObj =new ECImpl(ec, evidences);
		assertEquals(ec, ecObj.getValue());
		assertTrue(ecObj.isValid());
		assertEquals(evidences, ecObj.getEvidences());
		TestHelper.verifyJson(ecObj);
	}

}
