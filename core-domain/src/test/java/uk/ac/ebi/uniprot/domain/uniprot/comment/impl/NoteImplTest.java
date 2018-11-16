package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidenceCode;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;
import uk.ac.ebi.uniprot.domain.uniprot.impl.EvidenceImpl;
import uk.ac.ebi.uniprot.domain.uniprot.impl.EvidencedValueImpl;

class NoteImplTest {

	@Test
	void testNoteImpl() {
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
		
		NoteImpl note = new NoteImpl(texts);
		assertEquals(texts, note.getTexts());
		TestHelper.verifyJson(note);
	}
	@Test
	void testNoteImplEmpty() {
		List<EvidencedValue> texts =new ArrayList<>();
		NoteImpl note = new NoteImpl(texts);
		assertEquals(texts, note.getTexts());
		TestHelper.verifyJson(note);
	}

}
