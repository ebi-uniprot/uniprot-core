package org.uniprot.core.xml.uniprot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Sequence;
import org.uniprot.core.impl.SequenceBuilder;
import org.uniprot.core.xml.jaxb.uniprot.SequenceType;

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
        System.out.println(
                UniProtXmlTestHelper.toXmlString(xmlSeq, SequenceType.class, "sequence"));
        Sequence converted = converter.fromXml(xmlSeq);
        assertEquals(uniSeq, converted);
    }
}
