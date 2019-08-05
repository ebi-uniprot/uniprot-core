package uk.ac.ebi.uniprot.xml.uniprot;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.evidence.EvidencedValue;
import org.uniprot.core.uniprot.evidence.builder.EvidencedValueBuilder;

import uk.ac.ebi.uniprot.xml.jaxb.uniprot.EvidencedStringType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ObjectFactory;
import uk.ac.ebi.uniprot.xml.uniprot.EvidenceIndexMapper;
import uk.ac.ebi.uniprot.xml.uniprot.EvidencedValueConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.uniprot.core.uniprot.evidence.impl.EvidenceHelper.parseEvidenceLine;

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
        converter = new EvidencedValueConverter(new EvidenceIndexMapper(), new ObjectFactory(), true);
        xmlObj = converter.toXml(evValue);
        System.out.println(UniProtXmlTestHelper.toXmlString(xmlObj, EvidencedStringType.class, "text"));
        assertEquals(text + ".", xmlObj.getValue());


        assertEquals(Arrays.asList(1, 2), xmlObj.getEvidence());
        converted = converter.fromXml(xmlObj);
        assertEquals(evValue, converted);

    }

}
