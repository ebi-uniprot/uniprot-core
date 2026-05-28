package org.uniprot.core.xml.uniprot.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.uniprot.cv.evidence.EvidenceHelper.parseEvidenceLine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.comment.SubcellularLocation;
import org.uniprot.core.uniprotkb.comment.SubcellularLocationValue;
import org.uniprot.core.uniprotkb.comment.impl.SubcellularLocationBuilder;
import org.uniprot.core.uniprotkb.comment.impl.SubcellularLocationValueBuilder;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.xml.AbstractConverterTest;
import org.uniprot.core.xml.jaxb.uniprot.EvidencedStringType;
import org.uniprot.core.xml.jaxb.uniprot.SubcellularLocationType;
import org.uniprot.core.xml.uniprot.EvidenceIndexMapper;
import org.uniprot.core.xml.uniprot.EvidencedStringTypeConverterTest;
import org.uniprot.core.xml.uniprot.UniProtXmlTestHelper;

public class SubcellularLocationConverterTest extends AbstractConverterTest {

    @Test
    void test() {
        SubcellularLocationValue location =
                create(
                        "Membrane, caveola",
                        Arrays.asList(
                                "ECO:0000256|PIRNR:PIRNR037393", "ECO:0000256|RuleBase:RU361271"));
        SubcellularLocationValue topology =
                create(
                        "Lipid-anchor, GPI-like-anchor",
                        Arrays.asList(
                                "ECO:0000256|PIRNR:PIRNR0373943", "ECO:0000256|RuleBase:RU361271"));

        SubcellularLocationValue orientation =
                create(
                        "Peripheral membrane protein",
                        Arrays.asList(
                                "ECO:0000256|RuleBase:RU000680", "ECO:0000256|SAAS:SAAS00583323"));
        SubcellularLocation subcelLocation =
                new SubcellularLocationBuilder()
                        .location(location)
                        .topology(topology)
                        .orientation(orientation)
                        .build();
        SubcellularLocationConverter converter =
                new SubcellularLocationConverter(
                        new EvidenceIndexMapper(), new SubcellLocationNameMapImpl());
        SubcellularLocationType xmlsubcelLocation = converter.toXml(subcelLocation);
        System.out.println(
                UniProtXmlTestHelper.toXmlString(
                        xmlsubcelLocation, SubcellularLocationType.class, "subcellularLocation"));

        SubcellularLocation converted = converter.fromXml(xmlsubcelLocation);
        assertEquals(subcelLocation, converted);
    }
    
    @Test
    void testLocationWithLowercase() {
    	 SubcellularLocationValue location =
                 create(
                         "Golgi apparatus, trans-Golgi network membrane",
                         List.of(
                                 "ECO:0000256|PIRNR:PIRNR037393"));
        
         SubcellularLocation subcelLocation =
                 new SubcellularLocationBuilder()
                         .location(location)
                         .build();
         SubcellularLocationConverter converter =
                 new SubcellularLocationConverter(
                         new EvidenceIndexMapper(), new SubcellLocationNameMapImpl());
         SubcellularLocationType xmlsubcelLocation = converter.toXml(subcelLocation);
         String xml =UniProtXmlTestHelper.toXmlString(
                 xmlsubcelLocation, SubcellularLocationType.class, "subcellularLocation");
         String expectedXml ="""
<subcellularLocation xmlns="http://uniprot.org/uniprot">
    <location evidence="1">Golgi apparatus</location>
    <location evidence="1">trans-Golgi network membrane</location>
</subcellularLocation>\
         		""";
        assertEquals(expectedXml, xml ) ;		 
         SubcellularLocation converted = converter.fromXml(xmlsubcelLocation);
         assertEquals(subcelLocation, converted);
    }

    private SubcellularLocationValue create(String val, List<String> evidences) {
        return new SubcellularLocationValueBuilder()
                .id("")
                .value(val)
                .evidencesSet(createEvidence(evidences))
                .build();
    }

    private List<Evidence> createEvidence(List<String> evids) {
        List<Evidence> evidences = new ArrayList<>();
        for (String ev : evids) {
            evidences.add(parseEvidenceLine(ev));
        }
        return evidences;
    }

    public static SubcellularLocationType createObject() {
        SubcellularLocationType subcellularLocationType =
                uniProtObjectFactory.createSubcellularLocationType();
        List<EvidencedStringType> location = EvidencedStringTypeConverterTest.createObjects();
        List<EvidencedStringType> topology = EvidencedStringTypeConverterTest.createObjects();
        List<EvidencedStringType> orientation = EvidencedStringTypeConverterTest.createObjects();
        subcellularLocationType.getLocation().addAll(location);
        subcellularLocationType.getTopology().addAll(topology);
        subcellularLocationType.getOrientation().addAll(orientation);
        return subcellularLocationType;
    }
}
