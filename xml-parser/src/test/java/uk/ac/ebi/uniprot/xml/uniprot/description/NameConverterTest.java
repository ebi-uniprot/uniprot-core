package uk.ac.ebi.uniprot.xml.uniprot.description;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.uniprot.description.Name;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.EvidencedStringType;
import uk.ac.ebi.uniprot.xml.uniprot.EvidenceIndexMapper;
import uk.ac.ebi.uniprot.xml.uniprot.description.NameConverter;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static uk.ac.ebi.uniprot.xml.uniprot.description.DescriptionHelper.createEvidences;
import static uk.ac.ebi.uniprot.xml.uniprot.description.DescriptionHelper.createName;

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
