package uk.ac.ebi.uniprot.xmlparser.uniprot.description;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.domain.uniprot.description.Name;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidenceCode;
import uk.ac.ebi.uniprot.domain.uniprot.factory.ProteinDescriptionFactory;
import uk.ac.ebi.uniprot.domain.uniprot.impl.EvidenceImpl;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.EvidencedStringType;
import uk.ac.ebi.uniprot.xmlparser.uniprot.EvidenceIndexMapper;

class NameConverterTest {

	@Test
	void test() {
		String val ="some value";
		List<Evidence> evidences =new ArrayList<>();
		  evidences.add(new EvidenceImpl(
	        		EvidenceCode.ECO_0000256, "PIRNR", "PIRNR001361"
	                ));
        evidences.add(new EvidenceImpl(
        		EvidenceCode.ECO_0000313,  "Ensembl", "ENSP0001324"
                ));
      
        
		Name nameObj =ProteinDescriptionFactory.INSTANCE.createName(val, evidences);
		 EvidenceIndexMapper evRefMapper = new EvidenceIndexMapper();
		NameConverter converter =new NameConverter(evRefMapper);
		EvidencedStringType xmlObj =converter.toXml(nameObj);
		assertEquals(val, xmlObj.getValue());
		assertEquals(Arrays.asList(1, 2), xmlObj.getEvidence());
		Name converted = converter.fromXml(xmlObj);
		System.out.println(nameObj.toString());
		System.out.println(converted.toString());
		assertEquals(nameObj, converted);
		
	}

}
