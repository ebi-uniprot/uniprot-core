package uk.ac.ebi.uniprot.xmlparser.uniprot.citation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.domain.uniprot.ReferenceComment;
import uk.ac.ebi.uniprot.domain.uniprot.ReferenceCommentType;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtReferenceFactory;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.SourceDataType;
import uk.ac.ebi.uniprot.xmlparser.uniprot.EvidenceIndexMapper;
import uk.ac.ebi.uniprot.xmlparser.uniprot.UniProtXmlTestHelper;

class ReferenceCommentConverterTest {

	@Test
	void test() {
		List<Evidence> evidences = createEvidences();
		   List<ReferenceComment> refComments = new ArrayList<>();
	        refComments.add(UniProtReferenceFactory.INSTANCE
	                                .createReferenceComment(ReferenceCommentType.STRAIN, "S1", evidences));
	        refComments.add(UniProtReferenceFactory.INSTANCE
                    .createReferenceComment(ReferenceCommentType.STRAIN, "S241", Collections.emptyList()));
	      
	        refComments.add(UniProtReferenceFactory.INSTANCE
                    .createReferenceComment(ReferenceCommentType.PLASMID, "pls11", evidences));
	        refComments.add(UniProtReferenceFactory.INSTANCE
                    .createReferenceComment(ReferenceCommentType.TISSUE, "S11", evidences));
	        
	        ReferenceCommentConverter converter =new ReferenceCommentConverter(new EvidenceIndexMapper());
	        
	        SourceDataType sourceType =converter.toXml(refComments);
	    	System.out.println(UniProtXmlTestHelper.toXmlString(sourceType, SourceDataType.class, "source"));
	    	List<ReferenceComment> converted = converter.fromXml(sourceType)  ;
	    	assertEquals(refComments, converted);
	        
	}
    private List<Evidence> createEvidences() {
        List<Evidence> evidences = new ArrayList<>();
        evidences.add(UniProtFactory.INSTANCE.createEvidence("ECO:0000255|PROSITE-ProRule:PRU10028"));
        evidences.add(UniProtFactory.INSTANCE.createEvidence("ECO:0000256|PIRNR:PIRNR001361"));
        return evidences;
    }
}
