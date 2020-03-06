package org.uniprot.core.xml.uniprot.description;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.uniprot.core.xml.uniprot.description.DescriptionHelper.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.description.EC;
import org.uniprot.core.uniprot.description.Name;
import org.uniprot.core.uniprot.description.ProteinRecName;
import org.uniprot.core.uniprot.description.impl.NameBuilder;
import org.uniprot.core.uniprot.description.impl.ProteinRecNameBuilder;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.evidence.EvidenceCode;
import org.uniprot.core.uniprot.evidence.impl.EvidenceBuilder;
import org.uniprot.core.xml.jaxb.uniprot.ProteinType.RecommendedName;
import org.uniprot.core.xml.uniprot.EvidenceIndexMapper;
import org.uniprot.core.xml.uniprot.UniProtXmlTestHelper;

class RecNameConverterTest {

    @Test
    void testAll() {
        List<Evidence> evidences = createEvidences();
        Name fullName = new NameBuilder().value("a full Name").evidencesSet(evidences).build();
        List<Name> shortNames = createShortNames();
        List<EC> ecNumbers = createECNumbers();
        ProteinRecName recName =
                new ProteinRecNameBuilder()
                        .fullName(fullName)
                        .shortNamesSet(shortNames)
                        .ecNumbersSet(ecNumbers)
                        .build();
        EvidenceIndexMapper evRefMapper = new EvidenceIndexMapper();
        ECConverter ecConverter = new ECConverter(evRefMapper);
        NameConverter nameConverter = new NameConverter(evRefMapper);
        RecNameConverter converter = new RecNameConverter(nameConverter, ecConverter);

        RecommendedName xmlObj = converter.toXml(recName);

        System.out.println(
                UniProtXmlTestHelper.toXmlString(xmlObj, RecommendedName.class, "recommendedName"));
        ProteinRecName converted = converter.fromXml(xmlObj);
        assertEquals(recName, converted);
    }

    @Test
    void testNoShortName() {
        List<Evidence> evidences = createEvidences();
        Name fullName = createName("a full Name", evidences);
        List<Name> shortNames = Collections.emptyList();
        List<EC> ecNumbers = createECNumbers();
        ProteinRecName recName = createProteinRecName(fullName, shortNames, ecNumbers);
        EvidenceIndexMapper evRefMapper = new EvidenceIndexMapper();
        ECConverter ecConverter = new ECConverter(evRefMapper);
        NameConverter nameConverter = new NameConverter(evRefMapper);
        RecNameConverter converter = new RecNameConverter(nameConverter, ecConverter);

        RecommendedName xmlObj = converter.toXml(recName);

        System.out.println(
                UniProtXmlTestHelper.toXmlString(xmlObj, RecommendedName.class, "recommendedName"));
        ProteinRecName converted = converter.fromXml(xmlObj);
        assertEquals(recName, converted);
    }

    @Test
    void testNoEC() {
        List<Evidence> evidences = createEvidences();
        Name fullName = createName("a full Name", evidences);
        List<Name> shortNames = createShortNames();
        List<EC> ecNumbers = Collections.emptyList();
        ProteinRecName recName = createProteinRecName(fullName, shortNames, ecNumbers);
        EvidenceIndexMapper evRefMapper = new EvidenceIndexMapper();
        ECConverter ecConverter = new ECConverter(evRefMapper);
        NameConverter nameConverter = new NameConverter(evRefMapper);
        RecNameConverter converter = new RecNameConverter(nameConverter, ecConverter);

        RecommendedName xmlObj = converter.toXml(recName);

        System.out.println(
                UniProtXmlTestHelper.toXmlString(xmlObj, RecommendedName.class, "recommendedName"));
        ProteinRecName converted = converter.fromXml(xmlObj);
        assertEquals(recName, converted);
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

    private List<Evidence> createEvidences() {
        List<Evidence> evidences = new ArrayList<>();
        evidences.add(
                new EvidenceBuilder()
                        .databaseName("Ensembl")
                        .databaseId("ENSP0001324")
                        .evidenceCode(EvidenceCode.ECO_0000313)
                        .build());
        evidences.add(
                new EvidenceBuilder()
                        .databaseName("PIRNR")
                        .databaseName("PIRNR001361")
                        .evidenceCode(EvidenceCode.ECO_0000256)
                        .build());
        return evidences;
    }
}
