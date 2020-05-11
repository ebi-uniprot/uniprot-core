package org.uniprot.core.xml.uniprot.description;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.uniprot.core.xml.uniprot.description.DescriptionHelper.createEC;
import static org.uniprot.core.xml.uniprot.description.DescriptionHelper.createEvidences;
import static org.uniprot.core.xml.uniprot.description.DescriptionHelper.createName;
import static org.uniprot.core.xml.uniprot.description.DescriptionHelper.createProteinAltName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.description.EC;
import org.uniprot.core.uniprotkb.description.Name;
import org.uniprot.core.uniprotkb.description.ProteinAltName;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.xml.jaxb.uniprot.ProteinType.AlternativeName;
import org.uniprot.core.xml.uniprot.EvidenceIndexMapper;
import org.uniprot.core.xml.uniprot.UniProtXmlTestHelper;

class AltNameConverterTest {
    @Test
    void testAll() {
        List<Evidence> evidences = createEvidences();
        Name fullName = createName("a full Name", evidences);
        List<Name> shortNames = createShortNames();
        List<EC> ecNumbers = createECNumbers();
        ProteinAltName altName = createProteinAltName(fullName, shortNames, ecNumbers);
        EvidenceIndexMapper evRefMapper = new EvidenceIndexMapper();
        ECConverter ecConverter = new ECConverter(evRefMapper);
        NameConverter nameConverter = new NameConverter(evRefMapper);
        AltNameConverter converter = new AltNameConverter(nameConverter, ecConverter);

        AlternativeName xmlObj = converter.toXml(altName);

        System.out.println(
                UniProtXmlTestHelper.toXmlString(xmlObj, AlternativeName.class, "alternativeName"));
        ProteinAltName converted = converter.fromXml(xmlObj);
        assertEquals(altName, converted);
    }

    @Test
    void testNoShortName() {
        List<Evidence> evidences = createEvidences();
        Name fullName = createName("a full Name", evidences);
        List<Name> shortNames = Collections.emptyList();
        List<EC> ecNumbers = createECNumbers();
        ProteinAltName altName = createProteinAltName(fullName, shortNames, ecNumbers);
        EvidenceIndexMapper evRefMapper = new EvidenceIndexMapper();
        ECConverter ecConverter = new ECConverter(evRefMapper);
        NameConverter nameConverter = new NameConverter(evRefMapper);
        AltNameConverter converter = new AltNameConverter(nameConverter, ecConverter);

        AlternativeName xmlObj = converter.toXml(altName);

        System.out.println(
                UniProtXmlTestHelper.toXmlString(xmlObj, AlternativeName.class, "alternativeName"));
        ProteinAltName converted = converter.fromXml(xmlObj);
        assertEquals(altName, converted);
    }

    @Test
    void testNoEC() {
        List<Evidence> evidences = createEvidences();
        Name fullName = createName("a full Name", evidences);
        List<Name> shortNames = createShortNames();
        List<EC> ecNumbers = Collections.emptyList();
        ProteinAltName altName = createProteinAltName(fullName, shortNames, ecNumbers);
        EvidenceIndexMapper evRefMapper = new EvidenceIndexMapper();
        ECConverter ecConverter = new ECConverter(evRefMapper);
        NameConverter nameConverter = new NameConverter(evRefMapper);
        AltNameConverter converter = new AltNameConverter(nameConverter, ecConverter);

        AlternativeName xmlObj = converter.toXml(altName);

        System.out.println(
                UniProtXmlTestHelper.toXmlString(xmlObj, AlternativeName.class, "alternativeName"));
        ProteinAltName converted = converter.fromXml(xmlObj);
        assertEquals(altName, converted);
    }

    private List<Name> createShortNames() {
        List<Evidence> evidences = createEvidences();
        List<Name> shortNames = new ArrayList<>();
        shortNames.add(createName("short name1", evidences));
        shortNames.add(createName("short name2", evidences));
        return shortNames;
    }

    private List<EC> createECNumbers() {
        List<Evidence> evidences = createEvidences();
        List<EC> ecNumbers = new ArrayList<>();
        ecNumbers.add(createEC("1.2.3.4", evidences));
        ecNumbers.add(createEC("1.3.4.3", evidences));
        return ecNumbers;
    }
}
