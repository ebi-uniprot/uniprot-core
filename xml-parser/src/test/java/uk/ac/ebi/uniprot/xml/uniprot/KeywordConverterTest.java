package uk.ac.ebi.uniprot.xml.uniprot;

import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.keyword.KeywordCategory;

import uk.ac.ebi.uniprot.domain.uniprot.Keyword;
import uk.ac.ebi.uniprot.domain.uniprot.builder.KeywordBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.KeywordType;
import uk.ac.ebi.uniprot.xml.uniprot.EvidenceIndexMapper;
import uk.ac.ebi.uniprot.xml.uniprot.KeywordConverter;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static uk.ac.ebi.uniprot.xml.uniprot.EvidenceHelper.createEvidences;

class KeywordConverterTest {
	  @Test
	    public void test() {
	        String val = "Transmembrane";
	        String id = "KW-0812";
	        List<Evidence> evidences = createEvidences();
	        EvidenceIndexMapper evRefMapper = new EvidenceIndexMapper(evidences);
	        KeywordConverter converter = new KeywordConverter(evRefMapper);
	        Keyword keyword = new KeywordBuilder(id, val, KeywordCategory.UNKNOWN, evidences).build();
	        KeywordType xmlObj = converter.toXml(keyword);
	        
	        assertEquals(id, xmlObj.getId());
	        assertEquals(val, xmlObj.getValue());
	        assertEquals(Arrays.asList(1, 2), xmlObj.getEvidence());
	        System.out.println(UniProtXmlTestHelper.toXmlString(xmlObj, KeywordType.class, "keyword"));
	        
	        Keyword converted = converter.fromXml(xmlObj);
	        assertEquals(keyword, converted);
	    }
}
