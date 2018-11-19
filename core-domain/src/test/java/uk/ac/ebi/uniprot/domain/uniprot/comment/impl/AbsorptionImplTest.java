package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Absorption;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Note;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidenceCode;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;
import uk.ac.ebi.uniprot.domain.uniprot.impl.EvidenceImpl;
import uk.ac.ebi.uniprot.domain.uniprot.impl.EvidencedValueImpl;

class AbsorptionImplTest {

	@Test
	void testAbsorptionImpl() {
		Note note = createNote();
		List<Evidence> evidences =createEvidences();
		Absorption absorption = 
		new AbsorptionImpl(32,  note,  evidences);
		assertEquals(32, absorption.getMax());
		assertFalse(absorption.isApproximate());
		assertEquals(note, absorption.getNote());
		assertEquals(evidences, absorption.getEvidences());
		TestHelper.verifyJson(absorption);
	}
	@Test
	void testAbsorptionImplNoNote() {
		Note note = null;
		List<Evidence> evidences =null;
		Absorption absorption = 
		new AbsorptionImpl(35, true,  note,  evidences);
		assertEquals(35, absorption.getMax());
		assertTrue(absorption.isApproximate());
		assertEquals(note, absorption.getNote());
		assertTrue( absorption.getEvidences().isEmpty());
		TestHelper.verifyJson(absorption);
		
	}
	private Note createNote() {
		List<EvidencedValue> texts =new ArrayList<>();
		List<Evidence> evidences =new ArrayList<>();
        evidences.add(new EvidenceImpl(
        		EvidenceCode.ECO_0000313,  "Ensembl", "ENSP0001324"
                ));
        evidences.add(new EvidenceImpl(
        		EvidenceCode.ECO_0000256, "PIRNR", "PIRNR001361"
                ));
		texts.add(new EvidencedValueImpl("value 1", evidences ));
		texts.add(UniProtFactory.INSTANCE.createEvidencedValue("value2", Collections.emptyList()));
		return  new NoteImpl(texts);
	}
	
	private List<Evidence> createEvidences(){
		List<Evidence> evidences =new ArrayList<>();
        evidences.add(new EvidenceImpl(
        		EvidenceCode.ECO_0000313,  "Ensembl", "ENSP0001324"
                ));
        evidences.add(new EvidenceImpl(
        		EvidenceCode.ECO_0000256, "PIRNR", "PIRNR001361"
                ));
        return evidences;
	}
}
