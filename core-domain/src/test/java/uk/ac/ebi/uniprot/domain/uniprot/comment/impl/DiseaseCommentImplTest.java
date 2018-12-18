package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.impl.DBCrossReferenceImpl;
import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.comment.*;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.DiseaseBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

class DiseaseCommentImplTest {

	@Test
	void testDiseaseCommentImpl() {

	        String val ="some description";
	        List<Evidence> evidences =  createEvidences();
	        DiseaseDescription description = DiseaseBuilder.createDiseaseDescription(val,evidences);
	        DiseaseReferenceType referenceType = DiseaseReferenceType.MIM;
	        String referenceId = "3124";
	        DBCrossReference<DiseaseReferenceType> reference = new DBCrossReferenceImpl<>(referenceType, referenceId);
	        String diseaseId = "someId";
	        String diseaseAc =  "I-00742";
	        Disease disease =new DiseaseImpl(
	        		 diseaseId, diseaseAc, "someAcron", description, reference);
	    		
	        Note note =new NoteImpl(createEvidenceValues());
	        
	        DiseaseComment comment = new DiseaseCommentImpl(disease, note);
	       assertEquals(note, comment.getNote());
	        assertEquals(CommentType.DISEASE, comment.getCommentType());
	        assertEquals(disease, comment.getDisease());
	        TestHelper.verifyJson(comment);
	}
	 private List<Evidence> createEvidences() {
	        List<Evidence> evidences = new ArrayList<>();
	        evidences.add(UniProtFactory.INSTANCE.createEvidence("ECO:0000255|PROSITE-ProRule:PRU10028"));
	        evidences.add(UniProtFactory.INSTANCE.createEvidence("ECO:0000256|PIRNR:PIRNR001361"));
	        return evidences;
	    }
	    private List<EvidencedValue> createEvidenceValues() {
	        List<EvidencedValue> evidencedValues = new ArrayList<>();
	        evidencedValues.add(UniProtFactory.INSTANCE.createEvidencedValue("value1", Collections.emptyList()));
	        evidencedValues.add(UniProtFactory.INSTANCE.createEvidencedValue("value2", Collections.emptyList()));
	        return evidencedValues;
	    }
}
