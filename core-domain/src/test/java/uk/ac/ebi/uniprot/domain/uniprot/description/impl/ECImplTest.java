package uk.ac.ebi.uniprot.domain.uniprot.description.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.domain.common.TestHelper;
import uk.ac.ebi.uniprot.domain.uniprot.description.EC;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidenceCode;
import uk.ac.ebi.uniprot.domain.uniprot.impl.EvidenceImpl;

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
		TestHelper.writeJson(ecObj);
	}
	@Test
	void testECImplNoEv() {
		String ec ="4.6.1.2";
		List<Evidence> evidences =new ArrayList<>();
     
		EC ecObj =new ECImpl(ec, evidences);
		assertEquals(ec, ecObj.getValue());
		assertTrue(ecObj.isValid());
		assertEquals(evidences, ecObj.getEvidences());
		TestHelper.writeJson(ecObj);
	}

}
