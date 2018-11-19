package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.comment.APEventType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.APIsoform;
import uk.ac.ebi.uniprot.domain.uniprot.comment.AlternativeProductsComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.IsoformId;
import uk.ac.ebi.uniprot.domain.uniprot.comment.IsoformName;
import uk.ac.ebi.uniprot.domain.uniprot.comment.IsoformSequenceStatus;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Note;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidenceCode;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;
import uk.ac.ebi.uniprot.domain.uniprot.impl.EvidenceImpl;
import uk.ac.ebi.uniprot.domain.uniprot.impl.EvidencedValueImpl;

class AlternativeProductsCommentImplTest {

	@Test
	void testConstructor() {
	    List<APEventType> events = new ArrayList<>();
        events.add(APEventType.ALTERNATIVE_PROMOTER_USAGE);
        events.add(APEventType.ALTERNATIVE_SPLICING);
        List<APIsoform> isoforms = createIsoforms();
        Note note = createNote();
        AlternativeProductsComment comment =
		new AlternativeProductsCommentImpl(events, isoforms,  note  ) ;
        assertEquals(events, comment.getEvents());
        assertEquals(isoforms, comment.getIsoforms());
        assertEquals(note, comment.getNote());
        assertEquals(CommentType.ALTERNATIVE_PRODUCTS, comment.getCommentType());
        TestHelper.verifyJson(comment);
	}
	
	@Test
	void testConstructorNoNote() {
	    List<APEventType> events = new ArrayList<>();
        events.add(APEventType.ALTERNATIVE_SPLICING);
        List<APIsoform> isoforms = createIsoforms();
        Note note =null;
        AlternativeProductsComment comment =
		new AlternativeProductsCommentImpl(events, isoforms,  note  ) ;
        assertEquals(events, comment.getEvents());
        assertEquals(isoforms, comment.getIsoforms());
        assertEquals(note, comment.getNote());
        TestHelper.verifyJson(comment);
	}
	
	public List<APIsoform> createIsoforms(){
		 List<APIsoform> isoforms = new ArrayList<>();
			List<Evidence> evidences = createEvidences();
			IsoformName isoformName1 =APIsoformImpl.createIsoformName("Name 1", evidences);
			APIsoform apIsoform1= 
					new APIsoformImpl(isoformName1, null, null, Collections.emptyList(), null
							 , IsoformSequenceStatus.DISPLAYED) ;
			isoforms.add(apIsoform1);
		 
		 String name="Name 2";
		
			IsoformName isoformName =APIsoformImpl.createIsoformName(name, evidences);
			List<IsoformName> synonyms =createSynonyms();
			Note note = createNote2();
			List<IsoformId> isoformIds = createIsoformIds() ;
			List<String> sequenceIds =Arrays.asList("seq 1", "seq 2");
			
			APIsoform apIsoform2= 
			new APIsoformImpl(isoformName, synonyms, note, isoformIds,
					 sequenceIds, IsoformSequenceStatus.DESCRIBED) ;
		 
			isoforms.add(apIsoform2);
		 return isoforms;
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
		texts.add(new EvidencedValueImpl("AP comment", evidences ));
		texts.add(UniProtFactory.INSTANCE.createEvidencedValue("AP comment 2", Collections.emptyList()));
		
		return new NoteImpl(texts);
		
	}
	private  List<IsoformId> createIsoformIds(){
		List<IsoformId> ids =new ArrayList<>();
		ids.add( APIsoformImpl.createIsoformId("id 1"));
		ids.add( APIsoformImpl.createIsoformId("id 2"));
		return ids;
	}
	private Note createNote2() {
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
