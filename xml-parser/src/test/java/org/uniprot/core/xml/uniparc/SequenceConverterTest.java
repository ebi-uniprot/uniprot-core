package org.uniprot.core.xml.uniparc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Sequence;
import org.uniprot.core.impl.SequenceBuilder;

/**
 * @author jluo
 * @date: 24 May 2019
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
        org.uniprot.core.xml.jaxb.uniparc.Sequence xmlSeq = converter.toXml(uniSeq);

        System.out.println(
                UniParcXmlTestHelper.toXmlString(
                        xmlSeq, org.uniprot.core.xml.jaxb.uniparc.Sequence.class, "sequence"));
        Sequence converted = converter.fromXml(xmlSeq);
        assertEquals(uniSeq, converted);
    }
}
