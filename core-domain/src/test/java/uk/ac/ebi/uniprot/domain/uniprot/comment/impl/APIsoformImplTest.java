package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.comment.*;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidenceCode;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;
import uk.ac.ebi.uniprot.domain.uniprot.impl.EvidenceImpl;
import uk.ac.ebi.uniprot.domain.uniprot.impl.EvidencedValueImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class APIsoformImplTest {

	@Test
	void testIsoformNameImpl() {
		String name="some name";
		List<Evidence> evidences = createEvidences();
		IsoformName isoformName =APIsoformImpl.createIsoformName(name, evidences);
		assertEquals(name, isoformName.getValue());
		assertEquals(evidences, isoformName.getEvidences());
		TestHelper.verifyJson(isoformName);
	}
	@Test 
	void testIsoformId() {
		String name="some id";
		IsoformId isoId = APIsoformImpl.createIsoformId(name);
		assertEquals(name, isoId.getValue());
		TestHelper.verifyJson(isoId);
	}
	@Test
	void testAPIsoformFull() {
		String name="some name";
		List<Evidence> evidences = createEvidences();
		IsoformName isoformName =APIsoformImpl.createIsoformName(name, evidences);
		List<IsoformName> synonyms =createSynonyms();
		Note note = createNote();
		List<IsoformId> isoformIds = createIsoformIds() ;
		List<String> sequenceIds =Arrays.asList("seq 1", "seq 2");
		
		APIsoform apIsoform= 
		new APIsoformImpl(isoformName, synonyms, note, isoformIds,
				 sequenceIds, IsoformSequenceStatus.DESCRIBED) ;
		assertEquals(isoformName, apIsoform.getName());
		assertEquals(synonyms, apIsoform.getSynonyms());
		assertEquals(note, apIsoform.getNote());
		assertEquals(isoformIds, apIsoform.getIsoformIds());
		assertEquals(sequenceIds, apIsoform.getSequenceIds());
		assertEquals(IsoformSequenceStatus.DESCRIBED, apIsoform.getIsoformSequenceStatus());
		TestHelper.verifyJson(apIsoform);
	}
	
	private  List<IsoformId> createIsoformIds(){
		List<IsoformId> ids =new ArrayList<>();
		ids.add( APIsoformImpl.createIsoformId("id 1"));
		ids.add( APIsoformImpl.createIsoformId("id 2"));
		return ids;
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
	private List<IsoformName> createSynonyms(){
		List<IsoformName>  synonyms = new ArrayList<>();
		List<Evidence> evidences = createEvidences();
		synonyms.add(APIsoformImpl.createIsoformName("Syn 1", evidences));
		synonyms.add(APIsoformImpl.createIsoformName("Syn 2", evidences));
		return synonyms;
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
