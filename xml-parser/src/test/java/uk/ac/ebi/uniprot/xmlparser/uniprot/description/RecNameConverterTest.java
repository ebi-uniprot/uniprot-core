package uk.ac.ebi.uniprot.xmlparser.uniprot.description;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.uniprot.description.EC;
import uk.ac.ebi.uniprot.domain.uniprot.description.Name;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinName;
import uk.ac.ebi.uniprot.domain.uniprot.description.builder.NameBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.description.builder.ProteinNameBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.EvidenceCode;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.builder.EvidenceBuilder;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ProteinType.RecommendedName;
import uk.ac.ebi.uniprot.xmlparser.uniprot.EvidenceIndexMapper;
import uk.ac.ebi.uniprot.xmlparser.uniprot.UniProtXmlTestHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static uk.ac.ebi.uniprot.xmlparser.uniprot.description.DescriptionHelper.*;

class RecNameConverterTest {

    @Test
    void testAll() {
        List<Evidence> evidences = createEvidences();
        Name fullName = new NameBuilder().value("a full Name").evidences(evidences).build();
        List<Name> shortNames = createShortNames();
        List<EC> ecNumbers = createECNumbers();
        ProteinName recName = new ProteinNameBuilder().fullName(fullName).shortNames(shortNames).ecNumbers(ecNumbers)
                .build();
        EvidenceIndexMapper evRefMapper = new EvidenceIndexMapper();
        ECConverter ecConverter = new ECConverter(evRefMapper);
        NameConverter nameConverter = new NameConverter(evRefMapper);
        RecNameConverter converter = new RecNameConverter(nameConverter, ecConverter);

        RecommendedName xmlObj = converter.toXml(recName);

        System.out.println(UniProtXmlTestHelper.toXmlString(xmlObj, RecommendedName.class, "recommendedName"));
        ProteinName converted = converter.fromXml(xmlObj);
        assertEquals(recName, converted);

    }

    @Test
    void testNoShortName() {
        List<Evidence> evidences = createEvidences();
        Name fullName = createName("a full Name", evidences);
        List<Name> shortNames = Collections.emptyList();
        List<EC> ecNumbers = createECNumbers();
        ProteinName recName = createProteinName(fullName, shortNames, ecNumbers);
        EvidenceIndexMapper evRefMapper = new EvidenceIndexMapper();
        ECConverter ecConverter = new ECConverter(evRefMapper);
        NameConverter nameConverter = new NameConverter(evRefMapper);
        RecNameConverter converter = new RecNameConverter(nameConverter, ecConverter);

        RecommendedName xmlObj = converter.toXml(recName);

        System.out.println(UniProtXmlTestHelper.toXmlString(xmlObj, RecommendedName.class, "recommendedName"));
        ProteinName converted = converter.fromXml(xmlObj);
        assertEquals(recName, converted);

    }

    @Test
    void testNoEC() {
        List<Evidence> evidences = createEvidences();
        Name fullName = createName("a full Name", evidences);
        List<Name> shortNames = createShortNames();
        List<EC> ecNumbers = Collections.emptyList();
        ProteinName recName = createProteinName(fullName, shortNames, ecNumbers);
        EvidenceIndexMapper evRefMapper = new EvidenceIndexMapper();
        ECConverter ecConverter = new ECConverter(evRefMapper);
        NameConverter nameConverter = new NameConverter(evRefMapper);
        RecNameConverter converter = new RecNameConverter(nameConverter, ecConverter);

        RecommendedName xmlObj = converter.toXml(recName);

        System.out.println(UniProtXmlTestHelper.toXmlString(xmlObj, RecommendedName.class, "recommendedName"));
        ProteinName converted = converter.fromXml(xmlObj);
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
        evidences.add(new EvidenceBuilder()
                              .databaseName("Ensembl")
                              .databaseId("ENSP0001324")
                              .evidenceCode(EvidenceCode.ECO_0000313)
                              .build());
        evidences.add(new EvidenceBuilder()
                              .databaseName("PIRNR")
                              .databaseName("PIRNR001361")
                              .evidenceCode(EvidenceCode.ECO_0000256)
                              .build());
        return evidences;
    }

}
