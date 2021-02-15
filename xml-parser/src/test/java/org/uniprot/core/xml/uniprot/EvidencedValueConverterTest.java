package org.uniprot.core.xml.uniprot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.uniprot.cv.evidence.EvidenceHelper.parseEvidenceLine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.evidence.EvidencedValue;
import org.uniprot.core.uniprotkb.evidence.impl.EvidencedValueBuilder;
import org.uniprot.core.xml.jaxb.uniprot.EvidencedStringType;
import org.uniprot.core.xml.jaxb.uniprot.ObjectFactory;

@Slf4j
class EvidencedValueConverterTest {

    @Test
    void test() {
        Evidence evidence1 = parseEvidenceLine("ECO:0000269|PubMed:9060645");
        Evidence evidence2 = parseEvidenceLine("ECO:0000269|PubMed:9060647");
        List<Evidence> evids = new ArrayList<>();
        evids.add(evidence1);
        evids.add(evidence2);
        String text = "some text";
        EvidencedValue evValue = new EvidencedValueBuilder(text, evids).build();
        EvidencedValueConverter converter = new EvidencedValueConverter(new EvidenceIndexMapper());
        EvidencedStringType xmlObj = converter.toXml(evValue);
        assertEquals(text, xmlObj.getValue());
        assertEquals(Arrays.asList(1, 2), xmlObj.getEvidence());
        EvidencedValue converted = converter.fromXml(xmlObj);
        assertEquals(evValue, converted);
        converter =
                new EvidencedValueConverter(new EvidenceIndexMapper(), new ObjectFactory(), true);
        xmlObj = converter.toXml(evValue);
        log.debug(
                UniProtXmlTestHelper.toXmlString(xmlObj, EvidencedStringType.class, "text"));
        assertEquals(text + ".", xmlObj.getValue());

        assertEquals(Arrays.asList(1, 2), xmlObj.getEvidence());
        converted = converter.fromXml(xmlObj);
        assertEquals(evValue, converted);
    }
}
