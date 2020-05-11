package org.uniprot.core.xml.uniprot.description;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.uniprot.core.xml.uniprot.description.DescriptionHelper.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.description.*;
import org.uniprot.core.uniprotkb.description.impl.ProteinDescriptionBuilder;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.xml.jaxb.uniprot.DbReferenceType;
import org.uniprot.core.xml.jaxb.uniprot.ProteinType;
import org.uniprot.core.xml.uniprot.EvidenceIndexMapper;
import org.uniprot.core.xml.uniprot.UniProtXmlTestHelper;

class ProteinDescriptionConverterTest {

    @Test
    void test() {
        List<Evidence> evidences = createEvidences();
        Name allergenName = createName("allergen", evidences);
        Name biotechName = createName("biotech", evidences);
        List<Name> antigenNames = new ArrayList<>();
        antigenNames.add(createName("cd antigen", evidences));

        Name fullName = createName("a full Name", evidences);
        List<Name> shortNames = createShortNames();
        List<EC> ecNumbers = createECNumbers();
        ProteinRecName recommendedName = createProteinRecName(fullName, shortNames, ecNumbers);

        List<ProteinAltName> proteinAltNames = createAltName();
        Name fullName1 = createName("a full Name", evidences);

        List<EC> ecNumbers1 = createECNumbers();
        ProteinSubName subName = createProteinSubName(fullName1, ecNumbers1);
        List<ProteinSubName> subNames = new ArrayList<>();
        subNames.add(subName);
        ProteinDescriptionBuilder builder = new ProteinDescriptionBuilder();
        ProteinDescription description =
                builder.recommendedName(recommendedName)
                        .submissionNamesSet(subNames)
                        .alternativeNamesSet(proteinAltNames)
                        .allergenName(allergenName)
                        .biotechName(biotechName)
                        .cdAntigenNamesSet(antigenNames)
                        .build();
        ProteinDescriptionConverter converter = createConverter();
        ProteinType xmlObj = converter.toXml(description);
        System.out.println(UniProtXmlTestHelper.toXmlString(xmlObj, ProteinType.class, "protein"));
        ProteinDescription converted = converter.fromXml(xmlObj);
        assertEquals(description, converted);
        List<DbReferenceType> dbReferences = converter.toXmlDbReferences(description);
        dbReferences.forEach(
                val ->
                        System.out.println(
                                UniProtXmlTestHelper.toXmlString(
                                        val, DbReferenceType.class, "dbReference")));
    }

    @Test
    void testFull() {
        List<Evidence> evidences = createEvidences();
        Name fullName = createName("a full Name", evidences);
        List<Name> shortNames = createShortNames();
        List<EC> ecNumbers = createECNumbers();
        ProteinRecName recommendedName = createProteinRecName(fullName, shortNames, ecNumbers);
        List<ProteinAltName> proteinAltNames = createAltName();
        Name fullName1 = createName("a full Name", evidences);
        List<EC> ecNumbers1 = createECNumbers();
        ProteinSubName subName = createProteinSubName(fullName1, ecNumbers1);

        ProteinSection included1 = createProteinNameSection(recommendedName, null);
        ProteinSection contain1 = createProteinNameSection(recommendedName, null);
        ProteinSection contain2 = createProteinNameSection(recommendedName, proteinAltNames);
        List<ProteinSection> includes = new ArrayList<>();
        includes.add(included1);
        List<ProteinSection> contains = new ArrayList<>();
        contains.add(contain1);
        contains.add(contain2);
        List<ProteinSubName> subNames = new ArrayList<>();
        subNames.add(subName);
        ProteinDescriptionBuilder builder = new ProteinDescriptionBuilder();
        builder.recommendedName(recommendedName)
                .submissionNamesSet(subNames)
                .alternativeNamesSet(proteinAltNames)
                .includesSet(includes)
                .containsSet(contains);
        ProteinDescription description = builder.build();
        ProteinDescriptionConverter converter = createConverter();
        ProteinType xmlObj = converter.toXml(description);
        System.out.println(UniProtXmlTestHelper.toXmlString(xmlObj, ProteinType.class, "protein"));
        ProteinDescription converted = converter.fromXml(xmlObj);
        assertEquals(description, converted);
    }

    private ProteinDescriptionConverter createConverter() {
        EvidenceIndexMapper evRefMapper = new EvidenceIndexMapper();
        ECConverter ecConverter = new ECConverter(evRefMapper);
        NameConverter nameConverter = new NameConverter(evRefMapper);
        RecNameConverter recNameConverter = new RecNameConverter(nameConverter, ecConverter);
        AltNameConverter altNameConverter = new AltNameConverter(nameConverter, ecConverter);
        SubNameConverter subNameConverter = new SubNameConverter(nameConverter, ecConverter);

        return new ProteinDescriptionConverter(
                recNameConverter, altNameConverter, subNameConverter, nameConverter);
    }

    private List<ProteinAltName> createAltName() {
        List<ProteinAltName> alternativeNames = new ArrayList<>();
        List<Evidence> evidences = createEvidences();
        Name fullName = createName("a full alt Name", evidences);
        List<Name> shortNames = new ArrayList<>();
        shortNames.add(createName("short name1", evidences));
        shortNames.add(createName("short name2", evidences));
        List<EC> ecNumbers = new ArrayList<>();
        ecNumbers.add(createEC("1.2.3.4", evidences));

        alternativeNames.add(createProteinAltName(fullName, shortNames, ecNumbers));

        return alternativeNames;
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
