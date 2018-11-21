package uk.ac.ebi.uniprot.domain.uniprot.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.uniprot.ReferenceComment;
import uk.ac.ebi.uniprot.domain.uniprot.ReferenceCommentType;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidenceCode;

class ReferenceCommentImplTest {

	@Test
	void testReferenceCommentImpl() {
		ReferenceCommentType type = ReferenceCommentType.STRAIN;
		String value = "some strain";
		 List<Evidence> evidences =new ArrayList<>();
	        evidences.add(new EvidenceImpl(
	        		EvidenceCode.ECO_0000313,  "Ensembl", "ENSP0001324"
	                ));
	        evidences.add(new EvidenceImpl(
	        		EvidenceCode.ECO_0000256, "PIRNR", "PIRNR001361"
	                ));
		ReferenceComment rc = new  ReferenceCommentImpl( type,  value,  evidences) ;
		assertEquals(type, rc.getType());
		assertEquals(value, rc.getValue());
		assertEquals(evidences, rc.getEvidences());
		TestHelper.verifyJson(rc);
		
	}
	@Test
	void testReferenceCommentImplNoEvidence() {
		ReferenceCommentType type = ReferenceCommentType.PLASMID;
		String value = "some value";
		 List<Evidence> evidences = null;
		ReferenceComment rc = new  ReferenceCommentImpl( type,  value,  evidences) ;
		assertEquals(type, rc.getType());
		assertEquals(value, rc.getValue());
		assertTrue( rc.getEvidences().isEmpty());
		TestHelper.verifyJson(rc);
		
		
	}
}
