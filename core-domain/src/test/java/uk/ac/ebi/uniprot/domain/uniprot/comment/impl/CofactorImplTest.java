package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.impl.DBCrossReferenceImpl;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Cofactor;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CofactorReferenceType;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidenceCode;
import uk.ac.ebi.uniprot.domain.uniprot.impl.EvidenceImpl;

class CofactorImplTest {

	@Test
	void testCofactorImpl() {
		String name ="Some cofactor";
		DBCrossReference<CofactorReferenceType>  reference  = new DBCrossReferenceImpl<>(CofactorReferenceType.CHEBI, "ChEBI:213");
		List<Evidence> evidences =createEvidences();
		Cofactor cofactor = new CofactorImpl( name, reference,  evidences);
		assertEquals(name, cofactor.getName());
		assertEquals(reference, cofactor.getCofactorReference());
		assertEquals(evidences, cofactor.getEvidences());
		TestHelper.verifyJson(cofactor);
	}
	@Test
	void testCofactorImplNoEvidence() {
		String name ="Some cofactor";
		DBCrossReference<CofactorReferenceType>  reference  = new DBCrossReferenceImpl<>(CofactorReferenceType.CHEBI, "ChEBI:213");
		List<Evidence> evidences=Collections.emptyList();
		Cofactor cofactor = new CofactorImpl( name, reference,  evidences);
		assertEquals(name, cofactor.getName());
		assertEquals(reference, cofactor.getCofactorReference());
		assertEquals(evidences, cofactor.getEvidences());
		TestHelper.verifyJson(cofactor);
	}
	private List<Evidence> createEvidences() {
		List<Evidence> evidences = new ArrayList<>();
		evidences.add(new EvidenceImpl(EvidenceCode.ECO_0000313, "Ensembl", "ENSP0001324"));
		evidences.add(new EvidenceImpl(EvidenceCode.ECO_0000256, "PIRNR", "PIRNR001361"));
		return evidences;
	}
	
}
