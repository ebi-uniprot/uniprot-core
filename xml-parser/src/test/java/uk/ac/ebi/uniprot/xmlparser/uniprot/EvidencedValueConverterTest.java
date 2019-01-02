package uk.ac.ebi.uniprot.xmlparser.uniprot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.EvidencedStringType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ObjectFactory;

class EvidencedValueConverterTest {

	@Test
	void test() {
		Evidence evidence1 = UniProtFactory.INSTANCE.createEvidence("ECO:0000269|PubMed:9060645");
		Evidence evidence2 = UniProtFactory.INSTANCE.createEvidence("ECO:0000269|PubMed:9060647");
		List<Evidence> evids = new ArrayList<>();
		evids.add(evidence1);
		evids.add(evidence2);
		String text = "some text";
		EvidencedValue evValue = UniProtFactory.INSTANCE.createEvidencedValue(text, evids);
		EvidencedValueConverter converter = new EvidencedValueConverter(new EvidenceIndexMapper());
		EvidencedStringType xmlObj = converter.toXml(evValue);
		assertEquals(text, xmlObj.getValue());
		assertEquals(Arrays.asList(1, 2), xmlObj.getEvidence());
		EvidencedValue converted = converter.fromXml(xmlObj);
		assertEquals(evValue, converted);
		converter = new EvidencedValueConverter(new EvidenceIndexMapper(), new ObjectFactory(), true);
		xmlObj = converter.toXml(evValue);
		System.out.println(UniProtXmlTestHelper.toXmlString(xmlObj, EvidencedStringType.class, "text"));
		assertEquals(text + ".", xmlObj.getValue());
		
		
		assertEquals(Arrays.asList(1, 2), xmlObj.getEvidence());
		converted = converter.fromXml(xmlObj);
		assertEquals(evValue, converted);

	}

}
