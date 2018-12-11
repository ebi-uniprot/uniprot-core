package uk.ac.ebi.uniprot.domain.uniprot.impl;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtTaxonId;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidenceCode;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UniProtTaxonIdImplTest {

	@Test
	void testWithEvidence() {
		   long taxid = 9606l;
	        List<Evidence> evidences =new ArrayList<>();
	        evidences.add(new EvidenceImpl(
	        		EvidenceCode.ECO_0000313,  "Ensembl", "ENSP0001324"
	                ));
	        evidences.add(new EvidenceImpl(
	        		EvidenceCode.ECO_0000256, "PIRNR", "PIRNR001361"
	                ));
	        UniProtTaxonId taxonID = new UniProtTaxonIdImpl(taxid, evidences);
	        assertEquals(taxid, taxonID.getTaxonId());
	        assertEquals(evidences, taxonID.getEvidences());
	        TestHelper.verifyJson(taxonID);;
	        
	}
	@Test
	void test() {
		   long taxid = 9606l;
	        List<Evidence> evidences =new ArrayList<>();
	       
	        UniProtTaxonId taxonID = new UniProtTaxonIdImpl(taxid, evidences);
	        assertEquals(taxid, taxonID.getTaxonId());
	        assertEquals(evidences, taxonID.getEvidences());
	        TestHelper.verifyJson(taxonID);
	        
	}
}
