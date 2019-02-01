package uk.ac.ebi.uniprot.xmlparser.uniprot;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.Sequence;
import uk.ac.ebi.uniprot.domain.builder.SequenceBuilder;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.SequenceType;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SequenceConverterTest {

	@Test
	void test() {
		   String sequence =
	                "MALYSISKPVGSKINKHSYQDENTLVGKQALSKGTEKTKLSTNFEINLPRRTVLSDVSNV"
	              + "GKNNADEKDTKKAKRSFDESNLSTNEEADKPVESKFVKKLKVYSKNADPSVETLQKDRVS"
	              + "NVDDHLSSNPLMAEEYAPEIFEYIRKLDLKCLPNPKYMDQQKELTWKMREILNEWLVEIH"
	              + "SNFCLMPETLYLAVNIIDRFLSRRSCSLSKFQLTGITALLIASKYEEVMCPSIQNFVYMT"
	              + "DGAFTVEDVCVAERYMLNVLNFDLSYPSPLNFLRKISQAEGYDAQTRTLGKYLTEIYLFD"
	              + "HDLLRYPMSKIAAAAMYLSRRLLRRGPWTPKLVESSGGYEEHELKEIAYIMLHYHNKPLE"
	              + "HKAFFQKYSSKRFLKASIFVHQLVRQRYSVNRTDDDDLQSEPSSSLTNDGH";
		   Sequence uniSeq = new SequenceBuilder(sequence).build();
		   SequenceConverter converter = new SequenceConverter();
		   SequenceType xmlSeq = converter.toXml(uniSeq);
		   System.out.println(UniProtXmlTestHelper.toXmlString(xmlSeq, SequenceType.class, "sequence"));
		   Sequence converted = converter.fromXml(xmlSeq);
		   assertEquals(uniSeq, converted);
	}

}
