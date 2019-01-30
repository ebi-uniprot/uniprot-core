package uk.ac.ebi.uniprot.xmlparser.uniprot;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.uniprot.Keyword;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.EvidenceCode;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.KeywordType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

class KeywordConverterTest {
	  @Test
	    public void test() {
	        String val = "Transmembrane";
	        String id = "KW-0812";
	        List<Evidence> evidences = createEvidences();
	        EvidenceIndexMapper evRefMapper = new EvidenceIndexMapper(evidences);
	        KeywordConverter converter = new KeywordConverter(evRefMapper);
	        Keyword keyword = UniProtFactory.INSTANCE.createKeyword(id, val, evidences);
	        KeywordType xmlObj = converter.toXml(keyword);
	        
	        assertEquals(id, xmlObj.getId());
	        assertEquals(val, xmlObj.getValue());
	        assertEquals(Arrays.asList(1, 2), xmlObj.getEvidence());
	        System.out.println(UniProtXmlTestHelper.toXmlString(xmlObj, KeywordType.class, "keyword"));
	        
	        Keyword converted = converter.fromXml(xmlObj);
	        assertEquals(keyword, converted);
	    }
	  
	    private List<Evidence> createEvidences(){
	        List<Evidence> evidences = new ArrayList<>();
	        evidences.add(UniProtFactory.INSTANCE.createEvidence(
	                EvidenceCode.ECO_0000256, "PIRNR", "PIRNR001361"));
	        evidences.add(UniProtFactory.INSTANCE.createEvidence(
	                EvidenceCode.ECO_0000313, "Ensembl", "ENSP0001324"));
	       
	        
	        return evidences;
	    }
}
