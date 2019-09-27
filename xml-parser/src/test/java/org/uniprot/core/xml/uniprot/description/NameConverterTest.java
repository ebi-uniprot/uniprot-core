package org.uniprot.core.xml.uniprot.description;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.uniprot.core.xml.uniprot.description.DescriptionHelper.createEvidences;
import static org.uniprot.core.xml.uniprot.description.DescriptionHelper.createName;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.description.Name;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.xml.jaxb.uniprot.EvidencedStringType;
import org.uniprot.core.xml.uniprot.EvidenceIndexMapper;

class NameConverterTest {

    @Test
    void test() {
        String val = "some value";
        List<Evidence> evidences = createEvidences();

        Name nameObj = createName(val, evidences);
        EvidenceIndexMapper evRefMapper = new EvidenceIndexMapper();
        NameConverter converter = new NameConverter(evRefMapper);
        EvidencedStringType xmlObj = converter.toXml(nameObj);
        assertEquals(val, xmlObj.getValue());
        assertEquals(Arrays.asList(1, 2), xmlObj.getEvidence());
        Name converted = converter.fromXml(xmlObj);
        System.out.println(nameObj.toString());
        System.out.println(converted.toString());
        assertEquals(nameObj, converted);
    }
}
