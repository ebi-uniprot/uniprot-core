package uk.ac.ebi.uniprot.xml.uniparc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.domain.Sequence;
import uk.ac.ebi.uniprot.domain.builder.SequenceBuilder;

/**
 *
 * @author jluo
 * @date: 24 May 2019
 *
*/

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
		   uk.ac.ebi.uniprot.xml.jaxb.uniparc.Sequence xmlSeq = converter.toXml(uniSeq);
		   
		   System.out.println(UniParcXmlTestHelper.toXmlString(xmlSeq, uk.ac.ebi.uniprot.xml.jaxb.uniparc.Sequence.class, "sequence"));
		   Sequence converted = converter.fromXml(xmlSeq);
		   assertEquals(uniSeq, converted);
	}

}

