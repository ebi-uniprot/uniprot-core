package org.uniprot.core.xml.uniprot.description;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.uniprot.core.xml.uniprot.description.DescriptionHelper.createEC;
import static org.uniprot.core.xml.uniprot.description.DescriptionHelper.createEvidences;
import static org.uniprot.core.xml.uniprot.description.DescriptionHelper.createName;
import static org.uniprot.core.xml.uniprot.description.DescriptionHelper.createProteinSubName;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.description.EC;
import org.uniprot.core.uniprotkb.description.Name;
import org.uniprot.core.uniprotkb.description.ProteinSubName;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.xml.jaxb.uniprot.ProteinType.SubmittedName;
import org.uniprot.core.xml.uniprot.EvidenceIndexMapper;
import org.uniprot.core.xml.uniprot.UniProtXmlTestHelper;

class SubNameConverterTest {

    @Test
    void test() {
        List<Evidence> evidences = createEvidences();
        Name fullName = createName("a full Name", evidences);

        List<EC> ecNumbers = createECNumbers();
        ProteinSubName subName = createProteinSubName(fullName, ecNumbers);
        EvidenceIndexMapper evRefMapper = new EvidenceIndexMapper();
        ECConverter ecConverter = new ECConverter(evRefMapper);
        NameConverter nameConverter = new NameConverter(evRefMapper);
        SubNameConverter converter = new SubNameConverter(nameConverter, ecConverter);
        SubmittedName xmlObj = converter.toXml(subName);

        System.out.println(
                UniProtXmlTestHelper.toXmlString(xmlObj, SubmittedName.class, "submittedName"));
        ProteinSubName converted = converter.fromXml(xmlObj);
        assertEquals(subName, converted);
    }

    private List<EC> createECNumbers() {
        List<Evidence> evidences = createEvidences();
        List<EC> ecNumbers = new ArrayList<>();
        ecNumbers.add(createEC("1.2.3.4", evidences));
        ecNumbers.add(createEC("1.3.4.3", evidences));
        return ecNumbers;
    }
}
